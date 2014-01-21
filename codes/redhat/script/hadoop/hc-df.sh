SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----excuting df on host: $host-----"; 
  ssh $host "df -h"
done
