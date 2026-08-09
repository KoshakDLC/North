@echo off
rem Заливает клиент в GitHub. Запускать из корня проекта.
setlocal
cd /d "%~dp0"

set "REPO=https://github.com/KoshakDLC/LowFree.git"
set "BRANCH=main"

where git >nul 2>&1
if errorlevel 1 (
  echo [!] Git не установлен.
  echo     Скачай его с https://git-scm.com/download/win, поставь и запусти этот файл заново.
  pause
  exit /b 1
)

for /f "delims=" %%e in ('git config --get user.email 2^>nul') do set "GITMAIL=%%e"
if not defined GITMAIL (
  echo [!] Не задан автор коммитов. Выполни один раз:
  echo         git config --global user.name "KoshakDLC"
  echo         git config --global user.email "почта@от.github"
  pause
  exit /b 1
)

if not exist ".git" (
  echo [*] Создаю репозиторий...
  git init -b %BRANCH%
  if errorlevel 1 goto :fail
)

git remote get-url origin >nul 2>&1
if errorlevel 1 (
  git remote add origin %REPO%
) else (
  git remote set-url origin %REPO%
)

echo [*] Добавляю файлы...
git add -A
if errorlevel 1 goto :fail

git diff --cached --quiet
if errorlevel 1 (
  git commit -m "Исходники клиента low free"
  if errorlevel 1 goto :fail
) else (
  echo [*] Изменений нет, коммит не нужен.
)

echo [*] Отправляю в %REPO%
echo     При первом пуше откроется окно входа в GitHub.
git push -u origin %BRANCH%
if errorlevel 1 goto :fail

echo.
echo [+] Готово. Дальше GitHub Actions соберёт клиент и обновит релиз latest,
echo     откуда загрузчик скачает джарник.
pause
exit /b 0

:fail
echo.
echo [!] Команда завершилась с ошибкой, смотри вывод выше.
pause
exit /b 1
