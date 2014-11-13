# Locate ODE library
# This module defines
# ODE_LIBRARY, the name of the library to link against
# ODE_FOUND, if false, do not try to link to ODE
# ODE_INCLUDE_DIR, where to find ode.h
#

set( ODE_FOUND "NO" )

find_path( ODE_INCLUDE_DIR ode.h
  HINTS
  $ENV{ODEDIR}
  PATH_SUFFIXES include
  PATHS
  /usr/local/include
  /usr/include
  /opt/local/include
  /opt/csw/include
  /opt/include
)

#message( "ODE_INCLUDE_DIR is ${ODE_INCLUDE_DIR}" )

find_library( ODE_LIBRARY
  NAMES ode_single
  HINTS
  $ENV{ODEDIR}
  PATH_SUFFIXES lib64 lib bin ReleaseSingleDLL
  PATHS
  /usr/local
  /usr
  /opt/local
  /opt/csw
  /opt
)

#message( "ODE_LIBRARY is ${ODE_LIBRARY}" )

set( ODE_FOUND "YES" )

#message( "ODE_LIBRARY is ${ODE_LIBRARY}" )
