SELECT * FROM v$resource_limit WHERE resource_name IN ('processes','sessions');
ALTER system SET processes=300 scope=spfile;
--- restart oracle instance