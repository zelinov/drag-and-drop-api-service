# Drag And Drop Api Service

### Переменные окружения:

| name                 | type    | default                                                    | description                                                                                               |
|----------------------|---------|------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|
| DATA_SOURCE_URL      | string  | jdbc:postgresql://localhost:6432/drag_and_drop_api_service | полный url для коннекта к базе данных                                                                     |
| DATA_SOURCE_USERNAME | string  | drag_and_drop_api_service                                  | имя пользователя бд                                                                                       |
| DATA_SOURCE_PASSWORD | string  | password                                                   | пароль бд                                                                                                 |
| WINDOW_SIZE          | integer | 1024                                                       | минимальный размер<br/>окна в рамках которого<br/>происходит балансировка;<br/>является стартовым зазором |
| LOAD_FACTOR          | double  | 0.9                                                        | коэффициент перегрузки окна                                                                               |

### API

```
GET /api/v1/items
Query params:
limit - requered
lastPosition - not requered (default 0)
```

```
POST /api/v1/items/{itemId}/move/up
no body
```

```
POST /api/v1/items/{itemId}/move/down
no body
```

```
POST /api/v1/items/{itemId}/move
{
    "leftItemId": string, - идентификатор айтема слева (сверху) от позиции в которую хотим вставить
    "rightItemId": string - идентификатор айтема справа (снизу) от позиции в которую хотим вставить
}
```

Файл с генерацией данных лежит по пути
```
./src/main/resources/migrations/sql/1756215609_data.sql
```

Так же есть конфигурация pgbouncer
```
./src/main/resources/pgbouncer/pgbouncer.ini
```

После старта приложения стоит выполнить команду

```
VACUUM (ANALYZE, VERBOSE) items;
```