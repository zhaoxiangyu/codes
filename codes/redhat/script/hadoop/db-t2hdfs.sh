set -x

${SQOOP_HOME:?SQOOP_HOME not set!}

CONNECTURL=jdbc:oracle:thin:@10.9.91.132:1521:ora132
ORACLENAME=wlw116132
ORACLEPASSWORD=wlw_116

oracleTableName=$1
columns=REGCODE,GPSTIME,LONGTITUDE,LATITUDE,SPEED,DIRECTION,EFF,STATE,TRACEID,ENTERTIME,V_PROVIDER
targetdir=/sqoop-imp/$ORACLENAME-$oracleTableName

TARGETFILE=$targetdir/part-m-00000
export TARGETFILE

taskCount=1

import-table() {
  hadoop fs -test -e $TARGETFILE
  if [ $? -eq 0 ]; then
    echo $TARGETFILE already exists,quit writing table to hdfs!
    return 0
  fi

  hadoop fs -test -e $targetdir
  if [ $? -eq 0 ]; then
    echo $targetdir already exists,removing it
    hadoop fs -rmr $targetdir
  fi

  #echo creating directory $targetdir
  #hadoop fs -mkdir $targetdir
  $SQOOP_HOME/bin/sqoop import --connect $CONNECTURL --username $ORACLENAME --password $ORACLEPASSWORD --m $taskCount --table $oracleTableName --columns $columns --target-dir $targetdir --outdir sqoop-gen

  hadoop fs -ls $targetdir
  hadoop fs -tail $targetdir/part-m-00000
}

if [ $# -eq 1 ] ; then
	import-table
else
	echo "usage:$0 oracle-table-name=gps_trace"
fi
