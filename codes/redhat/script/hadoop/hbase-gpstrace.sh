$HBASE_HOME/bin/hbase shell <<EOI
  version
  list
  #scan 'gps_trace'
  #get 'gps_trace',"\xe4\xba\xacBF3784"
  #scan 'gps_trace',{STOPROW =>'\xe4\xba\xacB'}
  #scan 'gps_trace',{STARTROW =>'\xe4\xba\xacB',STOPROW =>'\xe4\xba\xacBF3784'}
  #scan 'gps_trace',{STARTROW =>'\xe4\xba\xacBF',STOPROW =>'\xe4\xba\xacBH'}
  #scan 'gps_trace',{STARTROW => '\xe4\xba\xacBF3',STOPROW => '\xe4\xba\xacBF4'}
  count 'gps_trace'
  count 'gps_trace_fi'
  #count 'gps_trace_fot', INTERVAL => 100000
  count 'gps_trace_fot_lab', INTERVAL => 100000
  exit
EOI
