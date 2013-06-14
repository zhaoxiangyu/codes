cmake_minimum_required(VERSION 2.8.7)
project(wxMedia)

##################################################### libs #################################

find_package(wxWidgets COMPONENTS net gl core base REQUIRED)
INCLUDE(FindSDL)
# find_package(OpenCV REQUIRED )

##################################################### wxMedia APP ##########################

set(SRCS ../src/ui/wxMediaApp.cpp ../src/ui/wxMediaMain.cpp ../src/ui/wxSDLPanel.cpp)
add_executable(wxMedia ${SRCS})

# include_directories(${OpenCV_INCLUDE_DIR})
# link_directories(${OpenCV_LIBRARY_DIR})

IF(wxWidgets_FOUND)
	include( "${wxWidgets_USE_FILE}" )
	target_link_libraries(wxMedia ${wxWidgets_LIBRARIES})
ENDIF(wxWidgets_FOUND)

IF(SDL_FOUND)
	INCLUDE_DIRECTORIES(${SDL_INCLUDE_DIR})
	target_link_libraries(wxMedia ${SDL_LIBRARY})
ELSE(SDL_FOUND)
    MESSAGE("SDL not found")
ENDIF(SDL_FOUND)

###################################################### SDL APP ##############################

set(SDL_SRCS ../src/ui/wxSDLApp.cpp)
add_executable(wxSDL ${SDL_SRCS})

IF(wxWidgets_FOUND)
	target_link_libraries(wxSDL ${wxWidgets_LIBRARIES})
ENDIF(wxWidgets_FOUND)

INCLUDE(FindSDL)
IF(SDL_FOUND)
	INCLUDE_DIRECTORIES(${SDL_INCLUDE_DIR})
	target_link_libraries(wxSDL ${SDL_LIBRARY})
ELSE(SDL_FOUND)
    MESSAGE("SDL not found")
ENDIF(SDL_FOUND)

###################################################### ffmpeg APP ##############################

#set(FFM_SRCS ../src/core/ffmpeg/useFfmpeg.c)
#set(FFM_SRCS ../src/core/ffmpeg/sdlPlayer.c)
#set(FFM_SRCS ../src/core/ffmpeg/withAudio.c)
#set(FFM_SRCS ../src/core/ffmpeg/spawnThread.c)
set(FFM_SRCS ../src/core/ffmpeg/syncVideo.c)
add_executable(myFfmpeg ${FFM_SRCS})

IF(SDL_FOUND)
	INCLUDE_DIRECTORIES(${SDL_INCLUDE_DIR})
	target_link_libraries(myFfmpeg ${SDL_LIBRARY})
ELSE(SDL_FOUND)
    MESSAGE("SDL not found")
ENDIF(SDL_FOUND)

find_path( AVCODEC_INCLUDE_DIR
    NAMES libavcodec/avcodec.h
    PATH_SUFFIXES include
    PATHS
    /usr/local/include
    /usr/include
    /opt/local/include
    /opt/include
)

find_library( AVCODEC_LIBRARY
    NAMES avcodec
    PATH_SUFFIXES lib64 lib bin
    PATHS
    /usr/local
    /usr
    /opt/local
    /opt
)

include_directories( ${AVCODEC_INCLUDE_DIR} )
target_link_libraries(myFfmpeg ${AVCODEC_LIBRARY})

find_path( AVFORMAT_INCLUDE_DIR
    NAMES libavformat/avformat.h
    PATH_SUFFIXES include
    PATHS
    /usr/local/include
    /usr/include
    /opt/local/include
    /opt/include
)

find_library( AVFORMAT_LIBRARY
    NAMES avformat
    PATH_SUFFIXES lib64 lib bin
    PATHS
    /usr/local
    /usr
    /opt/local
    /opt
)

include_directories( ${AVFORMAT_INCLUDE_DIR} )
target_link_libraries(myFfmpeg ${AVFORMAT_LIBRARY})

find_path( SWSCALE_INCLUDE_DIR
    NAMES libswscale/swscale.h
    PATH_SUFFIXES include
    PATHS
    /usr/local/include
    /usr/include
    /opt/local/include
    /opt/include
)

find_library( SWSCALE_LIBRARY
    NAMES swscale
    PATH_SUFFIXES lib64 lib bin
    PATHS
    /usr/local
    /usr
    /opt/local
    /opt
)

include_directories( ${SWSCALE_INCLUDE_DIR} )
target_link_libraries(myFfmpeg ${SWSCALE_LIBRARY})

##############################################################################################

# include_directories(${EXAMPLE_MARKERLESS_AR_SOURCE_DIR}/src)
# add_subdirectory(src)
