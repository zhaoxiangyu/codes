@echo ----------bat file----------

@rem CALL E:\C\VS2005\VC\vcvarsall.bat

@rem SET PATH=E:\CommonTools\upx307w;%PATH%
@rem SET PATH=E:\CommonTools\Git\bin;%PATH%
@rem SET JAVA_HOME=C:\Program Files\Java\jdk1.6.0_03
@rem SET MSVC_HOME=E:\C\Microsoft Visual Studio 9.0\VC

SET NDK_ROOT=E:\Android\android-ndk-r6b-windows\android-ndk-r6b
SET PRJ_LOC=%cd%
SET CYGWIN_LOC=E:\C\Cygwin

@rem CALL cygwn batch file
CD /d %CYGWIN_LOC%\bin

FOR /F %%A IN ('cygpath %PRJ_LOC%') DO (
@ECHO %%A
SET PRJ_LOC_CYG=%%A
)
@ECHO PRJ_LOC_CYG: %PRJ_LOC_CYG%

@ECHO %0 %1 %2 %3 %*
bash.exe --login -i %PRJ_LOC_CYG%/build/build.sh %*
