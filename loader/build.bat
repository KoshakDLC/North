@echo off
setlocal
cd /d "%~dp0"

call "%~dp0find-java.bat"
if not defined JAVA_BIN (
  echo [!] Не найдена Java 17 или новее.
  echo     Поставь JDK 17+ или задай JAVA_HOME на нужную сборку.
  exit /b 1
)
if not exist "%JAVA_BIN%\javac.exe" (
  echo [!] В "%JAVA_BIN%" нет javac — это JRE, а не JDK.
  echo     Для сборки нужен JDK 17 или новее.
  exit /b 1
)

echo [*] Компилятор: %JAVA_BIN%\javac.exe

if exist out rmdir /s /q out
mkdir out

rem Компилируем из src по относительным путям: абсолютные ломаются, если в пути проекта есть пробелы.
rem --release 17 даёт байткод, который запустится на любой Java 17 и новее.
pushd "src"
"%JAVA_BIN%\javac.exe" --release 17 -encoding UTF-8 -d "..\out" wild\loader\*.java
if errorlevel 1 (
  popd
  echo [!] Ошибка компиляции.
  exit /b 1
)
popd

"%JAVA_BIN%\jar.exe" --create --file low-free-loader.jar --main-class wild.loader.Loader -C out .
if errorlevel 1 (
  echo [!] Не удалось собрать jar.
  exit /b 1
)

echo [+] Готово: low-free-loader.jar
