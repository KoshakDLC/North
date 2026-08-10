@echo off
setlocal
cd /d "%~dp0"

set "CSC=%SystemRoot%\Microsoft.NET\Framework64\v4.0.30319\csc.exe"
if not exist "%CSC%" set "CSC=%SystemRoot%\Microsoft.NET\Framework\v4.0.30319\csc.exe"
if not exist "%CSC%" (
  echo [!] Не найден csc.exe — нужна .NET Framework 4.x.
  exit /b 1
)

echo [*] Компилятор: %CSC%
"%CSC%" /nologo /optimize+ /target:winexe /platform:anycpu /reference:System.Windows.Forms.dll /out:"%~dp0low-free.exe" "%~dp0tools\Launcher.cs"
if errorlevel 1 (
  echo [!] Ошибка компиляции.
  exit /b 1
)

echo [+] Готово: low-free.exe
