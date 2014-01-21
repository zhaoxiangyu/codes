#!/bin/bash
set -x

SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----remove logs on host: $host-----"; 
  ssh $host 'source /etc/profile; rm -r $HADOOP_HOME/logs/userlogs'
done

rm -r $HADOOP_HOME/logs/userlogs
