select * from gps_trace_20121022 t
select * from gps_trace_20121022 where regcode='¾©BN1840' for update
select * from gps_trace_20121022 where longtitude='116.12358'
select * from gps_trace_20121022 where speed=0
select regcode, count(*) from gps_trace_20121022 t group by regcode having regcode='¾©BN1840'

CREATE MATERIALIZED VIEW mv1 AS select regcode, count(*) from gps_trace_20121022 t group by regcode;
select * from mv1 where regcode='¾©BN1840';