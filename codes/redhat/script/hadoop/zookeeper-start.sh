#!/bin/bash
set -x

if [ $# -eq 0 ];then
  #$ZOOKEEPER_HOME/bin/zkServer.sh start
  for svr in `cat zookeeper-servers`;do
    ssh $svr 'source /etc/profile;$ZOOKEEPER_HOME/bin/zkServer.sh start'
  done
else
  for svr in $*;do
    ssh $svr 'source /etc/profile;$ZOOKEEPER_HOME/bin/zkServer.sh start'
  done
fi
