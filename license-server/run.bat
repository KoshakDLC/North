@echo off
setlocal
cd /d "%~dp0"

if not exist north-license-server.jar call "%~dp0build.bat"
if errorlevel 1 exit /b 1

set "JAVA_BIN=C:\Users\lowfi\.jdks\liberica-26.0.2\bin"
if not exist "%JAVA_BIN%\java.exe" (
  call "%~dp0..\loader\find-java.bat"
)

if "%~1"=="create" (
  "%JAVA_BIN%\java.exe" -Dnorth.license.root="%~dp0data" -jar north-license-server.jar create %*
  exit /b %ERRORLEVEL%
)

echo [*] Starting license server...
"%JAVA_BIN%\java.exe" -Dnorth.license.root="%~dp0data" -Dnorth.license.port=8787 -jar north-license-server.jar
