#!/bin/bash
set -x

SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "====================$host===================="
  ssh $host 'source /etc/profile; ~/i/hadoop-perf.sh'
done
