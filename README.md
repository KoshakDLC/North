# North

Клиент для Minecraft 1.21.8 на Fabric и загрузчик к нему.

## Сборка

Нужен JDK 21.

```bat
gradlew build
```

Готовый джарник появится в `build/libs`.

Каждый пуш в `main` собирается через GitHub Actions и обновляет релиз
[`latest`](../../releases/latest) — оттуда загрузчик и берёт свежую сборку.

## Система ключей

1. Подними сервер лицензий:

```bat
cd license-server
run.bat create --count 1 --days 30
run.bat
```

2. В лоадере вставь ключ и нажми «Активировать» (API по умолчанию
   `http://127.0.0.1:8787`, меняется в настройках).
3. Запуск без валидной лицензии блокируется. Файл лицензии:
   `%APPDATA%\WildClient\license.json`.

Для разработки без сервера: создай файл `%USERPROFILE%\.north-dev-unlock` и
запускай с `-Dnorth.license.dev=1`. Простой `license.bypass` больше не работает.

Сессия лицензии живёт ~24 часа (подписанный `sessionUntil`). Потом нужна
онлайн-проверка на твоём license-server — отозванные ключи отваливаются.

Подробности — в [license-server/README.md](license-server/README.md).

## Загрузчик

```bat
cd loader
run.bat
```

Проверяет ключ, скачивает последнюю сборку, кладёт её в `mods` и открывает игру.
Подробности — в [loader/README.md](loader/README.md).

## Структура

| Путь | Что внутри |
| --- | --- |
| `src/main/java` | исходники клиента |
| `src/main/resources` | ассеты: шрифты, шейдеры, звуки |
| `libs/` | библиотеки, которые в оригинале лежали внутри jar |
| `loader/` | десктопный загрузчик |
| `license-server/` | API активации ключей |
| `.github/workflows` | сборка и публикация релизов |
