call ..\setpath.bat
sqlplus.exe wlw116120/qweasd as sysdba @dblink.sql

impdp.exe wlw116120/qweasd NETWORK_LINK=orcl120 TABLES=gps_trace_20121022 PARALLEL=1 REMAP_TABLESPACE=WLW120:WLW135 REMAP_TABLESPACE=WLW120_01:WLW135_01 REMAP_TABLESPACE=WLW120_02:WLW135_02 REMAP_TABLESPACE=WLW120_03:WLW135_03 REMAP_TABLESPACE=WLW120_04:WLW135_04 REMAP_TABLESPACE=WLW120_05:WLW135_05 REMAP_TABLESPACE=WLW120_06:WLW135_06 REMAP_TABLESPACE=WLW120_07:WLW135_07 REMAP_TABLESPACE=WLW120_08:WLW135_08 REMAP_TABLESPACE=WLW120_09:WLW135_09 REMAP_TABLESPACE=WLW120_10:WLW135_10 REMAP_TABLESPACE=WLW120_11:WLW135_11 REMAP_TABLESPACE=WLW120_12:WLW135_12 REMAP_TABLESPACE=WLW120_13:WLW135_13 REMAP_TABLESPACE=WLW120_14:WLW135_14 REMAP_TABLESPACE=WLW120_15:WLW135_15 REMAP_TABLESPACE=WLW120_16:WLW135_16 REMAP_TABLESPACE=WLW120_17:WLW135_17 REMAP_TABLESPACE=WLW120_18:WLW135_18 REMAP_TABLESPACE=WLW120_19:WLW135_19 REMAP_TABLESPACE=WLW120_20:WLW135_20 REMAP_TABLESPACE=WLW120_21:WLW135_21 REMAP_TABLESPACE=WLW120_22:WLW135_22 REMAP_TABLESPACE=WLW120_23:WLW135_23 REMAP_TABLESPACE=WLW120_24:WLW135_24 REMAP_TABLESPACE=WLW120_25:WLW135_25