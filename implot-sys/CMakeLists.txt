cmake_minimum_required(VERSION 3.28)

project(implot-sys LANGUAGES CXX)

set(IMGUI_SYS_ROOT
    ""
    CACHE STRING "Directory of imgui-sys install")
if(NOT IMGUI_SYS_ROOT)
  message(FATAL_ERROR "Must specify -DIMGUI_SYS_ROOT=...")
endif()

add_library(implot STATIC lib/implot/implot.cpp lib/implot/implot_demo.cpp
                          lib/implot/implot_items.cpp)
target_include_directories(implot PUBLIC "${IMGUI_SYS_ROOT}/include")
target_link_directories(implot PUBLIC "${IMGUI_SYS_ROOT}/lib")
target_link_libraries(implot PUBLIC imgui)

install(
  TARGETS implot
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
install(FILES lib/implot/implot.h lib/implot/implot_internal.h
        DESTINATION include/)
