@echo off
setlocal
cd /d "%~dp0"

call "%~dp0find-java.bat"
if not defined JAVA_BIN (
  echo [!] Не найдена Java 17 или новее.
  echo     Загрузчику нужна она — та Java, что стоит в PATH, слишком старая.
  echo     Поставь JDK 17+ или задай JAVA_HOME.
  pause
  exit /b 1
)

if not exist north-loader.jar (
  call "%~dp0build.bat"
  if errorlevel 1 (
    pause
    exit /b 1
  )
)

rem "run.bat debug" запускает с консолью, чтобы было видно ошибки.
if /i "%~1"=="debug" (
  "%JAVA_BIN%\java.exe" -jar north-loader.jar
  echo.
  echo [*] Загрузчик завершился с кодом %errorlevel%.
  pause
) else (
  start "" "%JAVA_BIN%\javaw.exe" -jar north-loader.jar
)
