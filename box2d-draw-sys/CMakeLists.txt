cmake_minimum_required(VERSION 3.28)
project(box2d_draw_sys LANGUAGES C)

set(RAYLIB_SYS_ROOT
    ""
    CACHE STRING "Directory of raylib-sys install")
if(NOT RAYLIB_SYS_ROOT)
  message(FATAL_ERROR "Must specify -DRAYLIB_SYS_ROOT=...")
endif()

set(BOX2D_SYS_ROOT
    ""
    CACHE STRING "Directory of box2d-sys install")
if(NOT RAYLIB_SYS_ROOT)
  message(FATAL_ERROR "Must specify -DBOX2D_SYS_ROOT=...")
endif()

add_library(box2d_draw STATIC src/box2d_draw.c)
target_include_directories(box2d_draw PUBLIC include
                                             "${RAYLIB_SYS_ROOT}/src"
                                             "${RAYLIB_SYS_ROOT}/include"
                                             "${BOX2D_SYS_ROOT}/src"
                                             "${BOX2D_SYS_ROOT}/include")

install(
  TARGETS box2d_draw
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
install(FILES include/box2d_draw.h DESTINATION include)
