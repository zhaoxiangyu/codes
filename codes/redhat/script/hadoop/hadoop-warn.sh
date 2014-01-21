HADOOP_LOG_DIR=$HADOOP_HOME/logs
username=`id -un`
hostname=`hostname`

ls -t $HADOOP_LOG_DIR/hadoop-$username-namenode* | head -n3
test -f $HADOOP_LOG_DIR/hadoop-$username-namenode-$hostname.log && grep WARN $HADOOP_LOG_DIR/hadoop-$username-namenode-$hostname.log

echo '-------------------------------------------------------------------------'
ls -t $HADOOP_LOG_DIR/hadoop-$username-datanode* | head -n3
test -f $HADOOP_LOG_DIR/hadoop-$username-datanode-$hostname.log && grep WARN $HADOOP_LOG_DIR/hadoop-$username-datanode-$hostname.log
