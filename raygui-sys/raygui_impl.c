/*
 * The single translation unit for raygui.
 *
 * raygui is header-only: declarations and implementation share one file, and
 * RAYGUI_IMPLEMENTATION must be defined in exactly ONE translation unit. That
 * makes this file the whole of the C in this package.
 *
 * raygui's controls call GetMousePosition, DrawRectangle and friends
 * internally, so those symbols are left undefined here and resolve against the
 * libraylib that raylib-sys built when the consumer links. Resolving them
 * against a second copy of raylib would leave every control reading empty
 * input state, inert, with no error to show for it.
 */
#define RAYGUI_IMPLEMENTATION
#include "raygui.h"
