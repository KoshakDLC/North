@echo off
setlocal
cd /d "%~dp0"

set "JAVA_BIN="
if exist "C:\Users\lowfi\.jdks\liberica-26.0.2\bin\javac.exe" set "JAVA_BIN=C:\Users\lowfi\.jdks\liberica-26.0.2\bin"
if not defined JAVA_BIN call "%~dp0..\loader\find-java.bat"
if not defined JAVA_BIN (
  echo [!] Need JDK 17+
  exit /b 1
)
if not exist "%JAVA_BIN%\javac.exe" (
  echo [!] javac missing in %JAVA_BIN%
  exit /b 1
)

if exist out rmdir /s /q out
mkdir out
pushd src
"%JAVA_BIN%\javac.exe" --release 17 -encoding UTF-8 -d ..\out north\license\*.java
if errorlevel 1 (
  popd
  exit /b 1
)
popd
"%JAVA_BIN%\jar.exe" --create --file north-license-server.jar --main-class north.license.Server -C out .
echo [+] north-license-server.jar
