cmake_minimum_required(VERSION 2.8.7)

project(opengl-rubix-cube)
set(SRCS src/game/Camera.cpp src/game/Entity.cpp src/game/Game.cpp src/game/InitShader.cpp src/game/resource/Face.cpp src/game/resource/MtlLoader.cpp src/game/resource/ObjLoader.cpp src/game/resource/ResMgr.cpp src/Main.cpp src/samples/meshviewer/MeshViewer.cpp src/samples/rubix/RubixCube.cpp src/samples/rubix/SmallCube.cpp)

add_executable(opengl-rubix-cube ${SRCS})
include_directories(include/)

message(STATUS "PROJECT_SOURCE_DIR:" ${PROJECT_SOURCE_DIR})
set(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}")
if(WIN32)
	set(ENV{GLEWDIR} e:/opensource/glew-1.11.0-win32/glew-1.11.0) 
	set(ENV{FREEGLUTDIR} e:/opensource/freeglut-2.8.1) 

	message("finding glew libraries")
	find_package(Glew REQUIRED)
	include_directories(${GLEW_INCLUDE_DIR})
	target_link_libraries(opengl-rubix-cube ${GLEW_LIBRARY})

	message("finding freeglut libraries")
	find_package(FreeGlut REQUIRED)
	include_directories(${FREEGLUT_INCLUDE_DIR})
	target_link_libraries(opengl-rubix-cube ${FREEGLUT_LIBRARY})
elseif(UNIX)
	include(FindPkgConfig)

	message("finding glew libraries")
	pkg_search_module(GLEW REQUIRED glew)
	include_directories(${GLEW_INCLUDE_DIRS})
	target_link_libraries(opengl-rubix-cube ${GLEW_LIBRARIES})

	message("finding freeglut libraries")
	find_package(FreeGlut REQUIRED)
	include_directories(${FREEGLUT_INCLUDE_DIR})
	target_link_libraries(opengl-rubix-cube ${FREEGLUT_LIBRARY})

	message("finding opengl libraries")
	find_package(OpenGL REQUIRED)
	#include_directories(${FREEGLUT_INCLUDE_DIR})
	target_link_libraries(opengl-rubix-cube ${OPENGL_gl_LIBRARY})
endif()

