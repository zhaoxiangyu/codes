@echo off
set CURDIR=%~dp0

call %CURDIR%\envvars-server.bat

if "%1"=="" (
  echo command options: download upload build clean
) else (
  call :%1
)
exit /b 0

:download
  if not exist storm-0.8.1.zip (
    wget https://github.com/apache/incubator-storm/archive/0.8.1.zip
    rename 0.8.1 storm-0.8.1.zip
  )
  if not exist storm-0.8.1\nul (
    mkdir storm-0.8.1
  )
  7z x -ostorm-0.8.1 storm-0.8.1.zip
exit /b 0

:upload
  pscp -r -pw %PASSWORD% storm-0.8.1 %USERNAME%@%IP%:work/opensource
exit /b 0

:build
  plink -pw %PASSWORD% %USERNAME%@%IP% "cd work/opensource/storm-0.8.1/incubator-storm-0.8.1; lein install"
exit /b 0

:clean
  rmdir /s /q %CURDIR%\storm-0.8.1
  del %CURDIR%\storm-0.8.1.zip
exit /b 0
