# Order Application Service

Сервис управления заказами с Telegram-уведомлениями. Пользователи регистрируются через бота, создают заказы и получают уведомления о смене статуса.

## Стек
Java 21, Spring Boot 4.1
Spring Data JPA, Hibernate
Spring Security (аутентификация по tgChatId)
PostgreSQL
Telegram Bot API
Docker

## Функционал
Регистрация через Telegram-бота (/start)
Создание заказа (/neworder)
Просмотр списка заказов (/orders)
Просмотр заказа по номеру (/order N)
REST API для ручной смены статусов
Контейнеризация (Docker Compose)

## Быстрый старт
1. Создать .env файл с данными:

TELEGRAM_BOT_TOKEN=your_bot_token_here
TELEGRAM_BOT_USERNAME=your_bot_username_here
POSTGRES_DB=order_app
POSTGRES_USER=order_app_adm
POSTGRES_PASSWORD=your_password_here

2. Запустить:

docker-compose up -d

3. Найти бота в Telegram и написать /start

REST API
POST /orders/create — Создать заказ
GET /orders — Список заказов
GET /orders/{id} — Заказ по UUID
GET /orders/by-number/{n} — Заказ по номеру
PATCH /orders/{id}/processing — В обработку
PATCH /orders/{id}/shipped — Отправлен
PATCH /orders/{id}/executed — Выполнен
PATCH /orders/{id}/cancelled — Отменён

Авторизация: заголовок X-Telegram-Chat-Id

Структура проекта
src/main/java/ru/pet_project/order_app/
controller/ # REST контроллеры
dto/ # Input/Output DTO
entity/ # JPA сущности
enums/ # OrderStatus
mapper/ # Entity ↔ DTO
repository/ # Spring Data репозитории
security/ # TgAuthFilter, SecurityConfig
service/ # Бизнес-логика
telegram/ # TgBot, команды

Команды бота
/start — регистрация
/help — список команд
/neworder Название | Описание — создать заказ
/orders — мои заказы
/order N — заказ №N
