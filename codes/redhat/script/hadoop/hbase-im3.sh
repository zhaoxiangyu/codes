set -x
CONNECTURL=jdbc:oracle:thin:@10.9.91.124:1521:GJ116124
ORACLENAME=wlw116124
ORACLEPASSWORD=_116

#targetdir=/sqoop-imp/
columns=ID,ROWKEY,REGCODE,GPSTIME,LONGTITUDE,LATITUDE,SPEED,DIRECTION,EFF,STATE,TRACEID,ENTERTIME,V_PROVIDER
hbaseID=REGCODE
hbaseColumns=gps_info

oracleTableName=$1
hbaseTableName=$2
taskCount=1

import-table() {
##  $SQOOP_HOME/bin/sqoop import --append --connect $CONNECTURL --username $ORACLENAME --password $ORACLEPASSWORD --m $taskCount --table $oracleTableName --columns $columns --target-dir $targetdir --hbase-create-table --hbase-table $hbaseTableName --hbase-row-key $hbaseID --column-family $hbaseColumns --outdir sqoop-gen --split-by $hbaseID
  $SQOOP_HOME/bin/sqoop import --append --connect $CONNECTURL --username $ORACLENAME --password $ORACLEPASSWORD --m $taskCount --target-dir /sqoop-imp --table $oracleTableName --columns $columns --hbase-create-table --hbase-table $hbaseTableName --hbase-row-key $hbaseID --column-family $hbaseColumns --outdir sqoop-gen --split-by id
}

if [ $# -eq 2 ]; then
	import-table
elif [ $# -eq 1 ] ; then
	hbaseTableName=gps_trace
	import-table
else
	echo "usage:$0 [oracle-table-name=gps_trace] hbase-table-name"
fi
