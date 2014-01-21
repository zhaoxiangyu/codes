#!/bin/bash
set -x

HADOOP_LOG_DIR=$HADOOP_HOME/logs
username=`id -un`
hostname=`hostname`

test -f $HADOOP_LOG_DIR/hadoop-$username-datanode-$hostname.log && grep -m1 -e 'Could not obtain block' $HADOOP_LOG_DIR/hadoop-$username-datanode-$hostname.log
