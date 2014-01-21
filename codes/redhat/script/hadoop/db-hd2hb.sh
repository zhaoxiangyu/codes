#!/bin/bash
set -x

acc_job_path=`pwd`/jobs
gps_trace_data_file=${1:?"target file not set!ERROR!"}
table_name=${2:?"table name not set!ERROR!"}

$HADOOP_HOME/bin/hadoop jar $acc_job_path/useHbase.jar \
GpsTraceTableImporter -t $table_name -i $gps_trace_data_file -c f1:
