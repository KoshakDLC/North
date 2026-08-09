@echo off
rem Ищет Java 17 или новее и возвращает путь к её каталогу bin в переменной JAVA_BIN.
rem В PATH часто лежит старая Java 8, поэтому её версия проверяется, а не берётся на веру.
rem Использование: call "%~dp0find-java.bat"

setlocal enabledelayedexpansion
set "FOUND="

if defined JAVA_HOME call :probe "%JAVA_HOME%\bin"
if not defined FOUND for /d %%d in ("%USERPROFILE%\.jdks\*") do call :probe "%%~fd\bin"
if not defined FOUND for /d %%d in ("%ProgramFiles%\Eclipse Adoptium\*") do call :probe "%%~fd\bin"
if not defined FOUND for /d %%d in ("%ProgramFiles%\Java\*") do call :probe "%%~fd\bin"
if not defined FOUND for /d %%d in ("%ProgramFiles%\Microsoft\*") do call :probe "%%~fd\bin"
if not defined FOUND for /d %%d in ("%ProgramFiles%\Amazon Corretto\*") do call :probe "%%~fd\bin"
if not defined FOUND for /d %%d in ("%ProgramFiles%\Zulu\*") do call :probe "%%~fd\bin"
if not defined FOUND for /d %%d in ("%ProgramFiles%\BellSoft\*") do call :probe "%%~fd\bin"
if not defined FOUND for /f "delims=" %%p in ('where java 2^>nul') do call :probe "%%~dpp."

endlocal & set "JAVA_BIN=%FOUND%"
exit /b 0

:probe
if defined FOUND exit /b 0
set "CAND=%~1"
if not exist "%CAND%\java.exe" exit /b 0

set "VERFILE=%TEMP%\low-free-java-version.txt"
"%CAND%\java.exe" -version > "%VERFILE%" 2>&1
if errorlevel 1 (
  del "%VERFILE%" >nul 2>&1
  exit /b 0
)

set "VER="
for /f "tokens=3" %%v in ('findstr /i "version" "%VERFILE%"') do if not defined VER set "VER=%%v"
del "%VERFILE%" >nul 2>&1
if not defined VER exit /b 0

set "VER=%VER:"=%"
rem Версии вида 1.8.0_501 — это Java 8 и старше, они не подходят.
if "%VER:~0,2%"=="1." exit /b 0

for /f "tokens=1 delims=.-+_" %%m in ("%VER%") do set "MAJOR=%%m"
if not defined MAJOR exit /b 0
if %MAJOR% GEQ 17 set "FOUND=%CAND%"
exit /b 0
