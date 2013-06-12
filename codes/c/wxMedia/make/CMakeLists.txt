cmake_minimum_required(VERSION 2.8.7)
project(wxMedia)

set(SRCS ../src/ui/wxMediaApp.cpp ../src/ui/wxMediaMain.cpp)
add_executable(wxMedia ${SRCS})

# find_package(OpenCV REQUIRED )
# include_directories(${OpenCV_INCLUDE_DIR})
# link_directories(${OpenCV_LIBRARY_DIR})

find_package(wxWidgets COMPONENTS net gl core base REQUIRED)
IF(wxWidgets_FOUND)
	include( "${wxWidgets_USE_FILE}" )
	target_link_libraries(wxMedia ${wxWidgets_LIBRARIES})
ENDIF(wxWidgets_FOUND)

# include_directories(${EXAMPLE_MARKERLESS_AR_SOURCE_DIR}/src)
# add_subdirectory(src)
