SRC_PATH="$HADOOP_HOME/conf/slaves"
for host in $(cat $SRC_PATH); do
  echo "-----excuting reboot on host: $host-----"; 
  sudo ssh $host "reboot"
done
echo "-----excuting reboot on host: namenode-----"; 
sudo reboot
