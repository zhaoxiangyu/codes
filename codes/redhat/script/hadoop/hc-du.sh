SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----excuting du on host: $host-----"; 
  ssh $host "du /home/hadoop/hadoop/logs -sh"
done
