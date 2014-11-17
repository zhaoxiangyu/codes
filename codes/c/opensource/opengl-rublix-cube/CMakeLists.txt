cmake_minimum_required(VERSION 2.8.7)

project(opengl-rubix-cube)
set(SRCS src/game/Camera.cpp src/game/Entity.cpp src/game/Game.cpp src/game/InitShader.cpp src/game/resource/Face.cpp src/game/resource/MtlLoader.cpp src/game/resource/ObjLoader.cpp src/game/resource/ResMgr.cpp src/Main.cpp src/samples/meshviewer/MeshViewer.cpp src/samples/rubix/RubixCube.cpp src/samples/rubix/SmallCube.cpp)

add_executable(opengl-rubix-cub ${SRCS})
include_directories(include/)

message(STATUS "PROJECT_SOURCE_DIR:" ${PROJECT_SOURCE_DIR})
if(WIN32)
	message("Using opengl and glut")
elseif(UNIX)
endif()
