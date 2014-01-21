#!/bin/bash
set -x

acc_job_path=`pwd`/jobs
#gps_trace_data_file=/sqoop-imp/wlw116132-GPS_TRACE_20120702/part-m-00000
gps_trace_data_file=/sqoop-imp/wlw116132-GPS_TRACE_20120717/part-m-00000

hadoop fs -rmr /user/hadoop/gps_trace_line_counter
time $HADOOP_HOME/bin/hadoop jar $acc_job_path/useHbase.jar \
GpsTraceLineCounter -i $gps_trace_data_file -o gps_trace_line_counter

hadoop dfs -text gps_trace_line_counter/part-r-00000
