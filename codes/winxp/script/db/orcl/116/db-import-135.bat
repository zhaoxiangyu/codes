rem imp.exe log=t.log file=D:\he\qb\itis2_20120813.dmp userid="sys/qweasd@orcl@75 as sysdba" buffer=30720 commit=no full=yes grants=yes ignore=yes indexes=yes rows=yes show=no constraints=yes

rem imp.exe log=t.log file=D:\he\qb\itis2_20120813.dmp userid="qb/qweasd" buffer=30720 commit=no full=yes grants=yes ignore=yes indexes=yes rows=yes show=no constraints=yes 

rem imp.exe log=t.log file=D:\he\qb\dic_value.dmp userid="qb/qweasd" buffer=30720 commit=no full=yes grants=yes ignore=yes indexes=yes rows=yes show=no constraints=yes

imp.exe log=t.log file=f:\135dmp\wlw116135.dmp userid="qb/qweasd" fromuser=wlw116135 touser=wlw116120 buffer=30720 commit=yes grants=yes ignore=yes indexes=yes rows=yes show=no constraints=yes
