#include <stdlib.h>
#include <math.h>

#include <box2d/box2d.h>
#include <raylib.h>

// ---------------------------------------------------------------------------
// View settings
// ---------------------------------------------------------------------------

typedef struct Context
{
  // screen position of world (0,0)
  Vector2 origin;
  float pixelsPerMeter;
} Context;

#define FILL_ALPHA 0.5f
#define ARC_STEP (PI / 12.0f)

// ---------------------------------------------------------------------------
// Helpers
// ---------------------------------------------------------------------------
static Color ToColor(b2HexColor c, float alpha)
{
  Color col;
  col.r = (unsigned char)((c >> 16) & 0xFF);
  col.g = (unsigned char)((c >> 8) & 0xFF);
  col.b = (unsigned char)(c & 0xFF);
  col.a = (unsigned char)(alpha * 255.0f);
  return col;
}

static Vector2 ToScreen(Context *ctx, Vector2 w)
{
  return (Vector2){ ctx->origin.x + w.x * ctx->pixelsPerMeter,
                    ctx->origin.y - w.y * ctx->pixelsPerMeter };
}

static Vector2 PosToWorld(b2Pos p)
{
  return (Vector2){ (float)p.x, (float)p.y };
}

// Local-space vertex -> world-space point
static Vector2 TransformPoint(b2WorldTransform t, b2Vec2 v)
{
  float c = t.q.c, s = t.q.s;
  return (Vector2){ (float)t.p.x + c * v.x - s * v.y, (float)t.p.y + s * v.x + c * v.y };
}

// Filled sector (triangle fan) in world space, sweeping CCW from a0.
static void FillSector(Context *ctx, Vector2 c, float r, float a0, float sweep, Color col)
{
  int n = (int)ceilf(fabsf(sweep) / ARC_STEP);
  if(n < 1)
  {
    n = 1;
  }
  float da = sweep / n;
  Vector2 prev = { c.x + r * cosf(a0), c.y + r * sinf(a0) };
  for(int i = 1; i <= n; ++i)
  {
    float a = a0 + da * i;
    Vector2 cur = { c.x + r * cosf(a), c.y + r * sinf(a) };
    DrawTriangle(ToScreen(ctx, c), ToScreen(ctx, prev), ToScreen(ctx, cur), col);
    prev = cur;
  }
}

// Arc outline in world space, sweeping CCW from a0.
static void DrawArc(Context *ctx, Vector2 c, float r, float a0, float sweep, Color col)
{
  int n = (int)ceilf(fabsf(sweep) / ARC_STEP);
  if(n < 1)
  {
    n = 1;
  }
  float da = sweep / n;
  Vector2 prev = { c.x + r * cosf(a0), c.y + r * sinf(a0) };
  for(int i = 1; i <= n; ++i)
  {
    float a = a0 + da * i;
    Vector2 cur = { c.x + r * cosf(a), c.y + r * sinf(a) };
    DrawLineV(ToScreen(ctx, prev), ToScreen(ctx, cur), col);
    prev = cur;
  }
}

// Convex fan fill from world-space points (must be CCW, as Box2D provides).
static void FillConvex(Context *ctx, Vector2 const *w, int n, Color col)
{
  for(int i = 1; i < n - 1; ++i)
  {
    DrawTriangle(ToScreen(ctx, w[0]), ToScreen(ctx, w[i]), ToScreen(ctx, w[i + 1]), col);
  }
}

static void OutlineLoop(Context *ctx, Vector2 const *w, int n, Color col)
{
  for(int i = 0; i < n; ++i)
  {
    DrawLineV(ToScreen(ctx, w[i]), ToScreen(ctx, w[(i + 1) % n]), col);
  }
}

// ---------------------------------------------------------------------------
// Box2D debug draw callbacks
// ---------------------------------------------------------------------------
void DrawPolygonFcn(b2WorldTransform transform,
                    b2Vec2 const *vertices,
                    int vertexCount,
                    b2HexColor color,
                    void *context)
{
  Context *ctx = (Context *)context;
  if(vertexCount > B2_MAX_POLYGON_VERTICES)
  {
    vertexCount = B2_MAX_POLYGON_VERTICES;
  }

  Vector2 w[B2_MAX_POLYGON_VERTICES];
  for(int i = 0; i < vertexCount; ++i)
  {
    w[i] = TransformPoint(transform, vertices[i]);
  }

  OutlineLoop(ctx, w, vertexCount, ToColor(color, 1.0f));
}

