SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----excuting jps on host: $host -----"; 
  ssh $host "$JAVA_HOME/bin/jps|grep -v Jps"
done
echo "-----excuting jps on host: namenode -----"; 
jps|grep -v Jps
