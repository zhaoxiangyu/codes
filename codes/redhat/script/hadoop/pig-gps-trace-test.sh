#!/bin/bash
set -x

GPS_TRACE_DATA_FILE=/home/hadoop/backup-sqoop-imp/wlw116132-GPS_TRACE_20120717/part-m-00000

prepare-data(){
  head -n 100000 $GPS_TRACE_DATA_FILE >gps_trace_samples.txt
  hadoop fs -copyFromLocal gps_trace_samples.txt /sqoop-imp/gps_trace_samples.txt
}

#prepare-data
hadoop fs -rm /sqoop-imp/gps_trace_data.txt
time hadoop fs -copyFromLocal $GPS_TRACE_DATA_FILE /sqoop-imp/gps_trace_data.txt
#time pig -param input=/sqoop-imp/wlw116132-GPS_TRACE_20120717/part-m-00000 gps_trace_count.pig
