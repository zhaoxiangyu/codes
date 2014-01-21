#!/bin/bash
set -x
#set -e

test -d sqoop-gen && rm -rf sqoop-gen/*
#./hbase-schema.sh

source db-t2hdfs.sh GPS_TRACE_20120717
hd_file=${TARGETFILE:?"target file not exported,error!"}
./db-hd2hb.sh $TARGETFILE gps_trace_fot_lab2 2>&1 | tee db-hd2hb.stdout
