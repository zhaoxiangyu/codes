#!/bin/bash
set -x

WRK_DIR=/home/hadoop/i
HBASE_CONF_DIR=$HBASE_HOME/conf
HBASE_CONF_BKUP_DIR=$WRK_DIR/bak-hbase-conf
BAK_SUFFIX=ori

mv $HBASE_CONF_DIR/hbase-env.sh $HBASE_CONF_DIR/hbase-env.$BAK_SUFFIX.sh
mv $HBASE_CONF_DIR/hbase-site.xml $HBASE_CONF_DIR/hbase-site.$BAK_SUFFIX.xml
mv $HBASE_CONF_DIR/regionservers $HBASE_CONF_DIR/regionservers.$BAK_SUFFIX

cp $HBASE_CONF_BKUP_DIR/* $HBASE_CONF_DIR