void DrawSolidPolygonFcn(b2WorldTransform transform,
                         b2Vec2 const *vertices,
                         int vertexCount,
                         float radius,
                         b2HexColor color,
                         void *context)
{
  Context *ctx = (Context *)context;
  if(vertexCount > B2_MAX_POLYGON_VERTICES)
  {
    vertexCount = B2_MAX_POLYGON_VERTICES;
  }
  if(vertexCount < 3)
  {
    return;
  }

  Color fill = ToColor(color, FILL_ALPHA);
  Color outline = ToColor(color, 1.0f);

  Vector2 w[B2_MAX_POLYGON_VERTICES];
  for(int i = 0; i < vertexCount; ++i)
  {
    w[i] = TransformPoint(transform, vertices[i]);
  }

  // Core polygon
  FillConvex(ctx, w, vertexCount, fill);

  if(radius <= 0.0f)
  {
    OutlineLoop(ctx, w, vertexCount, outline);
    return;
  }

  // Rounded polygon: extrude each edge outward and add a sector at each corner.
  Vector2 normals[B2_MAX_POLYGON_VERTICES];
  for(int i = 0; i < vertexCount; ++i)
  {
    Vector2 a = w[i], b = w[(i + 1) % vertexCount];
    float dx = b.x - a.x, dy = b.y - a.y;
    float len = sqrtf(dx * dx + dy * dy);
    if(len < 1e-9f)
    {
      len = 1.0f;
    }
    normals[i] = (Vector2){ dy / len, -dx / len }; // outward for CCW winding
  }

  for(int i = 0; i < vertexCount; ++i)
  {
    int j = (i + 1) % vertexCount;
    Vector2 n = normals[i];
    Vector2 a = w[i], d = w[j];
    Vector2 b = { a.x + n.x * radius, a.y + n.y * radius };
    Vector2 c = { d.x + n.x * radius, d.y + n.y * radius };

    // Edge strip
    DrawTriangle(ToScreen(ctx, a), ToScreen(ctx, b), ToScreen(ctx, c), fill);
    DrawTriangle(ToScreen(ctx, a), ToScreen(ctx, c), ToScreen(ctx, d), fill);
    DrawLineV(ToScreen(ctx, b), ToScreen(ctx, c), outline);

    // Corner sector at vertex j, between normal[i] and normal[j]
    Vector2 n0 = normals[i], n1 = normals[j];
    float a0 = atan2f(n0.y, n0.x);
    float a1 = atan2f(n1.y, n1.x);
    float sweep = a1 - a0;
    if(sweep < 0.0f)
    {
      sweep += 2.0f * PI;
    }

    FillSector(ctx, w[j], radius, a0, sweep, fill);
    DrawArc(ctx, w[j], radius, a0, sweep, outline);
  }
}

void DrawCircleFcn(b2Pos center, float radius, b2HexColor color, void *context)
{
  Context *ctx = (Context *)context;
  Vector2 c = ToScreen(ctx, PosToWorld(center));
  DrawCircleLinesV(c, radius * ctx->pixelsPerMeter, ToColor(color, 1.0f));
}

void DrawSolidCircleFcn(b2WorldTransform transform,
                        b2Vec2 center,
                        float radius,
                        b2HexColor color,
                        void *context)
{
  Context *ctx = (Context *)context;
  Vector2 wc = TransformPoint(transform, center);
  Vector2 sc = ToScreen(ctx, wc);
  float rp = radius * ctx->pixelsPerMeter;

  DrawCircleV(sc, rp, ToColor(color, FILL_ALPHA));
  DrawCircleLinesV(sc, rp, ToColor(color, 1.0f));

  // Line showing the body's rotation
  Vector2 axisEnd = { wc.x + transform.q.c * radius, wc.y + transform.q.s * radius };
  DrawLineV(sc, ToScreen(ctx, axisEnd), ToColor(color, 1.0f));
}

