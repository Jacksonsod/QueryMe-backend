@echo off
echo Testing Custom Sandbox Exceptions...
echo.

REM Compile exception classes
echo Compiling exception classes...
javac -cp "src/main/java" src\main\java\com\year2\queryme\sandbox\exception\*.java
if %ERRORLEVEL% neq 0 (
    echo Failed to compile exception classes
    pause
    exit /b 1
)

REM Compile test class
echo Compiling test class...
javac -cp "src/main/java;src/test/java" src\test\java\com\year2\queryme\sandbox\exception\SandboxExceptionTest.java
if %ERRORLEVEL% neq 0 (
    echo Failed to compile test class
    pause
    exit /b 1
)

REM Run the test
echo Running test...
java -cp "src/main/java;src/test/java" org.junit.platform.console.ConsoleLauncher --select-class=com.year2.queryme.sandbox.exception.SandboxExceptionTest

echo.
echo Test completed!
pause
