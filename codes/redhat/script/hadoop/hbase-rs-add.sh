#!/bin/bash
set -x
if [ $# -eq 0 ];then
  $HBASE_HOME/bin/hbase-daemon.sh start regionserver
else
  for host in $*; do
    grep $host $HBASE_HOME/conf/regionservers || sed -i -e "\$a$host" $HBASE_HOME/conf/regionservers
    ./hbase-deploy.sh
    ssh $host $HBASE_HOME/bin/hbase-daemon.sh start regionserver
  done
fi
