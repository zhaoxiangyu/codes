# Locate IRRLICHT library
# This module defines
# IRRLICHT_LIBRARY, the name of the library to link against
# IRRLICHT_FOUND, if false, do not try to link to IRRLICHT
# IRRLICHT_INCLUDE_DIR, where to find irrlicht.h
#

set( IRRLICHT_FOUND "NO" )

find_path( IRRLICHT_INCLUDE_DIR irrlicht.h
  HINTS
  $ENV{IRRLICHTDIR}
  PATH_SUFFIXES include
  PATHS
  /usr/local/include
  /usr/include
  /opt/local/include
  /opt/csw/include
  /opt/include
)

#message( "IRRLICHT_INCLUDE_DIR is ${IRRLICHT_INCLUDE_DIR}" )

find_library( IRRLICHT_LIBRARY
  NAMES Irrlicht
  HINTS
  $ENV{IRRLICHTDIR}
  PATH_SUFFIXES lib64 lib bin Win32-visualstudio
  PATHS
  /usr/local
  /usr
  /opt/local
  /opt/csw
  /opt
)

#message( "IRRLICHT_LIBRARY is ${IRRLICHT_LIBRARY}" )

set( IRRLICHT_FOUND "YES" )

#message( "IRRLICHT_LIBRARY is ${IRRLICHT_LIBRARY}" )
