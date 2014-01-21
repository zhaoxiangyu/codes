set -x
SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----sync time to timeserver----"; 
  sudo ssh $host "rdate -s -p timeserver;hwclock -w"
done
sudo rdate -s -p timeserver
sudo hwclock -w
