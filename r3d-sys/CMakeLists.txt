cmake_minimum_required(VERSION 3.28)
project(r3d-sys LANGUAGES C)

find_package(raylib REQUIRED)
set(RAYLIB_INCLUDE_DIRS "${raylib_DIR}/../../include")
set(RAYLIB_LIBRARIES "raylib")

add_subdirectory(lib/r3d)

# r3d doesn't declare any install targets, so we do that here.
install(DIRECTORY lib/r3d/include/ DESTINATION ${CMAKE_INSTALL_INCLUDEDIR})
install(TARGETS r3d assimp
    ARCHIVE DESTINATION lib
    LIBRARY DESTINATION lib
    RUNTIME DESTINATION bin
)
