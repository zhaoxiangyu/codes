REM https://github.com/talios/clojure-maven-plugin
@echo off
if ""%1""=="""" (
    echo "command options: new build run repl test"
) else (
    call :%1
)
exit /b 0

:new
    call mvn archetype:generate
exit /b

:build
    call mvn clean
    call mvn clojure:compile
exit /b

:repl
    call mvn clojure:repl
exit /b

:run
    REM call :build >NUL
    call mvn compile clojure:run -Dclojure.mainClass=org.sharpx.parser.main
exit /b

:test
    call mvn clojure:test
exit /b
