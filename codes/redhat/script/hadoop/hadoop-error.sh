#!/bin/bash
set -x

HADOOP_LOG_DIR=$HADOOP_HOME/logs
username=`id -un`
hostname=`hostname`
NAMENODE_LOG_FILE=$HADOOP_LOG_DIR/hadoop-$username-namenode-$hostname.log

#ls -t $HADOOP_LOG_DIR/hadoop-$username-namenode* | head -n3
test -f $NAMENODE_LOG_FILE && grep -m1 -e FATAL $NAMENODE_LOG_FILE || grep -m1 -e ERROR $NAMENODE_LOG_FILE || grep -m1 -e WARN $NAMENODE_LOG_FILE

DATANODE_LOG_FILE=$HADOOP_LOG_DIR/hadoop-$username-datanode-$hostname.log
#ls -t $HADOOP_LOG_DIR/hadoop-$username-datanode* | head -n3
test -f $DATANODE_LOG_FILE && grep -m1 -e FATAL $DATANODE_LOG_FILE || grep -m1 -e ERROR $DATANODE_LOG_FILE || grep -m1 -e WARN $DATANODE_LOG_FILE

find $HADOOP_LOG_DIR -name syslog|xargs grep -m1 -e ERROR -e FATAL
