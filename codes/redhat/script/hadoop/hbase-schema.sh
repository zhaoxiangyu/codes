#!/bin/bash
set -x

$HBASE_HOME/bin/hbase shell <<EOI
create 'gps_trace_fot_lab2',{NAME => 'f1', VERSIONS => 1}
EOI
