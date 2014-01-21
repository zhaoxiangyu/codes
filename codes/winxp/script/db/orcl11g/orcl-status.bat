call setpath.bat
set ORACLE_UNQNAME=F:\app\Administrator\product\11.2.0\dbhome_1
call emctl.bat status dbconsole
echo Running oracle 11g services:
net start|find /I "11g" || (echo No services running!)
popd
echo Command "services.msc /s" to launch services pannels!
pause