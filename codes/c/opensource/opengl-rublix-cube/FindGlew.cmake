# Locate GLEW library
# This module defines
# GLEW_LIBRARY, the name of the library to link against
# GLEW_FOUND, if false, do not try to link to GLEW
# GLEW_INCLUDE_DIR, where to find irrlicht.h
#

set( GLEW_FOUND "NO" )

find_path( GLEW_INCLUDE_DIR GL/glew.h
  HINTS
  $ENV{GLEWDIR}
  PATH_SUFFIXES include
  PATHS
  /usr/local/include
  /usr/include
  /opt/local/include
  /opt/csw/include
  /opt/include
)

#message( "GLEW_INCLUDE_DIR is ${GLEW_INCLUDE_DIR}" )

find_library( GLEW_LIBRARY
  NAMES glew32
  HINTS
  $ENV{GLEWDIR}
  PATH_SUFFIXES lib/Release/Win32
  PATHS
  /usr/local
  /usr
  /opt/local
  /opt/csw
  /opt
)

#message( "GLEW_LIBRARY is ${GLEW_LIBRARY}" )

set( GLEW_FOUND "YES" )

#message( "GLEW_LIBRARY is ${GLEW_LIBRARY}" )
