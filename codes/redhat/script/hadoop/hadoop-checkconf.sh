#!/bin/bash
set -x
HADOOP_CONF_DIR=$HADOOP_HOME/conf

grep -C1 HADOOP_HEAPSIZE $HADOOP_CONF_DIR/hadoop-env.sh
grep -C1 HADOOP_LOG_DIR $HADOOP_CONF_DIR/hadoop-env.sh

grep -C1 'dfs.hosts.exclude' $HADOOP_CONF_DIR/core-site.xml
grep -C1 'fs.default.name' $HADOOP_CONF_DIR/core-site.xml

grep -C1 'fs.name.dir' $HADOOP_CONF_DIR/hdfs-site.xml
grep -C1 'fs.data.dir' $HADOOP_CONF_DIR/hdfs-site.xml
grep -C1 'fs.checkpoint.dir' $HADOOP_CONF_DIR/hdfs-site.xml
grep -C1 'dfs.permissions' $HADOOP_CONF_DIR/hdfs-site.xml
grep -C1 'dfs.datanode.du.reserved' $HADOOP_CONF_DIR/hdfs-site.xml

grep -C1 'mapred.job.tracker' $HADOOP_CONF_DIR/mapred-site.xml
grep -C1 'mapred.local.dir' $HADOOP_CONF_DIR/mapred-site.xml
grep -C1 'mapred.system.dir' $HADOOP_CONF_DIR/mapred-site.xml
grep -C1 'mapred.jobtracker.restart.recover' $HADOOP_CONF_DIR/mapred-site.xml
grep -C1 'mapred.map.child.log.level' $HADOOP_CONF_DIR/mapred-site.xml
grep -C1 'mapred.reduce.child.log.level' $HADOOP_CONF_DIR/mapred-site.xml
grep -C1 'mapred.userlog.retain.hours' $HADOOP_CONF_DIR/mapred-site.xml
grep -C1 'mapred.userlog.limit.kb' $HADOOP_CONF_DIR/mapred-site.xml

echo following setting related performances
grep -C1 'io.file.buffer.size' $HADOOP_CONF_DIR/core-site.xml
grep -C1 'dfs.datanode.max.xcievers' $HADOOP_CONF_DIR/hdfs-site.xml
