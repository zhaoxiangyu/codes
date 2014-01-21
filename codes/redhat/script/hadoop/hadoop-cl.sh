#!/bin/bash
set -x

HADOOP_LOG_DIR=$HADOOP_HOME/logs
username=`id -un`
hostname=`hostname`

test -f $HADOOP_LOG_DIR/hadoop-$username-datanode-$hostname.log && rm $HADOOP_LOG_DIR/hadoop-$username-datanode-$hostname.log
