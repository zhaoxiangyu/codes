#!/bin/bash
set -x

SSH_KILL_ZOOKEEPER=`./hc-jps.sh|awk '/host:/{host=$5;}/HQuorumPeer/{print "ssh " host " kill " $1 ";";}'`

if [ -z "$SSH_KILL_ZOOKEEPER" ]; then
  echo ZooKeeper not found
else
  eval $SSH_KILL_ZOOKEEPER
fi
