#!/bin/bash
set -x

HB_CONF_DIR=$HBASE_HOME/conf

grep -C1 JAVA_HOME $HB_CONF_DIR/hbase-env.sh
grep -C1 HBASE_MANAGES_ZK $HB_CONF_DIR/hbase-env.sh
grep -C1 hbase.rootdir $HB_CONF_DIR/hbase-site.xml
grep -C1 hbase.cluster.distributed $HB_CONF_DIR/hbase-site.xml
grep -C1 hbase.zookeeper.property $HB_CONF_DIR/hbase-site.xml
grep -C1 hbase.zookeeper.quorum $HB_CONF_DIR/hbase-site.xml
grep -C1 zookeeper.znode $HB_CONF_DIR/hbase-site.xml
cat $HB_CONF_DIR/regionservers
