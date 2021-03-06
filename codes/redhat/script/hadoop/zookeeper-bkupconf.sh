#!/bin/bash
set -x

WRK_DIR=/home/hadoop/i
ZOOKEEPER_CONF_DIR=$ZOOKEEPER_HOME/conf
ZOOKEEPER_CONF_BKUP_DIR=$WRK_DIR/bak-zookeeper-conf

test -d $ZOOKEEPER_CONF_BKUP_DIR || mkdir -p $ZOOKEEPER_CONF_BKUP_DIR
cp $ZOOKEEPER_CONF_DIR/zoo.cfg $ZOOKEEPER_CONF_BKUP_DIR
