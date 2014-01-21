#!/bin/bash
set -x

sudo rk/sqoop-install.sh /home/hadoop/downloads/sqoop-1.4.1-incubating__hadoop-1.0.0.tar.gz

source /etc/profile

FILE_OJDBC_JAR=`locate ojdbc6.jar | head -n1`
if test -f $FILE_OJDBC_JAR; then
  cp $FILE_OJDBC_JAR $SQOOP_HOME/lib
else
  echo WARN: oracle jdbc jar not installed.
fi
