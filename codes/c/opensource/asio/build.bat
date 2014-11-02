@echo off

set ASIO_HOME=e:\opensource\asio-1.10.4
set MAKE=mingw32-make.exe
if ""%1"" == """" (
	echo command options:mingw
) else (
	call :%1
)
exit /b 0

:mingw
	echo building using mingw
	REM set BOOSTDIR=XX
	pushd %ASIO_HOME%\src
	%MAKE% -f Makefile.mgw	
	%MAKE% -f Makefile.mgw check
	popd
exit /b 0
