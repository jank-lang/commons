cmake_minimum_required(VERSION 3.28)

project(imgui-sys LANGUAGES CXX)

add_library(
  imgui
  lib/imgui/imgui.cpp lib/imgui/imgui_demo.cpp lib/imgui/imgui_draw.cpp
  lib/imgui/imgui_tables.cpp lib/imgui/imgui_widgets.cpp
  lib/imgui/misc/cpp/imgui_stdlib.cpp)
target_include_directories(imgui PUBLIC lib/imgui lib/imgui/backends)

install(
  TARGETS imgui
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
install(DIRECTORY lib/imgui/backends/ DESTINATION backends)
install(
  DIRECTORY lib/imgui/
  DESTINATION include
  FILES_MATCHING
  PATTERN "backends" EXCLUDE
  PATTERN "docs" EXCLUDE
  PATTERN "examples" EXCLUDE
  PATTERN "*.h"
  PATTERN "*.hpp")
