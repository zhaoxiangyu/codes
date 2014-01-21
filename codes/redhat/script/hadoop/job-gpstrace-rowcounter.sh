#!/bin/bash
set -x

SED_OPTS=-i
while getopts t OPTION ; do
  case "$OPTION" in
    t) SED_OPTS=;;
  esac
done

HBASE_SITE_FILE=$HBASE_HOME/conf/hbase-site.xml
CLASS_FULL_NAME=ParrelProcessorImpl
SED_ACTION='/<\/configuration>/i<property>\n<name>hbase.coprocessor.region.classes<\/name>\n<value>'$CLASS_FULL_NAME'<\/value>\n<\/property>'

grep $CLASS_FULL_NAME $HBASE_SITE_FILE
if [ $? -eq 0 ]; then
  echo $CLASS_FULL_NAME already set in $HBASE_SITE_FILE
else
  sed $SED_OPTS $SED_ACTION $HBASE_SITE_FILE
fi

HBASE_ENV_FILE=$HBASE_HOME/conf/hbase-env.sh
JOB_JAR_FILE=/home/hadoop/i/jobs/useHbase.jar
SED_ACTION='/export HBASE_CLASSPATH=/aexport HBASE_CLASSPATH='
SED_ACTION=$SED_ACTION$JOB_JAR_FILE
SED_ACTION=$SED_ACTION':$HBASE_CLASSPATH'

grep $JOB_JAR_FILE $HBASE_ENV_FILE
if [ $? -eq 0 ]; then
  echo $JOB_JAR_FILE already set in $HBASE_ENV_FILE
else
  sed $SED_OPTS "$SED_ACTION" $HBASE_ENV_FILE
fi

