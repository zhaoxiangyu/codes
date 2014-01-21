#!/bin/bash
set -x

ZOOKEEPER_INSTALL_DIR=$(dirname $ZOOKEEPER_HOME)
ZOOKEEPER_DIST_NAME=$(basename $ZOOKEEPER_HOME)

SRC_PATH="zookeeper-servers"
serverid=1
for srv in $(cat $SRC_PATH); do
  rsync -vaz $ZOOKEEPER_HOME/conf $srv:/$ZOOKEEPER_HOME
  #ssh $srv "mkdir -p $ZOOKEEPER_HOME/server/data; echo $((serverid++)) >$ZOOKEEPER_HOME/server/data/myid"
  ssh $srv "mkdir -p ~/data2/zookeeper-data; echo $((serverid++)) >~/data2/zookeeper-data/myid"
done
