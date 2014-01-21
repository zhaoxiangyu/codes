#!/bin/bash
HBASE_INSTALL_DIR=$(dirname $HBASE_HOME)
HBASE_DIST_NAME=$(basename $HBASE_HOME)

SRC_PATH="$HBASE_HOME/conf/regionservers"
for srv in $(cat $SRC_PATH); do
  echo "Sending command to $srv..."; 
  sudo rsync /etc/profile $srv:/etc/profile
  ssh $srv 'source /etc/profile'
  rsync -vaz --delete $HBASE_HOME $srv:$HBASE_INSTALL_DIR
done
