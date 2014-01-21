#!/bin/bash -x
$HADOOP_HOME/bin/hadoop fs -du /
$HADOOP_HOME/bin/hadoop fs -du /sqoop-imp
$HADOOP_HOME/bin/hadoop fs -du /hbase
$HADOOP_HOME/bin/hadoop fs -df /
