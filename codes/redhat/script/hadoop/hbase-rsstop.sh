#!/bin/bash
set -x

host=${1:?"usage $0 hostname!"}
ssh $host 'source /etc/profile;$HBASE_HOME/bin/hbase-daemon.sh stop regionserver'
