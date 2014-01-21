#!/bin/bash
set -x

SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----clear hadoop data on host: $host-----"; 
  ssh $host "source /etc/profile; i/hadoop-uninst.sh"
done
./hadoop-uninst.sh
