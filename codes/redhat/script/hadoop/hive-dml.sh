#hive -f script.q
#hive -e 'select * from dummy'
#hive -S -e 'select * from dummy'
cd $HIVE_HOME
hive -e 'select * from gps_trace1'
