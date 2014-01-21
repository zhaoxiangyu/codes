call setpath.bat
pushd D:\he\orcl11g
rem sqlplus wlw116120/qweasd@orcl11g @login.sql
cmd /k sqlldr wlw116120/qweasd@orcl11g control=D:\he\orcl11g\gps-data.ctl log=D:\he\orcl11g\gps-data-sqlldr.log readsize=20971520 date_cache=220000 