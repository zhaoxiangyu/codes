#!/bin/bash
set -x

./host-bkupconf.sh
./hadoop-bkupconf.sh
./zookeeper-bkupconf.sh
./hbase-bkupconf.sh
