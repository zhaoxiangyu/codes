@echo off 

REM github user account: blueocci

if "%1"=="" (
  echo command options: pub install help
) else (
  call :%1
)

exit /b 0

:help
	git config -l
exit /b 0

:pub
	echo publish to github ...
	git add .
	git commit -m "publish"
	git push
exit /b 0

:install
	echo running install ...
	git config --global user.name "blueocci_ubuntu"
	git config --global user.email "blueocci@hotmail.com"

	set /p CODESDIR=enter full path of dir where you want to setup git repository:
	mkdir %CODESDIR%

	git clone https://github.com/blueocci/codes.git %CODESDIR%
	pushd %CODESDIR%
	git remote add upstream https://github.com/blueocci/codes.git
	git fetch upstream
	popd
exit /b 0
