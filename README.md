# 🚀 Система управления банковскими картами (Bank_REST)

## 📦 Описание
Backend-приложение на Java (Spring Boot) для управления банковскими картами с поддержкой ролей, JWT, шифрованием, Liquibase, Docker и OpenAPI.

## 🏁 Быстрый старт

### 1. Клонирование и сборка
```bash
git clone https://github.com/PaatoM/Bank_REST.git
cd Bank_REST
mvn clean package
```

### 2. Запуск через Docker Compose
```bash
docker-compose up --build
```
- Приложение: http://localhost:8080
- БД PostgreSQL: localhost:5432 (user: `bankuser`, pass: `bankpass`, db: `bankdb`)

### 3. Миграции
Миграции Liquibase применяются автоматически при запуске.

### 4. Документация API
Swagger UI будет доступен по адресу: http://localhost:8080/swagger-ui.html (если springdoc подключён)

## 🛡️ Аутентификация и роли
- JWT-токен обязателен для всех защищённых эндпоинтов.
- Роли: `ADMIN`, `USER`.
- Регистрация: `/auth/sign-up` (POST, JSON)
- Вход: `/auth/sign-in` (POST, JSON)

## 📚 Основные эндпоинты

### Пользователь
- `POST /auth/sign-up` — регистрация
- `POST /auth/sign-in` — вход (получение JWT)
- `GET /user/me` — текущий пользователь
- `GET /user/all` — все пользователи (ADMIN)
- `PATCH /user/{id}/role` — смена роли (ADMIN)

### Карты
- `POST /card/create` — создать карту
- `GET /card/my` — мои карты (USER, с пагинацией)
- `GET /card/all` — все карты (ADMIN, с пагинацией)
- `PATCH /card/{id}/status` — сменить статус карты
- `DELETE /card/{id}/delete` — удалить карту

### Переводы
- `POST /transfer/transfer` — перевод между своими картами
- `GET /transfer/my` — мои переводы (USER)
- `GET /transfer/all` — все переводы (ADMIN)

## 📝 Примеры запросов

### Регистрация пользователя
```json
POST /auth/sign-up
{
  "username": "user1",
  "email": "user1@email.com",
  "password": "password123"
}
```

### Вход и получение JWT
```json
POST /auth/sign-in
{
  "username": "user1",
  "password": "password123"
}
```

### Создание карты
```json
POST /card/create
{
  "encryptedCardNumber": "1234567890123456",
  "owner": {"id": 1},
  "expirationDate": "2026-12-31T23:59:59",
  "balance": 1000.00
}
```

## 🧪 Тесты
Запуск тестов:
```bash
mvn test
```

## 🗄️ Миграции
- Liquibase: `src/main/resources/db/migration/changelog-1.0-init.yaml`
- Применяются автоматически при запуске через Spring Boot или Docker Compose.

## 🐳 Docker
- `docker-compose.yml` содержит сервисы для приложения и PostgreSQL.
- Для локального запуска достаточно Docker и Maven.

## 🛠️ Технологии
- Java 17+, Spring Boot, Spring Security, Spring Data JPA, PostgreSQL, Liquibase, Docker, JWT, Swagger (OpenAPI)

---

**Временные README-файлы удалены.**

---

Для подробной OpenAPI-спецификации см. файл `docs/openapi.yaml` (заполнить по необходимости).
