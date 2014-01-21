#!/bin/bash
set -x

USER_HOME=/home/hadoop

SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----check config on host: $host-----"; 
  ssh $host "source /etc/profile; $USER_HOME/i/host-info.sh"
  sudo ssh $host "source /etc/profile; $USER_HOME/i/rk/host-checkconf.sh"
done
./host-info.sh
sudo rk/host-checkconf.sh
