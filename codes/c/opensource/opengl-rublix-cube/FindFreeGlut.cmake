# Locate FREEGLUT library
# This module defines
# FREEGLUT_LIBRARY, the name of the library to link against
# FREEGLUT_FOUND, if false, do not try to link to FREEGLUT
# FREEGLUT_INCLUDE_DIR, where to find irrlicht.h
#

set( FREEGLUT_FOUND "NO" )

find_path( FREEGLUT_INCLUDE_DIR GL/freeglut.h GL/freeglut_ext.h
  HINTS
  $ENV{FREEGLUTDIR}
  PATH_SUFFIXES include
  PATHS
  /usr/local/include
  /usr/include
  /opt/local/include
  /opt/csw/include
  /opt/include
)

#message( "FREEGLUT_INCLUDE_DIR is ${FREEGLUT_INCLUDE_DIR}" )

if(WIN32)
	find_library( FREEGLUT_LIBRARY
	  NAMES freeglut
	  HINTS
	  $ENV{FREEGLUTDIR}
	  PATH_SUFFIXES lib64 lib bin
	  PATHS
	  /usr/local
	  /usr
	  /opt/local
	  /opt/csw
	  /opt
	)
elseif(UNIX)
	find_library( FREEGLUT_LIBRARY
	  NAMES glut
	  HINTS
	  $ENV{FREEGLUTDIR}
	  PATH_SUFFIXES lib64 lib bin lib/x86_64-linux-gnu
	  PATHS
	  /usr/local
	  /usr
	  /opt/local
	  /opt/csw
	  /opt
	)
endif()
#message( "FREEGLUT_LIBRARY is ${FREEGLUT_LIBRARY}" )

set( FREEGLUT_FOUND "YES" )

#message( "FREEGLUT_LIBRARY is ${FREEGLUT_LIBRARY}" )
