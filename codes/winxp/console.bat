@echo off
REM cd /d E:\he-xun\prj\naomi-lzhd-storm
cd /d E:\code-repo2\github\codes\winxp

set PATH=%PATH%;C:\Program Files\PuTTY
set PATH=%PATH%;C:\Program Files\MySQL\MySQL Server 5.5\bin
set PATH=%PATH%;E:\tools
set PATH=%PATH%;E:\tools\clink_0.4.2
set PATH=E:\tools\shell.w32-ix86;%PATH%
set PATH=C:\Program Files\Git\cmd;%PATH%
set PATH=%PATH%;C:\Program Files\Lynx - web browser
REM @set PATH=%PATH%;E:\tools\ConEmuPack.140703
set PATH=C:\Program Files\7-Zip;%PATH%
set PATH=C:\Program Files\Vim\vim74;%PATH%
set PATH=C:\Program Files\PSPad editor;%PATH%
set PATH=C:\Program Files\Sublime Text 2;%PATH%
set PATH=C:\Program Files\Windows Resource Kits\Tools\;%PATH%

set PATH=C:\Program Files\MongoDB 2.6 Standard\bin;%PATH%

call e:\he\py\tools-py.bat setpath

REM set PATH=%PATH%;C:\Program Files\Java\jdk1.6.0_45\bin

set HOME=E:\code-repo2\github\codes\winxp\user-home
set PATH=%HOME%\bat-wrapper;%PATH%
REM set USERPROFILE=E:\code-repo2\github\codes\winxp\user-home
set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_65
set PATH=E:\tools\gnuwin32\bin;%PATH%

set PATH=%PATH:"=%
cmd.exe /s /k clink.bat inject
