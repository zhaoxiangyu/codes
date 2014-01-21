title 10g
pushd D:\oracle\product\10.2.0\db_1\BIN
set ORACLE_SID=orcl
call emctl.bat status dbconsole
echo Running oracle services:
net start|find "orcl"||(echo No service running!)
popd