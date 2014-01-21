#!/bin/bash
set -x

#$HADOOP_HOME/bin/hadoop jar $HADOOP_HOME/hadoop-test-*.jar

#$HADOOP_HOME/bin/hadoop jar $HADOOP_HOME/hadoop-test-*.jar TestDFSIO -clean
$HADOOP_HOME/bin/hadoop jar $HADOOP_HOME/hadoop-test-*.jar TestDFSIO -write -nrFiles 10 -fileSize 1000
$HADOOP_HOME/bin/hadoop jar $HADOOP_HOME/hadoop-test-*.jar TestDFSIO -read -nrFiles 10 -fileSize 1000

#$HADOOP_HOME/bin/hadoop jar $HADOOP_HOME/hadoop-examples-*.jar randomwriter random-data
#$HADOOP_HOME/bin/hadoop jar $HADOOP_HOME/hadoop-examples-*.jar sort random-data sorted-data
#$HADOOP_HOME/bin/hadoop jar $HADOOP_HOME/hadoop-test-*.jar testmapredsort -sortInput random-data -sortOutput sorted-data
