set -x
CONNECTURL=jdbc:oracle:thin:@10.9.91.124:1521:GJ116124
ORACLENAME=wlw116124
ORACLEPASSWORD=_116

oracleTableName=$1
columns=ID,ROWKEY,REGCODE,GPSTIME,LONGTITUDE,LATITUDE,SPEED,DIRECTION,EFF,STATE,TRACEID,ENTERTIME,V_PROVIDER
targetdir=/sqoop-imp/$1

taskCount=1

import-table() {
  hadoop fs -rmr $targetdir
  $SQOOP_HOME/bin/sqoop import --connect $CONNECTURL --username $ORACLENAME --password $ORACLEPASSWORD --m $taskCount --table $oracleTableName --columns $columns --target-dir $targetdir --outdir sqoop-gen
  hadoop fs -ls $targetdir
  hadoop fs -tail $targetdir/part-m-00000
}

if [ $# -eq 1 ] ; then
	import-table
else
	echo "usage:$0 oracle-table-name=gps_trace"
fi
