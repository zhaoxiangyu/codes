set PATH=d:\oracle\product\10.2.0\db_1\bin;%PATH%
exp.exe log=t.log file=f:\135dmp\wlw116135.dmp userid="wlw116135/wlw_116@ora@135" buffer=30720 grants=yes indexes=yes rows=yes constraints=yes
dir f:\135dmp\wlw116135.dmp