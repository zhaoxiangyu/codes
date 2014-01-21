#!/bin/bash
set -x

username=`id -un`
hostname=`hostname`

test -f $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log && grep -e ERROR -e FATAL $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log
