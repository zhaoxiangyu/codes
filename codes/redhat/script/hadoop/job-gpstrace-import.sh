#!/bin/bash

#ACC_JOB_DEPS_DIR=/home/hadoop/i/job-deps
#ACC_JOB_DEPS_JARS=`echo $ACC_JOB_DEPS_DIR/*.jar`
#ACC_JOB_DEPS_CLASSPATH=`echo $ACC_JOB_DEPS_JARS|tr ' ' ':'`
#export HADOOP_CLASSPATH="$ACC_JOB_DEPS_CLASSPATH:$HADOOP_CLASSPATH"

set -x
acc_job_path=`pwd`/jobs
gps_trace_data_file=/sqoop-imp/VIEW_GPS_TRACE/part-m-00000

$HADOOP_HOME/bin/hadoop jar $acc_job_path/useHbase.jar \
GpsTraceImporter -t gps_trace_fi -i $gps_trace_data_file -c cf:row
