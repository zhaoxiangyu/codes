#!/bin/bash

HBASE_LOG_DIR=$HBASE_HOME/logs
USERNAME=`id -un`
HOSTNAME=`hostname`

echo $HBASE_LOG_DIR/hbase-$USERNAME-zookeeper-$HOSTNAME.log
