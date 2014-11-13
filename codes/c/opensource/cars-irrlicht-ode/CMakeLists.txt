cmake_minimum_required(VERSION 2.8.7)

project(car-irrlicht-ode)
set(SRCS src/cars/Car.cpp src/cars/car_test.cpp src/cars/GameContext.cpp src/cars/GUI.cpp src/cars/PhysicAnimator.cpp src/cars/World.cpp)

add_executable(car-irrlicht-ode ${SRCS})
include_directories(include/)

#find_package(PkgConfig)
if(WIN32)
	set(CMAKE_MODULE_PATH "${PROJECT_SOURCE_DIR}")

	message("finding irrlicht libraries")
	set(ENV{IRRLICHTDIR} external/irrlicht-1.7.2) 
	find_package(Irrlicht)
	include_directories(${IRRLICHT_INCLUDE_DIR})
	target_link_libraries(car-irrlicht-ode ${IRRLICHT_LIBRARY})

	message("finding ode libraries")
	set(ENV{ODEDIR} external/ode-0.11.1) 
	find_package(Ode)
	include_directories(${ODE_INCLUDE_DIR})
	target_link_libraries(car-irrlicht-ode ${ODE_LIBRARY})
elseif(UNIX)
	message("PROJECT_SOURCE_DIR:" ${PROJECT_SOURCE_DIR})
endif()

