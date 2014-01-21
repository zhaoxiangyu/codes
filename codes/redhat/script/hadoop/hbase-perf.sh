#!/bin/bash
set -x

username=`id -un`
hostname=`hostname`

test -f $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log && grep -e 'exceeds the limit' -e 'Too many open files'  $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log
