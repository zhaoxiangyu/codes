#!/bin/bash
set -x

HBASE_LOG_DIR=$HBASE_HOME/logs
USERNAME=`id -un`
HOSTNAME=`hostname`

test -f ~/zookeeper.out && grep -m1 WARN ~/zookeeper.out
test -f $HBASE_LOG_DIR/hbase-$USERNAME-zookeeper-$HOSTNAME.log && grep -e WARN $HBASE_LOG_DIR/hbase-$USERNAME-zookeeper-$HOSTNAME.log
