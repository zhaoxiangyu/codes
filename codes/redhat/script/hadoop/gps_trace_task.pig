-- input='/sqoop-imp/gps_trace_samples.txt'
-- input='/sqoop-imp/wlw116132-GPS_TRACE_20120717/part-m-00000'
gps_traces = LOAD '$input' USING PigStorage(',') AS (regioncode:chararray,gpstime:chararray,longitude:chararray,latitude:chararray,speed:int,direction:int,eff:int,state:int,traceid:chararray,entertime:chararray,vprovider:int);
ga_gps_traces = GROUP gps_traces ALL;
rowcount = FOREACH ga_gps_traces GENERATE COUNT(gps_traces);
DUMP rowcount;
