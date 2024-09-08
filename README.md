Описание проекта
Account Service — это RESTful веб-сервис для управления пользователями, счетами и транзакциями между ними. Приложение поддерживает следующие функции:

Описание проекта
Account Service — это RESTful веб-сервис для управления пользователями, счетами и транзакциями между ними. Приложение поддерживает следующие функции:

Управление пользователями: создание, поиск по ID и документу.
Управление счетами: создание счетов для пользователей, пополнение баланса.
Управление транзакциями: получение списка транзакций для конкретного счета.
Приложение написано на Java 19+ с использованием Spring Boot 3.x, Maven и PostgreSQL в качестве базы данных.

Основные зависимости
Java 19 или выше — язык программирования.
Spring Boot 3.x — основная фреймворк для разработки приложения.
Maven — сборщик проекта.
PostgreSQL — база данных для хранения информации о пользователях, счетах и транзакциях.
Liquibase — инструмент для управления миграциями базы данных.
JUnit Jupiter, Mockito — библиотеки для модульного и интеграционного тестирования.
Swagger/OpenAPI — для автоматической генерации документации API.
Требования для запуска
Java 19 или выше: Убедитесь, что Java 19 установлена на вашем компьютере.
PostgreSQL: Установите и настройте PostgreSQL.
Maven: Если Maven не установлен, его можно скачать здесь.
Конфигурация базы данных
Приложение использует PostgreSQL в качестве основной базы данных. Чтобы настроить подключение к вашей базе данных, отредактируйте файл src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/account_service
spring.datasource.username=postgres
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.liquibase.enabled=true
Создайте базу данных account_service в PostgreSQL:

psql -U your_username -c "CREATE DATABASE account_service;"
Убедитесь, что имя пользователя и пароль PostgreSQL совпадают с указанными в application.properties.

Миграции базы данных
Для управления версионностью схемы базы данных используется Liquibase. После запуска приложения, Liquibase автоматически применит миграции из папки src/main/resources/db/changelog.

Запуск приложения
1. Сборка проекта
Чтобы собрать проект и установить зависимости, выполните команду:

mvn clean install
2. Запуск приложения
Для запуска приложения выполните команду:

mvn spring-boot:run
Приложение будет доступно по адресу: http://localhost:8080

API Документация
Swagger UI
Для доступа к документации и тестирования API с помощью Swagger, перейдите по следующему адресу:

http://localhost:8080/swagger-ui.html
Swagger предоставляет возможность видеть все доступные конечные точки API, их параметры и тестировать их через UI.

OpenAPI спецификация
Спецификацию API в формате OpenAPI можно найти по адресу:

http://localhost:8080/v3/api-docs
Описание API
Пользователи
POST /api/users — Создание нового пользователя.

Пример запроса:

{
  "name": "Alice",
  "documentNumber": "123456789",
  "documentType": "PASSPORT"
}
GET /api/users/{id} — Получение информации о пользователе по ID.

GET /api/users/document/{documentNumber} — Получение пользователя по номеру документа.

Счета
POST /api/accounts — Создание нового счета для пользователя.

Пример запроса:

POST /api/accounts?userId=1&currency=USD
POST /api/accounts/{id}/deposit — Пополнение баланса счета.

Пример запроса:

POST /api/accounts/1/deposit?amount=100.00&currency=USD
GET /api/accounts/{id} — Получение информации о счете по ID.

Транзакции
GET /api/transactions/account/{accountId} — Получение списка транзакций для указанного счета.
Тестирование
Unit и Integration тесты
Для запуска всех тестов (модульных и интеграционных) используйте следующую команду:

mvn test
Тесты покрывают основные аспекты бизнес-логики, включая создание пользователей, счетов, пополнение баланса и проверку транзакций.

Docker
Для упрощения развёртывания можно создать Docker-контейнер для вашего приложения.

Dockerfile
Пример Dockerfile для приложения:

FROM openjdk:19-jdk-alpine
VOLUME /tmp
COPY target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
ENTRYPOINT ["java", "-jar", "/account-service.jar"]
Запуск через Docker
Соберите проект с помощью Maven:

mvn clean install
Соберите Docker-образ:

docker build -t account-service .
Запустите контейнер:

docker run -p 8080:8080 account-service
ile

Пример Dockerfile для приложения:

FROM openjdk:19-jdk-alpine
VOLUME /tmp
COPY target/account-service-0.0.1-SNAPSHOT.jar account-service.jar
ENTRYPOINT ["java", "-jar", "/account-service.jar"]
Запуск через Docker
Соберите проект с помощью Maven:

mvn clean install
Соберите Docker-образ:

docker build -t account-service .
Запустите контейнер:

docker run -p 8080:8080 account-service
