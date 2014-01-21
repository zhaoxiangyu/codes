#!/bin/bash
set -x

HBASE_DEFAULT_FILE=$(locate hbase-default.xml|grep $(basename $HBASE_HOME)/ | grep src)
#find $HBASE_HOME -name '*.sh'|grep zookeeper

#HBASE_DEFAULT_FILE=$HBASE_HOME/src/main/resources/hbase-default.xml
grep -C1 hbase.hregion.max.filesize $HBASE_HOME/conf/hbase-site.xml
grep -C1 hbase.hregion.max.filesize $HBASE_DEFAULT_FILE
grep -C1 -e coprocessor -e timeout $HBASE_DEFAULT_FILE
grep -C1 -e socket $HBASE_DEFAULT_FILE
grep -C1 -e zookeeper $HBASE_DEFAULT_FILE
grep -C1 -e hbase.regionserver.handler.count $HBASE_HOME/conf/hbase-site.xml || grep -C1 -e hbase.regionserver.handler.count $HBASE_DEFAULT_FILE
grep -C1 -e hbase.rpc.timeout $HBASE_DEFAULT_FILE
grep -C1 -e hbase.client.retries.number $HBASE_DEFAULT_FILE

