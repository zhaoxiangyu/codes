@echo off
set CURDIR=%~dp0

if "%1"=="" (
  echo command options: build
) else (
  call :%1
)
exit /b 0

:build
  if not exist win32\nul (
    mkdir win32
    pushd win32
    cmake -G "Visual Studio 8 2005" ..  
    popd win32
  )
exit /b 0
