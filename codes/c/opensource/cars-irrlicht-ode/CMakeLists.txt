cmake_minimum_required(VERSION 2.8.7)

project(car-irrlicht-ode)
set(SRCS src/cars/Car.cpp src/cars/car_test.cpp src/cars/GameContext.cpp src/cars/GUI.cpp src/cars/PhysicAnimator.cpp src/cars/World.cpp)

add_executable(car-irrlicht-ode ${SRCS})
include_directories(include/)

#find_package(PkgConfig)
if(WIN32)
	message("Using directX libraries")
	add_definitions(-D__WINDOWS_DS__)
	set(LINKLIBS)
	list(APPEND LINKLIBS winmm ole32 dsound)
	target_link_libraries(car-irrlicht-ode ${LINKLIBS})
elseif(UNIX)
	message("PROJECT_SOURCE_DIR:" ${PROJECT_SOURCE_DIR})
endif()

