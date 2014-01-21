#!/bin/bash
set -x

SRC_PATH="$HBASE_HOME/conf/regionservers"
if [ $# -eq 0 ]; then
  HOSTS=$(cat $SRC_PATH)
else
  HOSTS=$*
fi

for host in $HOSTS; do
  echo "====================$host===================="
  ssh $host 'source /etc/profile; ~/i/hbase-perf.sh'
done

username=`id -un`
hostname=`hostname`
echo "====================$hostname===================="
