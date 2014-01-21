echo on
cd %~dp0
call riqi.bat 0
set today=%dtdd%
call riqi.bat -1
set yesterday=%dtdd%
rem call r-ftp.bat>"j-ftp-%dtdd%.log"
call r-winscp.bat %yesterday%>"j-ftp-%today%.log"