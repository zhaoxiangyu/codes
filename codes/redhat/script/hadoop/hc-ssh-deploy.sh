#!/bin/bash
SRC_PATH=$HADOOP_HOME/conf/slaves
for srv in $(cat $SRC_PATH); do
  echo "transfering authorized_keys to $srv..."
  scp ~/.ssh/authorized_keys $srv:~/.ssh/authorized_keys
done
