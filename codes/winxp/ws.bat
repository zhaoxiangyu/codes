@echo off
if ""%1"" == """" (
  echo command options: github ubuntu office list
) else (
  call :%1
)
exit /b 0

:github
  pushd E:\code-repo2\github\codes
exit /b

:ubuntu
  pushd E:\he\linux\ubuntu
exit /b

:office
  pushd E:\he-xun
exit /b

:list
  echo pushd E:\he-xun
  echo pushd E:\he\linux\ubuntu
  echo pushd E:\code-repo2\github\codes
exit /b
