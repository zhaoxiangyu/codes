#!/bin/bash
set -x

SRC_PATH="zookeeper-servers"
for host in $(cat $SRC_PATH); do
  echo ====================$host====================
  ssh $host 'source /etc/profile; ~/i/zookeeper-checkconf.sh'
done
