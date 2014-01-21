schtasks /create /tn merge-text /tr D:\GpsTask\j-merge-text.bat /sc daily /st 02:00:00
schtasks /create /tn upload-text /tr D:\GpsTask\j-ftp.bat /sc daily /st 06:00:00
rem netsh advfirewall firewall add rule name="ftp" dir=in action=allow program=%SystemRoot%\System32\ftp.exe enable=yes protocol=tcp
rem netsh advfirewall firewall add rule name="ftp" dir=in action=allow program=%SystemRoot%\System32\ftp.exe enable=yes protocol=udp
pause