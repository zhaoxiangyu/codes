cmake_minimum_required(VERSION 2.8.7)

project(opengl-rubix-cube)
set(SRCS src/game/Camera.cpp src/game/Entity.cpp src/game/Game.cpp src/game/InitShader.cpp src/game/resource/Face.cpp src/game/resource/MtlLoader.cpp src/game/resource/ObjLoader.cpp src/game/resource/ResMgr.cpp src/Main.cpp src/samples/meshviewer/MeshViewer.cpp src/samples/rubix/RubixCube.cpp src/samples/rubix/SmallCube.cpp)

add_executable(opengl-rubix-cube ${SRCS})
include_directories(include/)

message(STATUS "PROJECT_SOURCE_DIR:" ${PROJECT_SOURCE_DIR})
if(WIN32)
	set(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}")

	message("finding glew libraries")
	#set(ENV{GLEWDIR} e:/opensource/glew-1.11.0-win32/glew-1.11.0) 
	set(ENV{GLEWDIR} e:/opensource/glew-1.11.0) 
	find_package(Glew)
	include_directories(${GLEW_INCLUDE_DIR})
	target_link_libraries(opengl-rubix-cube ${GLEW_LIBRARY})

	message("finding freeglut libraries")
	#set(ENV{FREEGLUTDIR} e:/opensource/freeglut-2.8.1) 
	set(ENV{FREEGLUTDIR} e:/opensource/freeglut-MinGW-2.8.1-1.mp/freeglut) 
	find_package(FreeGlut)
	include_directories(${FREEGLUT_INCLUDE_DIR})
	target_link_libraries(opengl-rubix-cube ${FREEGLUT_LIBRARY})
elseif(UNIX)
	message("building on unix")
endif()
