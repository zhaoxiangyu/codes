#!/bin/bash
set -x

host=${1:?"usage:$0 host1 host2"}

#less $ZOOKEEPER_HOME/conf/zoo.cfg
#grep tickTime $ZOOKEEPER_HOME/conf/zoo.cfg
#grep dataDir $ZOOKEEPER_HOME/conf/zoo.cfg
#grep clientPort $ZOOKEEPER_HOME/conf/zoo.cfg
#grep "server\." $ZOOKEEPER_HOME/conf/zoo.cfg
for host in $@; do
  echo conf | nc $host 2181
done
