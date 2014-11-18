cmake_minimum_required(VERSION 2.8.7)

project(tuxtuner)
set(RTAUDIO_DIR E:/opensource/rtaudio-4.1.1 CACHE PATH "rtaudio source directory")
set(SRCS include/kiss_fftr.c include/kiss_fft.c ${RTAUDIO_DIR}/RtAudio.cpp main.cpp)

add_executable(tuxtuner ${SRCS})
include_directories(${RTAUDIO_DIR})
include_directories(${RTAUDIO_DIR}/include/)
include_directories(include/)

#find_package(PkgConfig)
if(WIN32)
	message("Using directX libraries")
	add_definitions(-D__WINDOWS_DS__)
	set(LINKLIBS)
	list(APPEND LINKLIBS winmm ole32 dsound)
	target_link_libraries(tuxtuner ${LINKLIBS})
elseif(UNIX)
	message("PROJECT_SOURCE_DIR:" ${PROJECT_SOURCE_DIR})
endif()

