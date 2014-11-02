@echo off

set ASIO_HOME=e:\opensource\asio-1.10.4
if ""%1"" == """" (
	echo usage:mingw
) else (
	call :%1
)
exit /b 0

:mingw
	echo building using mingw
	set BOOSTDIR=XX
	pushd %ASIO_HOME%\src
	make -f Makefile.mgw	
	make -f Makefile.mgw check
	popd
exit /b 0
