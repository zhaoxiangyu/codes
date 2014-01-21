#!/bin/bash
set -x

SRC_PATH="$HBASE_HOME/conf/regionservers"
for host in $(cat $SRC_PATH); do
  echo "====================$host===================="
  ssh $host 'source /etc/profile; ~/i/hbase-cl.sh'
done

username=`id -un`
hostname=`hostname`

test -f $HBASE_HOME/logs/hbase-$username-master-$hostname.log && rm $HBASE_HOME/logs/hbase-$username-master-$hostname.log
