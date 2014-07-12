@echo off

call envvars-server.bat

if "%1"=="" (
  echo command options: install
) else (
  call :%1
)
exit /b 0

:install
  plink -pw %PASSWORD% %USERNAME%@%IP% "echo 123qwe|sudo -S apt-get -y --force-yes install leiningen"
  plink -pw %PASSWORD% %USERNAME%@%IP% "lein --help"
exit /b 0
