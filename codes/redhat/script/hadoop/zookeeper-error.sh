#!/bin/bash
set -x

HBASE_LOG_DIR=$HBASE_HOME/logs
USERNAME=`id -un`
HOSTNAME=`hostname`
ZK_LOG_FILE=$HBASE_LOG_DIR/hbase-$USERNAME-zookeeper-$HOSTNAME.log

test -f ~/zookeeper.out && grep -m1 -e ERROR -e FATAL ~/zookeeper.out
test -f $ZK_LOG_FILE && grep -e ERROR -e FATAL $ZK_LOG_FILE || grep -e WARN $ZK_LOG_FILE
