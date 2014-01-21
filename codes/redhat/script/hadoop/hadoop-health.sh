#!/bin/bash
set -x

$HADOOP_HOME/bin/hadoop fsck /
$HADOOP_HOME/bin/hadoop balancer
$HADOOP_HOME/bin/hadoop dfsadmin -safemode get
