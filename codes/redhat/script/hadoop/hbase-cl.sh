#!/bin/bash
set -x

username=`id -un`
hostname=`hostname`

test -f $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log && rm $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log
