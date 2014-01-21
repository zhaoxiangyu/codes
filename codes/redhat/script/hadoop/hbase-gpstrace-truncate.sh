$HBASE_HOME/bin/hbase shell <<EOI
  #truncate 'gps_trace'
  #truncate 'gps_trace_fi'
  #truncate 'gps_trace_fot'
  #truncate 'gps_trace_fot_lab'
  truncate 'gps_trace_fot_lab2'
  exit
EOI
