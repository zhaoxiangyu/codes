#!/bin/bash
set -x

SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----remove logs on host: $host-----"; 
  ssh $host "source /etc/profile; i/hadoop-cl.sh"
done

HADOOP_LOG_DIR=$HADOOP_HOME/logs
username=`id -un`
hostname=`hostname`

test -f $HADOOP_LOG_DIR/hadoop-$username-namenode-$hostname.log && rm $HADOOP_LOG_DIR/hadoop-$username-namenode-$hostname.log
