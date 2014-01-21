SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----current time on host: $host-----"; 
  ssh $host date
done
echo "----------current time on namenode----------"
date
