#!/usr/bin/env bash

sqlplus64 -S gjzspt/12345678@192.168.21.249/gjzs <<-EOI >ws_log.txt
set wrap off
set linesize 32000
select * from t_dgap_ws_log order by create_time desc;
EOI
