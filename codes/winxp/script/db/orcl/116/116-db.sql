---表空间
CREATE bigfile TABLESPACE WLW135 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116.dbf' SIZE 500M AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_01 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_01.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_02 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_02.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_03 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_03.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_04 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_04.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_05 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_05.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_06 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_06.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_07 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_07.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_08 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_08.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_09 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_09.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_10 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_10.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_11 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_11.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_12 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_12.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_13 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_13.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_14 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_14.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_15 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_15.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_16 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_16.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_17 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_17.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_18 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_18.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_19 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_19.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_20 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_20.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_21 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_21.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_22 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_22.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_23 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_23.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_24 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_24.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;
CREATE bigfile TABLESPACE WLW135_25 DATAFILE 'D:\ORACLE\PRODUCT\10.2.0\ORADATA\ORA135\WLW116_25.dbf' SIZE 1G AUTOEXTEND ON NEXT 500M MAXSIZE UNLIMITED ;

---用户
create user WLW116135 identified by wlw_116 default tablespace WLW135 temporary tablespace TEMP profile DEFAULT;
grant dba to WLW116135;
grant unlimited tablespace to WLW116135;

---分区表
create table GPS_TRACE_20120808
(
  REGCODE    VARCHAR2(16),
  GPSTIME    DATE,
  LONGTITUDE NUMBER,
  LATITUDE   NUMBER,
  SPEED      INTEGER,
  DIRECTION  INTEGER,
  EFF        VARCHAR2(4),
  STATE      INTEGER,
  TRACEID    VARCHAR2(32),
  ENTERTIME  DATE default sysdate,
  V_PROVIDER VARCHAR2(2),
  EMPTY      VARCHAR2(1),
  EXIGENCY   VARCHAR2(1),
  LARCENY    VARCHAR2(1)
)partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE('2012-08-08 01:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_01,
  partition PART_02 values less than (TO_DATE('2012-08-08 02:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_02,
  partition PART_03 values less than (TO_DATE('2012-08-08 03:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_03,
  partition PART_04 values less than (TO_DATE('2012-08-08 04:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_04,
  partition PART_05 values less than (TO_DATE('2012-08-08 05:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_05,
  partition PART_06 values less than (TO_DATE('2012-08-08 06:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_06,
  partition PART_07 values less than (TO_DATE('2012-08-08 07:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_07,
  partition PART_08 values less than (TO_DATE('2012-08-08 08:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_08,
  partition PART_09 values less than (TO_DATE('2012-08-08 09:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_09,
  partition PART_10 values less than (TO_DATE('2012-08-08 10:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_10,
  partition PART_11 values less than (TO_DATE('2012-08-08 11:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_11,
  partition PART_12 values less than (TO_DATE('2012-08-08 12:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_12,
  partition PART_13 values less than (TO_DATE('2012-08-08 13:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_13,
  partition PART_14 values less than (TO_DATE('2012-08-08 14:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_14,
  partition PART_15 values less than (TO_DATE('2012-08-08 15:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_15,
  partition PART_16 values less than (TO_DATE('2012-08-08 16:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_16,
  partition PART_17 values less than (TO_DATE('2012-08-08 17:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_17,
  partition PART_18 values less than (TO_DATE('2012-08-08 18:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_18,
  partition PART_19 values less than (TO_DATE('2012-08-08 19:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_19,
  partition PART_20 values less than (TO_DATE('2012-08-08 20:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_20,
  partition PART_21 values less than (TO_DATE('2012-08-08 21:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_21,
  partition PART_22 values less than (TO_DATE('2012-08-08 22:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_22,
  partition PART_23 values less than (TO_DATE('2012-08-08 23:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_23,
  partition PART_24 values less than (TO_DATE('2012-08-09 00:00:00', 'YYYY-MM-DD HH24:MI:SS'))
    tablespace WLW135_24,
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW135_25
);

---分区索引
create index IDX_GPS_20120808_REG on GPS_TRACE_20120808 (REGCODE) local 
(
partition idx_01 tablespace wlw135_01 ,
partition idx_02 tablespace wlw135_02 ,
partition idx_03 tablespace wlw135_03 ,
partition idx_04 tablespace wlw135_04 ,
partition idx_05 tablespace wlw135_05 ,
partition idx_06 tablespace wlw135_06 ,
partition idx_07 tablespace wlw135_07 ,
partition idx_08 tablespace wlw135_08 ,
partition idx_09 tablespace wlw135_09 ,
partition idx_10 tablespace wlw135_10 ,
partition idx_11 tablespace wlw135_11 ,
partition idx_12 tablespace wlw135_12 ,
partition idx_13 tablespace wlw135_13 ,
partition idx_14 tablespace wlw135_14 ,
partition idx_15 tablespace wlw135_15 ,
partition idx_16 tablespace wlw135_16 ,
partition idx_17 tablespace wlw135_17 ,
partition idx_18 tablespace wlw135_18 ,
partition idx_19 tablespace wlw135_19 ,
partition idx_20 tablespace wlw135_20 ,
partition idx_21 tablespace wlw135_21 ,
partition idx_22 tablespace wlw135_22 ,
partition idx_23 tablespace wlw135_23 ,
partition idx_24 tablespace wlw135_24 ,
partition idx_25 tablespace wlw135_25 
);

---sequence
create or replace sequence SEQ_GPS_TRACEID
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20
cycle;

---trigger
create or replace trigger tri_bf_gps_trace_20120808  before insert on  gps_trace_20120808 for  each row
begin
select concat( 'GPS_',SEQ_GPS_TRACEID.nextval ) into :new.traceid from dual;
end;

---存储过程
create or replace procedure PRC_CREATE_TB_DAY
is
yes_b_date varchar2(256);
yes_date varchar2(256);
cur_date varchar2(256);
tom_date varchar2(256);
tom_a_date varchar2(256);

create_table varchar(4096);
create_index varchar(4096);

create_trig varchar2(256);
delete_trig varchar2(256);

insert_data varchar2(1024);
delete_data varchar2(1024);
begin_time varchar2(32);
end_time varchar2(32);

begin
yes_b_date := to_char( sysdate - 2,'yyyymmdd');
yes_date := to_char( sysdate - 1,'yyyymmdd');
cur_date := to_char( sysdate ,'yyyymmdd');
tom_date := to_char( sysdate + 1,'yyyymmdd');
tom_a_date := to_char( sysdate + 2,'yyyymmdd');

---创建明天的表
create_table := 'create table gps_trace_' || tom_date || '(
    REGCODE    VARCHAR2(16),
	GPSTIME    DATE,
  	LONGTITUDE NUMBER,
  	LATITUDE   NUMBER,
  	SPEED      INTEGER,
  	DIRECTION  INTEGER,
  	EFF        VARCHAR2(4),
  	STATE      INTEGER,
  	TRACEID    VARCHAR2(32),
  	ENTERTIME  DATE default sysdate,
  	V_PROVIDER VARCHAR2(2),
  	EMPTY      VARCHAR2(1),
  	EXIGENCY   VARCHAR2(1),
  	LARCENY    VARCHAR2(1)
)partition by range (GPSTIME)
(
  partition PART_01 values less than (TO_DATE(' || '''' || tom_date || '010000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_01,
  partition PART_02 values less than (TO_DATE(' || '''' || tom_date || '020000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_02,
  partition PART_03 values less than (TO_DATE(' || '''' || tom_date || '030000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_03,
  partition PART_04 values less than (TO_DATE(' || '''' || tom_date || '040000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_04,
  partition PART_05 values less than (TO_DATE(' || '''' || tom_date || '050000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_05,
  partition PART_06 values less than (TO_DATE(' || '''' || tom_date || '060000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_06,
  partition PART_07 values less than (TO_DATE(' || '''' || tom_date || '070000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_07,
  partition PART_08 values less than (TO_DATE(' || '''' || tom_date || '080000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_08,
  partition PART_09 values less than (TO_DATE(' || '''' || tom_date || '090000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_09,
  partition PART_10 values less than (TO_DATE(' || '''' || tom_date || '100000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_10,
  partition PART_11 values less than (TO_DATE(' || '''' || tom_date || '110000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_11,
  partition PART_12 values less than (TO_DATE(' || '''' || tom_date || '120000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_12,
  partition PART_13 values less than (TO_DATE(' || '''' || tom_date || '130000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_13,
  partition PART_14 values less than (TO_DATE(' || '''' || tom_date || '140000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_14,
  partition PART_15 values less than (TO_DATE(' || '''' || tom_date || '150000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_15,
  partition PART_16 values less than (TO_DATE(' || '''' || tom_date || '160000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_16,
  partition PART_17 values less than (TO_DATE(' || '''' || tom_date || '170000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_17,
  partition PART_18 values less than (TO_DATE(' || '''' || tom_date || '180000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_18,
  partition PART_19 values less than (TO_DATE(' || '''' || tom_date || '190000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_19,
  partition PART_20 values less than (TO_DATE(' || '''' || tom_date || '200000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_20,
  partition PART_21 values less than (TO_DATE(' || '''' || tom_date || '210000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_21,
  partition PART_22 values less than (TO_DATE(' || '''' || tom_date || '220000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_22,
  partition PART_23 values less than (TO_DATE(' || '''' || tom_date || '230000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_23,
  partition PART_24 values less than (TO_DATE(' || '''' || tom_a_date || '000000' || '''' || ',' || '''' || 'yyyyMMddhh24miss' || '''' || '))
    tablespace WLW135_24,
  partition PART_25 values less than (MAXVALUE)
    tablespace WLW135_25
)';
execute immediate create_table;

---为新表创建
create_index := 'create index idx_gps_' || tom_date || '_REG on gps_trace_'|| tom_date ||' (REGCODE) local 
(
partition idx_01 tablespace wlw135_01 ,
partition idx_02 tablespace wlw135_02 ,
partition idx_03 tablespace wlw135_03 ,
partition idx_04 tablespace wlw135_04 ,
partition idx_05 tablespace wlw135_05 ,
partition idx_06 tablespace wlw135_06 ,
partition idx_07 tablespace wlw135_07 ,
partition idx_08 tablespace wlw135_08 ,
partition idx_09 tablespace wlw135_09 ,
partition idx_10 tablespace wlw135_10 ,
partition idx_11 tablespace wlw135_11 ,
partition idx_12 tablespace wlw135_12 ,
partition idx_13 tablespace wlw135_13 ,
partition idx_14 tablespace wlw135_14 ,
partition idx_15 tablespace wlw135_15 ,
partition idx_16 tablespace wlw135_16 ,
partition idx_17 tablespace wlw135_17 ,
partition idx_18 tablespace wlw135_18 ,
partition idx_19 tablespace wlw135_19 ,
partition idx_20 tablespace wlw135_20 ,
partition idx_21 tablespace wlw135_21 ,
partition idx_22 tablespace wlw135_22 ,
partition idx_23 tablespace wlw135_23 ,
partition idx_24 tablespace wlw135_24 ,
partition idx_25 tablespace wlw135_25 
)';
execute immediate create_index;

---为新表创建生成traceid的trigger
create_trig := 'create or replace trigger tri_bf_gps_trace_' || tom_date || '  before insert on  gps_trace_'|| tom_date || ' for  each row
begin
select   concat( ' || '''' || 'GPS_' || '''' || ',SEQ_GPS_TRACEID.nextval ) into :new.traceid from dual;
end;';
execute immediate create_trig;

---删除旧表上的trigger
delete_trig := 'drop trigger tri_bf_gps_trace_'|| yes_b_date ;
execute immediate delete_trig;

---按照gpstime把昨天表中今天的数据迁移过来
insert_data :='INSERT INTO gps_trace_' ||  cur_date  || 
		      ' SELECT * FROM  gps_trace_'|| yes_date  ||
		      ' where to_char(gpstime,' || '''' || 'yyyymmdd' || '''' || ') = ''' || cur_date || '''';
execute immediate insert_data;
commit;

delete_data := 'DELETE FROM gps_trace_'|| yes_date ||
               ' where to_char(gpstime,' || '''' || 'yyyymmdd' || '''' || ') = ''' || cur_date || '''';
execute immediate delete_data;
commit;

---按照gpstime把昨天表中前天的数据迁移过去

insert_data :='INSERT INTO gps_trace_' ||  yes_b_date  || 
		      ' SELECT * FROM  gps_trace_'|| yes_date  ||
		      ' where to_char(gpstime,' || '''' || 'yyyymmdd' || '''' || ') = ''' || yes_b_date || '''';
execute immediate insert_data;
commit;

delete_data := 'DELETE FROM gps_trace_'|| yes_date ||
               ' where to_char(gpstime,' || '''' || 'yyyymmdd' || '''' || ') = ''' || yes_b_date || '''';
execute immediate delete_data;
commit;

end PRC_CREATE_TB_DAY;

---日统计存储过程
create or replace procedure PRC_COUNT_DAY is
  insert_data    varchar2(4096);
  delete_data    varchar2(1024);
  table_name varchar2(50);
  COUNTDATE  varchar2(50);
  num integer;

begin    
  COUNTDATE  := to_char(sysdate - 2, 'yyyy-MM-dd');
  table_name := concat('GPS_TRACE_', to_char(sysdate - 2, 'yyyyMMdd'));
  
  ---删除前天的数据
  delete_data := 'delete from gps_day_counts where COUNTDATE= to_date( ' || '''' || COUNTDATE || '''' || ',' || '''' || 'yyyy-MM-dd' || '''' || ') ';
  execute immediate delete_data;
  commit;
  
  ---统计前天的数据
  insert_data := '
  insert into gps_day_counts(PROVIDER,COUNTDATE,CHUZU,CHUZUCOUNT,ZULIN,ZULINCOUNT,GONGJIAO,GONGJIAOCOUNT,LVYOU,LVYOUCOUNT,KEYUN,KEYUNCOUNT,XIAOCHE,XIAOCHECOUNT,HUAWEICHE,HUAWEICHECOUNT,QITA,QITACOUNT) 
select t.v_provider,to_date( ' || '''' || COUNTDATE || '''' || ',' || '''' || 'yyyy-MM-dd' || '''' || '),
       sum(decode(t.style,' ||  '''' || '240' ||'''' || ',t.num,null)) as chuzu,
       sum(decode(t.style,' ||  '''' || '240' ||'''' || ',t.allnum,null)) as chuzucount,
       sum(decode(t.style,' ||  '''' || '241' ||'''' || ',t.num,null)) as zulin,
       sum(decode(t.style,' ||  '''' || '241' ||'''' || ',t.allnum,null)) as zulincount,
       sum(decode(t.style,' ||  '''' || '242' ||'''' || ',t.num,null)) as gongjiao,
       sum(decode(t.style,' ||  '''' || '242' ||'''' || ',t.allnum,null)) as gongjiaocount,
       sum(decode(t.style,' ||  '''' || '243' ||'''' || ',t.num,null)) as lvyou,
       sum(decode(t.style,' ||  '''' || '243' ||'''' || ',t.allnum,null)) as lvyoucount,
       sum(decode(t.style,' ||  '''' || '244' ||'''' || ',t.num,null)) as keyun,
       sum(decode(t.style,' ||  '''' || '244' ||'''' || ',t.allnum,null)) as keyuncount,
       sum(decode(t.style,' ||  '''' || '245' ||'''' || ',t.num,null)) as xiaoche,
       sum(decode(t.style,' ||  '''' || '245' ||'''' || ',t.allnum,null)) as xiaochecount,
       sum(decode(t.style,' ||  '''' || '246' ||'''' || ',t.num,null)) as huawei,
       sum(decode(t.style,' ||  '''' || '246' ||'''' || ',t.allnum,null)) as huaweicount,
       sum(decode(t.style,' ||  '''' || '255' ||'''' || ',t.num,null)) as qita,
       sum(decode(t.style,' ||  '''' || '255' ||'''' || ',t.allnum,null)) as qitacount
from (select t1.v_provider,t1.style,t1.num as allnum,t2.num as num from (SELECT count(*) num, b.v_provider,b.v_style style
      FROM gps_vehicle b group by b.v_provider,b.v_style) t1, (SELECT count(*) num,v_provider,style
   from (select distinct g.regcode,v.v_provider,v.v_style style
           from ' || table_name || ' g
           left join gps_vehicle v on g.regcode = v.regcode)
group by v_provider,style) t2 where t1.v_provider=t2.v_provider and t1.style=t2.style) t group by t.v_provider';
  execute immediate insert_data;
  commit;
  
   ---统计昨天的数据
  COUNTDATE  := to_char(sysdate - 1, 'yyyy-MM-dd');
  table_name := concat('GPS_TRACE_', to_char(sysdate - 1, 'yyyyMMdd'));
  insert_data := '
  insert into gps_day_counts(PROVIDER,COUNTDATE,CHUZU,CHUZUCOUNT,ZULIN,ZULINCOUNT,GONGJIAO,GONGJIAOCOUNT,LVYOU,LVYOUCOUNT,KEYUN,KEYUNCOUNT,XIAOCHE,XIAOCHECOUNT,HUAWEICHE,HUAWEICHECOUNT,QITA,QITACOUNT) 
select t.v_provider,to_date( ' || '''' || COUNTDATE || '''' || ',' || '''' || 'yyyy-MM-dd' || '''' || '),
       sum(decode(t.style,' ||  '''' || '240' ||'''' || ',t.num,null)) as chuzu,
       sum(decode(t.style,' ||  '''' || '240' ||'''' || ',t.allnum,null)) as chuzucount,
       sum(decode(t.style,' ||  '''' || '241' ||'''' || ',t.num,null)) as zulin,
       sum(decode(t.style,' ||  '''' || '241' ||'''' || ',t.allnum,null)) as zulincount,
       sum(decode(t.style,' ||  '''' || '242' ||'''' || ',t.num,null)) as gongjiao,
       sum(decode(t.style,' ||  '''' || '242' ||'''' || ',t.allnum,null)) as gongjiaocount,
       sum(decode(t.style,' ||  '''' || '243' ||'''' || ',t.num,null)) as lvyou,
       sum(decode(t.style,' ||  '''' || '243' ||'''' || ',t.allnum,null)) as lvyoucount,
       sum(decode(t.style,' ||  '''' || '244' ||'''' || ',t.num,null)) as keyun,
       sum(decode(t.style,' ||  '''' || '244' ||'''' || ',t.allnum,null)) as keyuncount,
       sum(decode(t.style,' ||  '''' || '245' ||'''' || ',t.num,null)) as xiaoche,
       sum(decode(t.style,' ||  '''' || '245' ||'''' || ',t.allnum,null)) as xiaochecount,
       sum(decode(t.style,' ||  '''' || '246' ||'''' || ',t.num,null)) as huawei,
       sum(decode(t.style,' ||  '''' || '246' ||'''' || ',t.allnum,null)) as huaweicount,
       sum(decode(t.style,' ||  '''' || '255' ||'''' || ',t.num,null)) as qita,
       sum(decode(t.style,' ||  '''' || '255' ||'''' || ',t.allnum,null)) as qitacount
from (select t1.v_provider,t1.style,t1.num as allnum,t2.num as num from (SELECT count(*) num, b.v_provider,b.v_style style
      FROM gps_vehicle b group by b.v_provider,b.v_style) t1, (SELECT count(*) num,v_provider,style
   from (select distinct g.regcode,v.v_provider,v.v_style style
           from ' || table_name || ' g
           left join gps_vehicle v on g.regcode = v.regcode)
group by v_provider,style) t2 where t1.v_provider=t2.v_provider and t1.style=t2.style) t group by t.v_provider';
  execute immediate insert_data;
  commit;
  
    COUNTDATE := to_char(sysdate - 1, 'yyyy-MM-dd');
    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=1;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_1' || '''' || ',1,' || '''' || COUNTDATE ||  '，该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
    	 commit;
     end if;
     
    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=2;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_2' || '''' || ',2,' || '''' || COUNTDATE ||  '，该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
    	 commit;
     end if;
     
    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=3;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_3' || '''' || ',3,' || '''' || COUNTDATE ||  '，该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
    	 commit;
     end if;
     
    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=4;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_4' || '''' || ',4,' || '''' || COUNTDATE ||  '，该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
    	 commit;
     end if;
     
    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=5;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_5' || '''' || ',5,' || '''' || COUNTDATE ||  '，该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
    	 commit;
     end if;
     
    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=6;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_6' || '''' || ',6,' || '''' || COUNTDATE ||  '，该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
    	 commit;
     end if;
     
    select count(*) into num from gps_vehicle t where to_char(t.enter_time,'yyyy-mm-dd')= to_char(sysdate - 1, 'yyyy-MM-dd') and t.v_provider=8;
    if num=0 then
       insert_data := 'insert into gps_sys_warn (warnid,v_provider,content,state_reg) values(' || '''' || COUNTDATE || '_8' || '''' || ',8,' || '''' || COUNTDATE ||  '，该运营商三次基础数据同步全部失败！' ||'''' || ',' || '''' || '' ||'''' || ')';
       execute immediate insert_data;
    	 commit;
     end if;
end PRC_COUNT_DAY;

---每天创建表的jpb
declare job1 number;
begin
	sys.dbms_job.submit(job1,'PRC_CREATE_TB_DAY;PRC_COUNT_DAY;',sysdate,'TRUNC(sysdate) +1 +1/24');
	commit;
end;
/