void DrawSolidCapsuleFcn(b2Pos p1, b2Pos p2, float radius, b2HexColor color, void *context)
{
  Context *ctx = (Context *)context;
  Color fill = ToColor(color, FILL_ALPHA);
  Color outline = ToColor(color, 1.0f);

  Vector2 a = PosToWorld(p1);
  Vector2 b = PosToWorld(p2);

  float dx = b.x - a.x, dy = b.y - a.y;
  float len = sqrtf(dx * dx + dy * dy);
  Vector2 d = (len > 1e-9f) ? (Vector2){ dx / len, dy / len } : (Vector2){ 1.0f, 0.0f };
  float theta = atan2f(d.y, d.x);

  // Right-hand perpendicular scaled by radius
  Vector2 nr = { d.y * radius, -d.x * radius };

  Vector2 q0 = { a.x + nr.x, a.y + nr.y };
  Vector2 q1 = { b.x + nr.x, b.y + nr.y };
  Vector2 q2 = { b.x - nr.x, b.y - nr.y };
  Vector2 q3 = { a.x - nr.x, a.y - nr.y };

  // Body
  DrawTriangle(ToScreen(ctx, q0), ToScreen(ctx, q1), ToScreen(ctx, q2), fill);
  DrawTriangle(ToScreen(ctx, q0), ToScreen(ctx, q2), ToScreen(ctx, q3), fill);

  // Caps
  FillSector(ctx, b, radius, theta - 0.5f * PI, PI, fill);
  FillSector(ctx, a, radius, theta + 0.5f * PI, PI, fill);

  // Outline
  DrawLineV(ToScreen(ctx, q0), ToScreen(ctx, q1), outline);
  DrawLineV(ToScreen(ctx, q3), ToScreen(ctx, q2), outline);
  DrawArc(ctx, b, radius, theta - 0.5f * PI, PI, outline);
  DrawArc(ctx, a, radius, theta + 0.5f * PI, PI, outline);
}

void DrawLineFcn(b2Pos p1, b2Pos p2, b2HexColor color, void *context)
{
  Context *ctx = (Context *)context;
  DrawLineV(ToScreen(ctx, PosToWorld(p1)), ToScreen(ctx, PosToWorld(p2)), ToColor(color, 1.0f));
}

void DrawTransformFcn(b2WorldTransform transform, void *context)
{
  Context *ctx = (Context *)context;
  float const axisLen = 0.2f; // meters

  Vector2 o = { (float)transform.p.x, (float)transform.p.y };
  Vector2 xe = { o.x + axisLen * transform.q.c, o.y + axisLen * transform.q.s };
  Vector2 ye = { o.x - axisLen * transform.q.s, o.y + axisLen * transform.q.c };

  DrawLineV(ToScreen(ctx, o), ToScreen(ctx, xe), RED);
  DrawLineV(ToScreen(ctx, o), ToScreen(ctx, ye), GREEN);
}

void DrawPointFcn(b2Pos p, float size, b2HexColor color, void *context)
{
  Context *ctx = (Context *)context;
  // `size` is in pixels
  DrawCircleV(ToScreen(ctx, PosToWorld(p)), size * 0.5f, ToColor(color, 1.0f));
}

void DrawStringFcn(b2Pos p, char const *s, b2HexColor color, void *context)
{
  Context *ctx = (Context *)context;
  Vector2 sp = ToScreen(ctx, PosToWorld(p));
  DrawText(s, (int)sp.x, (int)sp.y, 10, ToColor(color, 1.0f));
}

void DrawBoundsFcn(b2AABB aabb, b2HexColor color, void *context)
{
  Context *ctx = (Context *)context;
  Vector2 ll = { aabb.lowerBound.x, aabb.lowerBound.y };
  Vector2 lr = { aabb.upperBound.x, aabb.lowerBound.y };
  Vector2 ur = { aabb.upperBound.x, aabb.upperBound.y };
  Vector2 ul = { aabb.lowerBound.x, aabb.upperBound.y };

  Vector2 pts[4] = { ll, lr, ur, ul };
  OutlineLoop(ctx, pts, 4, ToColor(color, 1.0f));
}

b2DebugDraw b2CreateDebugDraw(float pixelsPerMeter)
{
  b2DebugDraw debugDraw = b2DefaultDebugDraw();
  debugDraw.DrawPolygonFcn = DrawPolygonFcn;
  debugDraw.DrawSolidPolygonFcn = DrawSolidPolygonFcn;
  debugDraw.DrawCircleFcn = DrawCircleFcn;
  debugDraw.DrawSolidCircleFcn = DrawSolidCircleFcn;
  debugDraw.DrawSolidCapsuleFcn = DrawSolidCapsuleFcn;
  debugDraw.DrawLineFcn = DrawLineFcn;
  debugDraw.DrawTransformFcn = DrawTransformFcn;
  debugDraw.DrawPointFcn = DrawPointFcn;
  debugDraw.DrawStringFcn = DrawStringFcn;
  debugDraw.DrawBoundsFcn = DrawBoundsFcn;
  Context *ctx = (Context *)malloc(sizeof(Context));
  ctx->origin = (Vector2){ GetScreenWidth() / 2, GetScreenHeight() / 2 };
  ctx->pixelsPerMeter = pixelsPerMeter;
  debugDraw.context = ctx;
  return debugDraw;
}
