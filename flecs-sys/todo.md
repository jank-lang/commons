## Add flecs

Used https://book.jank-lang.org/jank-build/packaging-source-lib.html, but also copied the folder from box3d-sys to make it easier o/

- [x] add git submodule into `flecs-sys/lib/lib`
- [x] project.clj (distr flecs.c and .h)
- [x] readme
- [x] jank build
- [x] example
- [x] cmake
- [ ] we can't write `void add_move_system(flecs::world &world)` in pure jank ?
  - not today it seems
- [ ] it seems we always need DEVELOPER_DIR=/Library/Developer/CommandLineTools in osx
- [ ] how to make all these flecs compilation flags available from jank build?
  - https://www.flecs.dev/flecs/BuildingFlecs.html
  - guess we can do it later

## Others

- [ ] hegel-sys
