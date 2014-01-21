load data 
 infile 'D:\3.txt' 
 badfile 'D:\3.bad' 
append 
INTO TABLE gps_trace_20121022 
fields terminated by ',' 
optionally enclosed by '"' 
trailing nullcols 
(REGCODE, 
GPSTIME DATE 'YYYY-MM-DD HH24:MI:SS', 
LONGTITUDE, 
LATITUDE, 
SPEED, 
DIRECTION, 
EFF, 
STATE, 
V_PROVIDER, 
EMPTY, 
EXIGENCY, 
LARCENY 
) 
