@echo off

set TRACE=--trace
REM set curr_dir=%~dp0
set curr_dir=%CD%
set build_dir=build\win32-vs
if ""%1"" == """" (
	echo command options:genprj build clean
) else (
	call :%1
)
exit /b 0

:genprj
	echo generate win32-vs project
	test -f glut-3.7.6-bin\glut.h && (mkdir glut-3.7.6-bin\GL & mv glut-3.7.6-bin\glut.h glut-3.7.6-bin\GL\)
	test -d %build_dir% || mkdir %build_dir%
	pushd %build_dir%
	cmake -G"Visual Studio 8 2005" %TRACE% %curr_dir%
	popd
exit /b 0

:build
	echo building using vs command line
exit /b 0

:clean
	rm -rf %build_dir%
exit /b 0
