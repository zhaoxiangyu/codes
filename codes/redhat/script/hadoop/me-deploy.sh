#!/bin/bash
ME_HOME=$PWD
ME_INSTALL_DIR=$(dirname $ME_HOME)
ME_DIST_NAME=$(basename $ME_HOME)

if [ "$#" != "0" ]; then
  echo "usage: $(basename $0) <dir-name> <ln-name>"
  echo "    example: $(basename $0) hadoopxxx hadoop"
  exit 1
fi
SRC_PATH="$HADOOP_HOME/conf/slaves"
for srv in $(cat $SRC_PATH); do
  echo "Sending command to $srv..."; 
  sudo rsync -vaz --delete --exclude='*std*' --exclude='downloads' $ME_HOME $srv:/$ME_INSTALL_DIR
done
