#!/bin/bash
set -x

SRC_PATH="zookeeper-servers"
for host in $(cat $SRC_PATH); do
  ssh $host 'source /etc/profile; ~/i/zookeeper-cl.sh'
done
./zookeeper-cl.sh
