username=`id -un`
hostname=`hostname`

less $HBASE_HOME/logs/hbase-$username-master-$hostname.log
