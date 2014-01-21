#!/bin/bash
set -x

./host-instconf.sh
./hadoop-instconf.sh
./zookeeper-instconf.sh
./hbase-instconf.sh
