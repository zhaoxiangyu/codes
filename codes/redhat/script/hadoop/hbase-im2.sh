CONNECTURL=jdbc:oracle:thin:@10.9.91.124:1521:GJ116124
ORACLENAME=wlw116124
ORACLEPASSWORD=_116

hbaseTableName=gps_trace
columnfamily=gps_info
taskCount=3

query='select rowid as rowkey,t.regcode,t.gpstime,t.longtitude,t.latitude,t.speed,t.direction,t.eff,t.direction,t.state,t.traceid,t.entertime,t.v_provider from gps_trace_20111215 t where $CONDITIONS'
splitby=rowkey
rowkey=rowkey

import-table() {
  $SQOOP_HOME/bin/sqoop import --connect $CONNECTURL --username $ORACLENAME --password $ORACLEPASSWORD --append -m $taskCount --query $query --target-dir /sqoop-query --split-by $splitby --hbase-table $hbaseTableName --hbase-create-table --hbase-row-key $rowkey --column-family $columnfamily --outdir sqoop-gen 
}

import-table
