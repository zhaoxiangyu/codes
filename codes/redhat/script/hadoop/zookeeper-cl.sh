#!/bin/bash
set -x

HBASE_LOG_DIR=$HBASE_HOME/logs
USERNAME=`id -un`
HOSTNAME=`hostname`

test -f ~/zookeeper.out && rm ~/zookeeper.out
test -f $HBASE_LOG_DIR/hbase-$USERNAME-zookeeper-$HOSTNAME.log && rm $HBASE_LOG_DIR/hbase-$USERNAME-zookeeper-$HOSTNAME.log
