#!/bin/bash
set -x

WRK_DIR=/home/hadoop/i
HADOOP_CONF_DIR=$HADOOP_HOME/conf
HADOOP_CONF_BKUP_DIR=$WRK_DIR/bak-hadoop-conf
BAK_SUFFIX=ori

mv $HADOOP_CONF_DIR/hadoop-env.sh $HADOOP_CONF_DIR/hadoop-env.$BAK_SUFFIX.sh
mv $HADOOP_CONF_DIR/core-site.xml $HADOOP_CONF_DIR/core-site.$BAK_SUFFIX.xml
mv $HADOOP_CONF_DIR/hdfs-site.xml $HADOOP_CONF_DIR/hdfs-site.$BAK_SUFFIX.xml
mv $HADOOP_CONF_DIR/mapred-site.xml $HADOOP_CONF_DIR/mapred-site.$BAK_SUFFIX.xml 
mv $HADOOP_CONF_DIR/masters $HADOOP_CONF_DIR/masters.$BAK_SUFFIX
mv $HADOOP_CONF_DIR/slaves $HADOOP_CONF_DIR/slaves.$BAK_SUFFIX

cp $HADOOP_CONF_BKUP_DIR/* $HADOOP_CONF_DIR
