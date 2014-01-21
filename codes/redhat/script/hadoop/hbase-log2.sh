username=`id -un`
hostname=`hostname`
# log levels such as: FATAL,ERROR,WARN
if [ $# -eq 0 ]; then
  loglevel=FATAL
else
  loglevel=$1
fi

ls -t $HBASE_HOME/logs/hbase-$username-master* | head -n3
test -f $HBASE_HOME/logs/hbase-$username-master-$hostname.log && grep $loglevel $HBASE_HOME/logs/hbase-$username-master-$hostname.log


ls -t $HBASE_HOME/logs/hbase-$username-regionserver* | head -n3
test -f $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log && grep $loglevel $HBASE_HOME/logs/hbase-$username-regionserver-$hostname.log
