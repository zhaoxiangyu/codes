#!/bin/bash
set -x

SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "====================$host===================="
  ssh $host 'source /etc/profile; ~/i/hadoop-error.sh'
done
echo "====================namenode===================="
./hadoop-error.sh
