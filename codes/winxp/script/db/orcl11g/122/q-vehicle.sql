select count(*),t1.v_provider
  from gps_vehicle t1 group by t1.v_provider order by v_provider;
  
/*
select * from gps_vehicle;
select count(*) from gps_vehicle;
*/
  
select count(distinct t1.regcode),t1.v_provider
  from gps_trace_20121102 t1
  group by t1.v_provider
  order by v_provider;
    
/*
select count(distinct regcode),v_provider from(
select distinct t1.regcode,t1.v_provider
  from gps_trace_20121102 t1
 where t1.regcode in (select t2.regcode from gps_vehicle t2))
 group by v_provider order by v_provider;
*/
 
select count(*),v_provider from(
select distinct t1.regcode,t1.v_provider
  from gps_trace_20121102 t1
 where t1.regcode not in (select t2.regcode from gps_vehicle t2))
 group by v_provider order by v_provider;
 
select count(*),t1.v_provider
  from gps_vehicle t1
 where t1.regcode not in (select t2.regcode from gps_trace_20121102 t2)
 group by t1.v_provider order by v_provider;