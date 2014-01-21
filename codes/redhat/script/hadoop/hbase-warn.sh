username=`id -un`
hostname=`hostname`

ls -t $HBASE_HOME/logs/hbase-$username-master* | head -n3
test -f $HBASE_HOME/logs/hbase-$username-master-$hostname.log && grep WARN $HBASE_HOME/logs/hbase-$username-master-$hostname.log

ls -t $HBASE_HOME/logs/hbase-$username-regionserver* | head -n3
test -f $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log && grep WARN $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log
