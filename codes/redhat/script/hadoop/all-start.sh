#!/bin/bash -x

rk/./hc-rdate.sh
./all-cl.sh

./hadoop-start.sh
#./zookeeper-start.sh
./hbase-start.sh

#sleep 5
#watch -n 5 --differences ./hc-jps.sh
