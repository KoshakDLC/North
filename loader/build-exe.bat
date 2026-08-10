@echo off
setlocal
cd /d "%~dp0"

rem Jar + один exe без автозагрузки Java (меньше ложных срабатываний AV).
call "%~dp0build.bat"
if errorlevel 1 exit /b 1

if not exist "%~dp0north-loader.jar" (
  echo [!] Нет north-loader.jar после сборки.
  exit /b 1
)

set "CSC=%SystemRoot%\Microsoft.NET\Framework64\v4.0.30319\csc.exe"
if not exist "%CSC%" set "CSC=%SystemRoot%\Microsoft.NET\Framework\v4.0.30319\csc.exe"
if not exist "%CSC%" (
  echo [!] Не найден csc.exe — нужна .NET Framework 4.x ^(стоит в Windows^).
  exit /b 1
)

echo [*] Компилятор: %CSC%
echo [*] Собираю NorthLoader.exe…

rem Копируем иконку в короткий путь — csc ломается на пробелах в /win32icon.
set "ICON_TMP=%TEMP%\north-loader.ico"
if exist "%~dp0loader.ico" copy /y "%~dp0loader.ico" "%ICON_TMP%" >nul

if exist "%ICON_TMP%" (
  "%CSC%" /nologo /optimize+ /target:winexe /platform:anycpu ^
    /reference:System.Windows.Forms.dll ^
    /reference:System.Drawing.dll ^
    /win32manifest:"%~dp0tools\app.manifest" ^
    /resource:"%~dp0north-loader.jar",north-loader.jar ^
    /win32icon:"%ICON_TMP%" ^
    /out:"%~dp0NorthLoader.exe" ^
    "%~dp0tools\Launcher.cs"
) else (
  "%CSC%" /nologo /optimize+ /target:winexe /platform:anycpu ^
    /reference:System.Windows.Forms.dll ^
    /reference:System.Drawing.dll ^
    /win32manifest:"%~dp0tools\app.manifest" ^
    /resource:"%~dp0north-loader.jar",north-loader.jar ^
    /out:"%~dp0NorthLoader.exe" ^
    "%~dp0tools\Launcher.cs"
)
if errorlevel 1 (
  echo [!] Ошибка компиляции.
  exit /b 1
)

for %%A in ("%~dp0NorthLoader.exe") do echo [+] Готово: NorthLoader.exe  ^(%%~zA байт^)
echo     Людям нужна Java 17+ ^(если нет — exe откроет adoptium.net^).
echo     Подпись Authenticode сильнее всего снижает детекты VirusTotal.
exit /b 0
