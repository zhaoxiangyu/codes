echo on
cd %~dp0
call riqi.bat 0
set today=%dtdd%
call riqi.bat -1
set yesterday=%dtdd%
call import-day.bat %yesterday%>"j-merge-text-%today%.log"