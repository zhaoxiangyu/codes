#!/bin/bash
set -x

SSH_KILL_REGIONSERVER=`./hc-jps.sh|awk '/host:/{host=$5;}/HRegionServer/{print "ssh " host " kill " $1 ";";}'`

if [ -z "$SSH_KILL_REGIONSERVER" ]; then
  echo HRegionServer not found
else
  eval $SSH_KILL_REGIONSERVER
fi
