set PATH=d:\oracle\product\10.2.0\db_1\bin;%PATH%
rem expdp.exe wlw116135/wlw_116@ora@135 ESTIMATE_ONLY=y TABLES=gps_trace_20121018,gps_trace_20121019,gps_trace_20121020,gps_trace_20121021
sqlplus qb/qweasd @dblink-135.sql
impdp.exe qb/qweasd NETWORK_LINK=ora135 TABLES=gps_trace_20121018 PARALLEL=1 REMAP_SCHEMA=wlw116135:qb