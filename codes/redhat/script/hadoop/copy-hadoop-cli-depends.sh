#!/bin/bash
set -x

DIST_DIR=`pwd`/hadoopcli-deps
test -d $DIST_DIR || mkdir $DIST_DIR
cd $HADOOP_HOME
cp *.jar $DIST_DIR
cd lib
cp *.jar $DIST_DIR
cp $HADOOP_HOME/conf/core-site.xml $DIST_DIR
