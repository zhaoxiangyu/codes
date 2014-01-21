rem set PATH=F:\app\Administrator\product\11.2.0\dbhome_1\BIN;%PATH%
sqlplus.exe wlw116120/qweasd as sysdba @dblink-135.sql
rem impdp.exe VERSION=10.2.0 
impdp.exe wlw116120/qweasd NETWORK_LINK=ora135 VERSION=10.2.0 TABLES=gps_trace_20121018 PARALLEL=1 REMAP_SCHEMA=wlw116135:wlw116120