jps|grep HMaster
$HBASE_HOME/bin/hbase hbck -details

#echo status|$HBASE_HOME/bin/hbase shell
#echo version|$HBASE_HOME/bin/hbase shell
#tail $HBASE_HOME/logs/*.out

#$ZOOKEEPER_HOME/bin/zkCli.sh -server datanode3 <<EOI
#ls /hbase
#get /hbase/master
#get /hbase/root-region-server
#quit
#EOI
