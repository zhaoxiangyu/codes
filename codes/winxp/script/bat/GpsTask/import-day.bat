echo on
echo %1
set day=%1
set DIR_GPS_DATA=D:\GPSData
pushd %DIR_GPS_DATA%\%day%
cd
copy *.txt ..\%day%.txt
del /q/f *.txt 
move ..\%day%.txt %DIR_GPS_DATA%\%day%
popd