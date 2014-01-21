select count(1) from &GPS_TRACE_XXXX where v_provider=&v_provide;

select count(1) from &GPS_TRACE_XXXX;

select 20121008,count(1) from GPS_TRACE_20121008
union
select 20121007,count(1) from GPS_TRACE_20121007
union
select 20121006,count(1) from GPS_TRACE_20121006

explain plan for
select count(1) from &GPS_TRACE_XXXX;
select * from table(DBMS_XPLAN.DISPLAY);

select count(1) from gps_vehicle;

select * from gps_drivers
where thename='&name';

select * from gps_drivers
where theid is not null;

select * from gps_drivers
where thename like '刘艳';

select * from gps_vehicle
select count(distinct v_companyid),count(1),count(distinct v_style),count(distinct v_color),count(distinct v_brand) from gps_vehicle
select distinct v_brand from gps_vehicle
group by v_brand

select * from &GPS_TRACE_XXXX order by entertime desc;

select * from &GPS_TRACE_XXXX@WLW_130.REGRESS.RDBMS.DEV.US.ORACLE.COM where entertime < TO_DATE('&endtime','yyyy-mm-dd hh24:mi:ss') order by entertime desc;
select * from &GPS_TRACE_XXXX@WLW_130.REGRESS.RDBMS.DEV.US.ORACLE.COM where entertime < TO_DATE('&endtime','yyyy-mm-dd hh24:mi:ss') order by traceid desc;
select * from &GPS_TRACE_XXXX@WLW_130.REGRESS.RDBMS.DEV.US.ORACLE.COM 
where gpstime = TO_DATE('2012-9-12 7:02:54','yyyy-mm-dd hh24:mi:ss')
and regcode = '京BP6165'
select * from &GPS_TRACE_XXXX@WLW_130.REGRESS.RDBMS.DEV.US.ORACLE.COM 
where gpstime = TO_DATE('2012-9-12 7:02:55','yyyy-mm-dd hh24:mi:ss')
and regcode = '京BM8756'
select * from &GPS_TRACE_XXXX@WLW_130.REGRESS.RDBMS.DEV.US.ORACLE.COM where entertime < TO_DATE('2012-09-12 09:00:00','yyyy-mm-dd hh24:mi:ss') order by traceid desc;
select * from &GPS_TRACE_XXXX@WLW_130.REGRESS.RDBMS.DEV.US.ORACLE.COM 
where gpstime = TO_DATE('2012-9-12 9:02:55','yyyy-mm-dd hh24:mi:ss')
and regcode = '京BP9025'

select * from GPS_TRACE_20120927 where regcode='京A31871' order by entertime desc;

select * from GPS_TRACE_20120927 where longtitude='116.41121';

--====================================================--
select * from GPS_SYS_WARN 
where 
0 = 0
-- and V_PROVIDER in (2,4,5,6)
-- and STATE_REG='0'
and content like '%5分钟没有GPS数据%'
order by warntime desc

--====================================================---
select count(style) num, style from( 
select distinct g.regcode regcode,t.v_style style 
from gps_trace_20120912 g left join gps_vehicle t on g.regcode = t.regcode 
WHERE 1=1 AND t.v_provider = 1 
AND gpstime > = TO_DATE('2012-09-12 10:16:11','yyyy-mm-dd hh24:mi:ss') 
AND gpstime < = TO_DATE('2012-09-12 10:19:11','yyyy-mm-dd hh24:mi:ss') ) group by style

-----------vs ----------------
select count(regcode) num, style from( 
select g.regcode regcode,t.v_style style 
from gps_trace_20120912 g left join gps_vehicle t on g.regcode = t.regcode 
WHERE 1=1 AND t.v_provider = 1 
AND gpstime > = TO_DATE('2012-09-12 10:16:11','yyyy-mm-dd hh24:mi:ss') 
AND gpstime < = TO_DATE('2012-09-12 10:19:11','yyyy-mm-dd hh24:mi:ss') ) group by style

-----------vs -------------------
select count(distinct regcode) num, style from( 
select g.regcode regcode,t.v_style style 
from gps_trace_20120912 g left join gps_vehicle t on g.regcode = t.regcode 
WHERE 1=1 AND t.v_provider = 1 
AND gpstime > = TO_DATE('2012-09-12 10:16:11','yyyy-mm-dd hh24:mi:ss') 
AND gpstime < = TO_DATE('2012-09-12 10:19:11','yyyy-mm-dd hh24:mi:ss') ) group by style

--==============================================--
select count(distinct g.regcode) regcode 
from gps_trace_20120912 g
WHERE 1=1 AND g.v_provider = 1 
AND gpstime > = TO_DATE('2012-09-12 10:16:11','yyyy-mm-dd hh24:mi:ss') 
AND gpstime < = TO_DATE('2012-09-12 10:19:11','yyyy-mm-dd hh24:mi:ss')

-----------vs--------
select count(distinct g.regcode) regcode  
from gps_trace_20120912 g left join gps_vehicle t on g.regcode = t.regcode 
WHERE 1=1 AND t.v_provider = 1 
AND gpstime > = TO_DATE('2012-09-12 10:16:11','yyyy-mm-dd hh24:mi:ss') 
AND gpstime < = TO_DATE('2012-09-12 10:19:11','yyyy-mm-dd hh24:mi:ss') 

--99999999999999999999999999999--

select distinct g.regcode regcode,t.v_style style 
from gps_trace_20120912 g left join gps_vehicle t on g.regcode = t.regcode 
WHERE 1=1 AND t.v_provider = 1 
AND gpstime > = TO_DATE('2012-09-12 10:16:11','yyyy-mm-dd hh24:mi:ss') 
AND gpstime < = TO_DATE('2012-09-12 10:19:11','yyyy-mm-dd hh24:mi:ss')

select count(1)
from gps_trace_20120912 g 
WHERE 1=1 
AND gpstime > = TO_DATE('2012-09-12 10:16:11','yyyy-mm-dd hh24:mi:ss') 
AND gpstime < = TO_DATE('2012-09-12 10:19:11','yyyy-mm-dd hh24:mi:ss')