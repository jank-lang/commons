cmake_minimum_required(VERSION 3.28)

project(imgui-opengl2-sys LANGUAGES CXX)

set(IMGUI_SYS_ROOT
    ""
    CACHE STRING "Directory of imgui-sys install")
if(NOT IMGUI_SYS_ROOT)
  message(FATAL_ERROR "Must specify -DIMGUI_SYS_ROOT=...")
endif()

find_package(OpenGL REQUIRED)

add_library(imgui_opengl2 "${IMGUI_SYS_ROOT}/backends/imgui_impl_opengl2.cpp")
target_include_directories(
  imgui_opengl2 PUBLIC "${IMGUI_SYS_ROOT}/include"
                       "${IMGUI_SYS_ROOT}/backends")
target_link_libraries(imgui_opengl2 PUBLIC OpenGL::GL)

install(
  TARGETS imgui_opengl2
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
install(FILES "${IMGUI_SYS_ROOT}/backends/imgui_impl_opengl2.h"
        DESTINATION include/backends/)
