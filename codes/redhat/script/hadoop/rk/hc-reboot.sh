set -x
SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  sudo ssh $host "reboot"
done
sudo reboot
