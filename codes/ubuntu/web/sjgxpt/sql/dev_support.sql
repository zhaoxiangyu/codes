--- 数据库并发连接数限制 ---
SELECT * FROM v$resource_limit WHERE resource_name IN ('processes','sessions');
ALTER system SET processes=300 scope=spfile;
--- restart oracle instance

--- 账户解锁 ---
ALTER USER gjzspt ACCOUNT UNLOCK;

--- 表解锁 ---
ALTER SESSION SET CURRENT_SCHEMA = gjzspt;
SELECT object_name,
  machine,
  s.sid,
  s.serial#
FROM v$locked_object l,
  dba_objects o ,
  v$session s
WHERE l.object_id　=　o.object_id
AND l.session_id  =s.sid;

SELECT * FROM v$lock;