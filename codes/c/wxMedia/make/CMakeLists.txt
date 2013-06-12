cmake_minimum_required(VERSION 2.8.7)
project(wxMedia)

# find_package(OpenCV REQUIRED )
# include_directories(${OpenCV_INCLUDE_DIR})
# link_directories(${OpenCV_LIBRARY_DIR})

find_package(wxWidgets COMPONENTS core base REQUIRED)
include( "${wxWidgets_USE_FILE}" )

set(SRCS ../wxMediaApp.cpp ../wxMediaMain.cpp)
add_executable(wxMedia ${SRCS})
target_link_libraries(wxMedia ${wxWidgets_LIBRARIES})

# include_directories(${EXAMPLE_MARKERLESS_AR_SOURCE_DIR}/src)
# add_subdirectory(src)
