#!/bin/bash
HADOOP_INSTALL_DIR=$(dirname $HADOOP_HOME)
HADOOP_DIST_NAME=$(basename $HADOOP_HOME)

SRC_PATH="$HADOOP_HOME/conf/slaves"
for srv in $(cat $SRC_PATH); do
  echo "Sending command to $srv..."; 
  sudo rsync /etc/profile $srv:/etc/profile
  ssh $srv 'source /etc/profile'
  rsync -vaz --delete $HADOOP_HOME $srv:$HADOOP_INSTALL_DIR
done
