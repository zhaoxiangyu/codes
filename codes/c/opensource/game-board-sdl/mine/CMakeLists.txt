cmake_minimum_required(VERSION 2.8.7)

project(bejeweled)
set(SRCS ../BoardGame/SoundManager.cpp ../BoardGame/main.cpp ../BoardGame/Tint.cpp ../BoardGame/Game.cpp ../BoardGame/LoadAssets.cpp ../BoardGame/SMLog.cpp)

add_executable(bejeweled ${SRCS})

find_package(PkgConfig)
if(WIN32)
	pkg_search_module(SDL2 REQUIRED sdl2)
	include_directories(${SDL2_INCLUDE_DIRS})
	target_link_libraries(racecar ${SDL2_LIBRARIES})
elseif(UNIX)
endif()
