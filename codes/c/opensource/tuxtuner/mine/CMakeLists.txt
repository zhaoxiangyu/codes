cmake_minimum_required(VERSION 2.8.7)

project(tuxtuner)
set(SRCS ../include/kiss_fftr.c ../include/kiss_fft.c ../include/RtAudio.cpp ../main.cpp)

add_executable(tuxtuner ${SRCS})
include_directories(${PROJECT_SOURCE_DIR}/../include)

find_package(PkgConfig)
if(WIN32)
	pkg_search_module(SDL2 REQUIRED sdl2)
	include_directories(${SDL2_INCLUDE_DIRS})
	target_link_libraries(racecar ${SDL2_LIBRARIES})
elseif(UNIX)
	message("PROJECT_SOURCE_DIR:" ${PROJECT_SOURCE_DIR})
endif()

