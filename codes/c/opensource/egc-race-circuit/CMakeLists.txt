cmake_minimum_required(VERSION 2.8.7)

project(race-circuit)
#set(RTAUDIO_DIR E:/opensource/rtaudio-4.1.1 CACHE PATH "rtaudio source directory")
set(SRCS Camera.cpp Car.cpp Cube.cpp Lamp.cpp Light.cpp NFS.cpp Object3D.cpp Obstacle.cpp Plane.cpp RectanglePrism.cpp)

add_executable(race-circuit ${SRCS})
#include_directories(include/)

#find_package(PkgConfig)
if(WIN32)
	message("Using opengl and glut")
	find_package(Glut)
	#add_definitions(-D__WINDOWS_DS__)
	if(GLUT_FOUND) 
		include_directories(${GLUT_INCLUDE_DIR})
		target_link_libraries(race-circuit ${GLUT_LIBRARIES})
	else()
		message(FATAL_ERROR "glut not found")
	endif()
elseif(UNIX)
	message(STATUS "PROJECT_SOURCE_DIR:" ${PROJECT_SOURCE_DIR})
endif()

