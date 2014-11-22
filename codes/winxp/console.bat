@echo off
cd /d E:\code-repo2\github\codes\winxp

set PATH=%PATH%;C:\Program Files\PuTTY
set PATH=%PATH%;C:\Program Files\MySQL\MySQL Server 5.5\bin
set PATH=%PATH%;E:\tools
set PATH=%PATH%;E:\tools\clink_0.4.2
REM set PATH=E:\tools\shell.w32-ix86;%PATH%
set PATH=C:\Program Files\Git\cmd;%PATH%
set PATH=%PATH%;C:\Program Files\Lynx - web browser
set PATH=C:\Program Files\7-Zip;%PATH%
set PATH=C:\Program Files\Vim\vim74;%PATH%
set PATH=C:\Program Files\PSPad editor;%PATH%
set PATH=C:\Program Files\Sublime Text 2;%PATH%
set PATH=C:\Program Files\Windows Resource Kits\Tools\;%PATH%

set PATH=C:\Program Files\MongoDB 2.6 Standard\bin;%PATH%

call e:\he\py\tools-py.bat setpath

REM set PATH=%PATH%;C:\Program Files\Java\jdk1.6.0_45\bin

set HOME=E:\user-home
set PATH=E:\code-repo2\github\codes\winxp\user-home\bat-wrapper;%PATH%
set JAVA_HOME=C:\Program Files\Java\jdk1.7.0_65

REM set PATH=C:\Program Files\Git\bin;%PATH%
set PATH=E:\tools\gnuwin32\bin;%PATH%
set PATH=E:\tools\emacs-24.3-bin-i386\emacs-24.3\bin;%PATH%

REM set PATH=C:\MinGW\bin;%PATH%
REM set PATH=%PATH%;C:\MinGW\msys\1.0\bin
set PATH=C:\Program Files\CodeBlocks\MinGW\bin;%PATH%
set PATH=C:\Program Files\CMake 2.8\bin;%PATH%
set PATH=E:\tools\cscope-15.8a;%PATH%

set PATH=%PATH:"=%
cmd.exe /s /k clink.bat inject
