#!/bin/bash
# Rsyncs HBase files across all slaves. Must run on master. Assumes
# all files are located in /usr/local
set -x

HBASE_INSTALL_DIR=$(dirname $HBASE_HOME)
HBASE_DIST_NAME=$(basename $HBASE_HOME)

if [ "$#" != "0" ]; then
  echo "usage: $(basename $0) <dir-name> <ln-name>"
  echo "    example: $(basename $0) hbase-0.1 hbase"
  exit 1
fi
SRC_PATH="$HBASE_HOME/conf/regionservers"
for srv in $(cat $SRC_PATH); do
  echo "Sending command to $srv..."; 
  rsync -vaz --exclude='logs/*' $HBASE_HOME $srv:/$HBASE_INSTALL_DIR
  ssh $srv "rm -fR $HBASE_INSTALL_DIR/hbase ; ln -s $HBASE_HOME $HBASE_INSTALL_DIR/hbase"
done
echo "done."
