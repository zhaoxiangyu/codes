@echo off

set TRACE=--trace
set MAKE=mingw32-make.exe
if ""%1"" == """" (
	echo command options:genprj build clean
) else (
	call :%1
)
exit /b 0

:genprj
	echo generate win32-cbp-mingw project
	test -f glut-3.7.6-bin\glut.h && (mkdir glut-3.7.6-bin\GL & mv glut-3.7.6-bin\glut.h glut-3.7.6-bin\GL\)
	test -d build\win32-cbp-mingw || mkdir build\win32-cbp-mingw
	pushd build\win32-cbp-mingw
	cmake -G"CodeBlocks - MinGW Makefiles" %TRACE% ..\..
	popd
exit /b 0

:build
	echo building using mingw
	set BOOSTDIR=e:/opensource/boost_1_56_0
	pushd %ASIO_HOME%\src
	%MAKE% -f Makefile.mgw	
	%MAKE% -f Makefile.mgw check
	popd
exit /b 0

:clean
	rm -rf build\win32-cbp-mingw
exit /b 0
