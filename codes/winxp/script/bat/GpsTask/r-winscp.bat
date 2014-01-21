echo %1
set day=%1
set DIR_GPS_DATA=D:\GPSData
set LOCAL_DIR=%DIR_GPS_DATA%\%day%
set FILE=%DIR_GPS_DATA%\%day%\%day%.txt
if not exist %FILE% (
	echo file not exists: %FILE%
	echo task aborted
	exit /b 1
)
echo running WinSCP.com
winscp511\WinSCP.com /command "option batch abort" "open root:svr@116@10.9.113.126" "cd /datafile/ftp/gpsdata" "put %FILE%" "close" "exit"
echo WinSCP exit code: %errorlevel%
if "%errorlevel%"=="0" (
	echo deleting file %FILE%
	if exist %FILE% (
	   del %FILE%
	   echo file deleted: %FILE%
	) else (
	   echo file not exists: %FILE%
	)
) else (
	echo failed to upload file to ftp server.
)
rem cmd /k
rem pause