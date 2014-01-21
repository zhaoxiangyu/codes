#!/bin/bash
# Rsyncs HBase files across all slaves. Must run on master. Assumes
# all files are located in /usr/local
HADOOP_INSTALL_DIR=$(dirname $HADOOP_HOME)
HADOOP_DIST_NAME=$(basename $HADOOP_HOME)

SRC_PATH="$HADOOP_HOME/conf/slaves"
for srv in $(cat $SRC_PATH); do
  echo "Sending command to $srv..."; 
  rsync -vaz --exclude='logs/*' --exclude='pids/*' $HADOOP_HOME $srv:$HADOOP_INSTALL_DIR
  ssh $srv "rm -fR $HADOOP_INSTALL_DIR/hadoop ; ln -s $HADOOP_HOME $HADOOP_INSTALL_DIR/hadoop"
done
echo "done."
