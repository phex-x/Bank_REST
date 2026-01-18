# Bank REST API

REST API на Spring Boot для управления пользователями, картами, транзакциями и процессом блокировки карт с аутентификацией на основе JWT.

## Возможности

* JWT аутентификация: вход, регистрация, обновление токена, выход.
* Эндпоинты для пользователей: список/поиск карт, проверка баланса, переводы, запрос на блокировку.
* Эндпоинты для администратора: управление пользователями, картами и запросами на блокировку.
* Миграции с Liquibase.
* Сохранение данных в PostgreSQL.
* Swagger UI и документация OpenAPI.

## Требования

* Java 17
* Maven 3.9+
* PostgreSQL 14+ (или Docker)
* Доступный порт 8080

## Быстрый старт

### Сборка проекта

```bash
mvn clean package
```

### Сборка Docker образа

```bash
docker-compose build
```

### Запуск контейнеров

```bash
docker-compose up -d
```

### Остановка контейнеров

```bash
docker-compose down
```

## Настройки

* Конфигурация приложения: `src/main/resources/application.yml`
* Настройки JWT: `jwt.secret`, `jwt.expiration`, `jwt.issuer` (см. `JWTConfig`)
* Настройки БД через `application.yml` или переменные окружения Docker Compose.

## Документация API

* Swagger UI: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
* OpenAPI JSON: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

Если используется статический YAML `src/main/resources/static/openapi.yaml`:

* Swagger UI с YAML: [http://localhost:8080/swagger-ui/index.html?url=/openapi.yaml](http://localhost:8080/swagger-ui/index.html?url=/openapi.yaml)

## Аутентификация

* Эндпоинты `/api/auth/**` — публичные.
* Эндпоинты `/api/user/**` — требуют роль `USER`.
* Эндпоинты `/api/admin/**` — требуют роль `ADMIN`.
* Передавайте JWT в заголовке: `Authorization: Bearer <token>`