#!/bin/bash

set -x

acc_job_path=`pwd`/jobs

$HADOOP_HOME/bin/hadoop jar $acc_job_path/useHbase.jar \
GpsTraceCounter -t gps_trace_fot_lab2 -o gps_counter

hadoop dfs -text gps_counter/part-r-00000
