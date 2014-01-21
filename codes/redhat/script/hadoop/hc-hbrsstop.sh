SRC_PATH="$HADOOP_HOME/conf/slaves"

echo "balance_switch false" | $HBASE_HOME/bin/hbase shell
for host in $(cat $SRC_PATH); do
  echo "-----stop region server on host: $host -----"; 
  ./hbase-rsstop.sh $host
done
