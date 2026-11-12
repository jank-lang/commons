cmake_minimum_required(VERSION 3.28)

project(flecs-sys LANGUAGES C)

add_library(flecs STATIC lib/flecs/distr/flecs.c)
target_include_directories(flecs PUBLIC lib/flecs/distr)
target_compile_definitions(flecs PUBLIC ${FLECS_MODE})
set_target_properties(flecs PROPERTIES C_STANDARD 99
                                       POSITION_INDEPENDENT_CODE ON)

install(
  TARGETS flecs
  LIBRARY DESTINATION lib
  ARCHIVE DESTINATION lib)
install(FILES lib/flecs/distr/flecs.h DESTINATION include)
