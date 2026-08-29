cmake_minimum_required(VERSION 3.28)

project(raygui-sys LANGUAGES C)

set(RAYLIB_SYS_ROOT
    ""
    CACHE STRING "Directory of raylib-sys install")
if(NOT RAYLIB_SYS_ROOT)
  message(FATAL_ERROR "Must specify -DRAYLIB_SYS_ROOT=...")
endif()

# raygui ships no library target of its own: its CMakeLists declares an
# INTERFACE target and installs headers. raygui_impl.c is our one translation
# unit, defining RAYGUI_IMPLEMENTATION.
#
# raylib is included but not linked. The archive keeps raygui's calls into
# raylib undefined, so they resolve against the libraylib the consumer links
# through its own raylib-sys dependency, rather than a second copy.
add_library(raygui raygui_impl.c)
target_include_directories(raygui PUBLIC lib/raygui/src
                                         "${RAYLIB_SYS_ROOT}/include")

install(
  TARGETS raygui
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
install(FILES lib/raygui/src/raygui.h DESTINATION include)
