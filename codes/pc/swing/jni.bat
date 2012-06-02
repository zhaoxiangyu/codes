REM using vc++ vcvarsall.bat
call "E:\C\Microsoft Visual Studio 9.0\VC\vcvarsall.bat" x86
@echo on
@echo INCLUDE=%INCLUDE%
@echo PATH=%PATH%
@echo LIB=%LIB%
@echo LIBPATH=%LIBPATH%

set java_home=C:\Program Files\Java\jdk1.6.0_03
cl /c /I"%java_home%\include" /I"%java_home%\include\win32" /Fobuild\c\org_sharp_jni_Win32.obj c\org_sharp_jni_Win32.c 
link /libpath:"%java_home%\lib" /out:build\c\org_sharp_jni_Win32.dll build\c\org_sharp_jni_Win32.obj /dll