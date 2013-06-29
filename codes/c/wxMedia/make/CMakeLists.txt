cmake_minimum_required(VERSION 2.8.7)
project(wxMedia)
INCLUDE( FindPkgConfig )
##################################################### libs #################################

find_package( wxWidgets COMPONENTS net gl core base REQUIRED )
#INCLUDE( FindSDL )
find_package( SDL REQUIRED )

find_package( GTK2 2.8 COMPONENTS gtk )

# pkg-config --modversion gstreamer-1.0
pkg_check_modules( GST REQUIRED gstreamer-1.0>=1.0.6)
pkg_check_modules( GST_INTF REQUIRED gstreamer-interfaces-0.10)

# find_package(OpenCV REQUIRED )
pkg_check_modules( OPENCV REQUIRED opencv>=2.3.1)

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

find_path( VLC_INCLUDE_DIR
    NAMES vlc/vlc.h
    PATH_SUFFIXES include
    PATHS
    /usr/local/include
    /usr/include
    /opt/local/include
    /opt/include
)
find_library( VLC_LIBRARY
    NAMES vlc
    PATH_SUFFIXES lib64 lib bin
    PATHS
    /usr/local
    /usr
    /opt/local
    /opt
)


##################################################### wxMedia APP ##########################

set(SRCS ../src/ui/wxMediaApp.cpp ../src/ui/wxMediaMain.cpp ../src/ui/wxSDLPanel.cpp ../src/ui/sdl/wxSDLSurfacePanel.cpp ../src/ui/wxGstPanel.cpp ../src/core/gst/gstLoader.cpp
	../src/ui/wxCVPanel.cpp)
add_executable(wxMedia ${SRCS})

# include_directories(${OpenCV_INCLUDE_DIR})
# link_directories(${OpenCV_LIBRARY_DIR})

IF(wxWidgets_FOUND)
	include( ${wxWidgets_USE_FILE} )
	target_link_libraries(wxMedia ${wxWidgets_LIBRARIES})
ENDIF(wxWidgets_FOUND)

IF(SDL_FOUND)
	INCLUDE_DIRECTORIES(${SDL_INCLUDE_DIR})
	target_link_libraries(wxMedia ${SDL_LIBRARY})
ELSE(SDL_FOUND)
    MESSAGE("SDL not found")
ENDIF(SDL_FOUND)

IF(GST_FOUND)
	INCLUDE_DIRECTORIES(${GST_INCLUDE_DIRS})
	target_link_libraries(wxMedia ${GST_LIBRARIES})
ELSE(GST_FOUND)
    MESSAGE("GST not found")
ENDIF(GST_FOUND)

IF(GST_INTF_FOUND)
	INCLUDE_DIRECTORIES(${GST_INTF_INCLUDE_DIRS})
	target_link_libraries(wxMedia ${GST_INTF_LIBRARIES})
ELSE(GST_INTF_FOUND)
    MESSAGE("GST_INTF not found")
ENDIF(GST_INTF_FOUND)

IF(OPENCV_FOUND)
	INCLUDE_DIRECTORIES(${OPENCV_INCLUDE_DIRS})
	target_link_libraries(wxMedia ${OPENCV_LIBRARIES})
ELSE(OPENCV_FOUND)
    MESSAGE("OPENCV not found")
ENDIF(OPENCV_FOUND)

include_directories( ${AVCODEC_INCLUDE_DIR} )
MESSAGE("wxMedia: ${AVCODEC_LIBRARY}")
target_link_libraries(wxMedia ${AVCODEC_LIBRARY})

include_directories( ${AVFORMAT_INCLUDE_DIR} )
target_link_libraries(wxMedia ${AVFORMAT_LIBRARY})

include_directories( ${SWSCALE_INCLUDE_DIR} )
target_link_libraries(wxMedia ${SWSCALE_LIBRARY})

###################################################### testLib APP ##############################

#set(FFM_SRCS ../src/core/ffmpeg/useFfmpeg.c)
#set(FFM_SRCS ../src/core/ffmpeg/sdlPlayer.c)
#set(FFM_SRCS ../src/core/ffmpeg/withAudio.c)
#set(FFM_SRCS ../src/core/ffmpeg/spawnThread.c)
set(FFM_SRCS ../src/core/ffmpeg/syncVideo.c)
#set(FFM_SRCS ../src/core/vlc/vlcPlayer.c)
add_executable(testLib ${FFM_SRCS})

IF(SDL_FOUND)
	INCLUDE_DIRECTORIES(${SDL_INCLUDE_DIR})
	target_link_libraries(testLib ${SDL_LIBRARY})
ELSE(SDL_FOUND)
    MESSAGE("SDL not found")
ENDIF(SDL_FOUND)

include_directories( ${AVCODEC_INCLUDE_DIR} )
#MESSAGE("testLib: ${AVCODEC_INCLUDE_DIR}")
target_link_libraries(testLib ${AVCODEC_LIBRARY})

include_directories( ${AVFORMAT_INCLUDE_DIR} )
target_link_libraries(testLib ${AVFORMAT_LIBRARY})

include_directories( ${SWSCALE_INCLUDE_DIR} )
target_link_libraries(testLib ${SWSCALE_LIBRARY})

include_directories( ${VLC_INCLUDE_DIR} )
target_link_libraries(testLib ${VLC_LIBRARY})

#################################### gtk gstreamer player ####################################

#set(GTK_GST_SRCS ../src/ui/gtk+/gstPlayer.c)
set(GTK_GST_SRCS ../src/core/gst/gstLoader.cpp ../src/ui/gtk+/gtkPlayer.cpp)
add_executable(gtkGstPlayer ${GTK_GST_SRCS})

IF(GTK2_FOUND)
	INCLUDE_DIRECTORIES(${GTK2_INCLUDE_DIRS})
	target_link_libraries(gtkGstPlayer ${GTK2_LIBRARIES})
ELSE(GTK2_FOUND)
    MESSAGE("GTK not found")
ENDIF(GTK2_FOUND)

IF(GST_FOUND)
	INCLUDE_DIRECTORIES(${GST_INCLUDE_DIRS})
	target_link_libraries(gtkGstPlayer ${GST_LIBRARIES})
ELSE(GST_FOUND)
    MESSAGE("GST not found")
ENDIF(GST_FOUND)

IF(GST_INTF_FOUND)
	INCLUDE_DIRECTORIES(${GST_INTF_INCLUDE_DIRS})
	target_link_libraries(gtkGstPlayer ${GST_INTF_LIBRARIES})
ELSE(GST_INTF_FOUND)
    MESSAGE("GST_INTF not found")
ENDIF(GST_INTF_FOUND)

##############################################################################################

# include_directories(${EXAMPLE_MARKERLESS_AR_SOURCE_DIR}/src)
# add_subdirectory(src)
