cmake_minimum_required(VERSION 3.28)

project(imgui-glfw-sys LANGUAGES CXX)

set(IMGUI_SYS_ROOT
    ""
    CACHE STRING "Directory of imgui-sys install")
if(NOT IMGUI_SYS_ROOT)
  message(FATAL_ERROR "Must specify -DIMGUI_SYS_ROOT=...")
endif()

find_package(glfw3 REQUIRED)

add_library(imgui_glfw "${IMGUI_SYS_ROOT}/backends/imgui_impl_glfw.cpp")
target_include_directories(
  imgui_glfw PUBLIC "${IMGUI_SYS_ROOT}/include"
                    "${IMGUI_SYS_ROOT}/include/backends")
target_link_libraries(imgui_glfw PUBLIC glfw)

install(
  TARGETS imgui_glfw
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
