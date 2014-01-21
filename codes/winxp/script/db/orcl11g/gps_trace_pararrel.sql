select * from gps_trace_20121022 t
select * from gps_trace_20121022 where regcode='¾©BN1840' for update
select * from gps_trace_20121022 where longtitude='116.12358'
select * from gps_trace_20121022 where speed=0;
select regcode, count(*) from gps_trace_20121022 t group by regcode having regcode='¾©BN1840';

select /* parallel (lineitem,8) */ count(*) from gps_trace_20121022;

-- pararrel query --
explain plan for 
select count(*) from gps_trace_20121022;

explain plan for 
select /* parallel (lineitem,8) */ count(*) from gps_trace_20121022;

select * from table(DBMS_XPLAN.DISPLAY);

alter session force parallel query parallel (degree 8);