cmake_minimum_required(VERSION 3.28)

project(imgui-sys LANGUAGES CXX)

find_package(glfw3 REQUIRED)
find_package(OpenGL REQUIRED)

add_library(
  imgui
  lib/imgui/imgui.cpp
  lib/imgui/imgui_demo.cpp
  lib/imgui/imgui_draw.cpp
  lib/imgui/imgui_tables.cpp
  lib/imgui/imgui_widgets.cpp
  lib/imgui/misc/cpp/imgui_stdlib.cpp
  lib/imgui/backends/imgui_impl_glfw.cpp
  lib/imgui/backends/imgui_impl_opengl2.cpp)
target_include_directories(imgui PUBLIC lib/imgui lib/imgui/backends)
target_link_libraries(imgui PUBLIC glfw OpenGL::GL)

install(
  TARGETS imgui
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
install(
  DIRECTORY lib/imgui/
  DESTINATION include
  FILES_MATCHING
  PATTERN "*.h"
  PATTERN "*.hpp")
