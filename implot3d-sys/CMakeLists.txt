cmake_minimum_required(VERSION 3.28)

project(implot3d-sys LANGUAGES CXX)

set(IMGUI_SYS_ROOT
    ""
    CACHE STRING "Directory of imgui-sys install")
if(NOT IMGUI_SYS_ROOT)
  message(FATAL_ERROR "Must specify -DIMGUI_SYS_ROOT=...")
endif()

add_library(
  implot3d STATIC
  lib/implot3d/implot3d.cpp lib/implot3d/implot3d_demo.cpp
  lib/implot3d/implot3d_items.cpp lib/implot3d/implot3d_meshes.cpp)
target_include_directories(implot3d PUBLIC "${IMGUI_SYS_ROOT}/include")
target_link_directories(implot3d PUBLIC "${IMGUI_SYS_ROOT}/lib")
target_link_libraries(implot3d PUBLIC imgui)

install(
  TARGETS implot3d
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
install(FILES lib/implot3d/implot3d.h lib/implot3d/implot3d_internal.h
        DESTINATION include/)
