# License server

Сервер активации ключей для NorthLoader / North client.

## Быстрый старт

```bat
cd license-server
build.bat
run.bat create --count 3 --days 30 --role USER
run.bat
```

Сервер слушает `http://127.0.0.1:8787`. Админ-токен печатается при старте
и лежит в `data/admin.token`.

## API

| Метод | Путь | Тело | Заметки |
| --- | --- | --- | --- |
| GET | `/v1/health` | — | жив ли сервер |
| POST | `/v1/activate` | `{"key","hwid"}` | выдаёт подписанный `license.json` + `sessionUntil` |
| POST | `/v1/validate` | `{"keyHash","hwid"}` | продление сессии; отозванный ключ → 403 |
| POST | `/v1/admin/keys` | `{"count","days","role","maxDevices","prefix"}` | заголовок `X-Admin-Token` |
| POST | `/v1/admin/revoke` | `{"key"}` | заголовок `X-Admin-Token` |

Ключи хранятся как SHA-256, не в открытом виде. Подпись — Ed25519
(`data/private.key`). Публичный ключ должен совпадать с тем, что вшит в
лоадер (`License.java`) и клиент (`LocalLicenseService`).

## Выдача ключей

```bat
run.bat create --count 5 --days 90 --role MEDIA --devices 1 --prefix North
```

Или через API:

```bat
curl -X POST http://127.0.0.1:8787/v1/admin/keys ^
  -H "Content-Type: application/json" ^
  -H "X-Admin-Token: <token>" ^
  -d "{\"count\":5,\"days\":30,\"role\":\"USER\",\"maxDevices\":1}"
```
