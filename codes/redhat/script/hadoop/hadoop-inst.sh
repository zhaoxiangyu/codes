#!/bin/bash
set -e
set -x

rk/hadoop-install.sh /home/hadoop/downloads/hadoop-1.0.3.tar.gz
./hadoop-instconf.sh
./hadoop-redeploy.sh
$HADOOP_HOME/bin/hadoop namenode -format
