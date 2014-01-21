#!/bin/bash
set -x

WRK_DIR=/home/hadoop/i
HBASE_CONF_DIR=$HBASE_HOME/conf
HBASE_CONF_BKUP_DIR=$WRK_DIR/bak-hbase-conf

test -d $HBASE_CONF_BKUP_DIR || mkdir -p $HBASE_CONF_BKUP_DIR
cp $HBASE_CONF_DIR/hbase-env.sh $HBASE_CONF_BKUP_DIR
cp $HBASE_CONF_DIR/hbase-site.xml $HBASE_CONF_BKUP_DIR
cp $HBASE_CONF_DIR/regionservers $HBASE_CONF_BKUP_DIR
