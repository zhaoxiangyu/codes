#!/bin/bash -x
$HADOOP_HOME/bin/hadoop dfsadmin -report
$HADOOP_HOME/bin/hadoop dfsadmin -safemode get
