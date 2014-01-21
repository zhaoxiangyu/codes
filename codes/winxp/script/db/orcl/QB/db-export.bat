set PATH=d:\oracle\product\10.2.0\db_1\bin;%PATH%
exp.exe log=t.log file=D:\he\qb\he-xcool_db.dmp userid="qb/qweasd" buffer=30720 grants=yes indexes=yes rows=yes constraints=yes
dir d:\he\qb\he-xcool_db.dmp