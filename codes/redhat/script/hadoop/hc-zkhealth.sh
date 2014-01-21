#!/bin/bash
set -x

SRC_PATH="zookeeper-servers"
for srv in $(cat $SRC_PATH); do
  echo "==========zookeeper server $srv=========="
  #ssh $srv "vmstat; free -m"
  ssh $srv "source /etc/profile; i/zookeeper-health.sh"
done
