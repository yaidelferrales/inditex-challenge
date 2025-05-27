
# Pricing Module

This module provides REST APIs to consult product prices based on brand, product, and date criteria. It follows a clean architecture with a Spring Boot backend.

---

## Features

- Query prices by brand, product, and date
- Swagger/OpenAPI documentation available
- Uses H2 in-memory database for development (configurable)
- Flyway migrations for database schema management

---

## Getting Started

### Prerequisites

- Java 21+ (or your configured Java version)
- Maven or Gradle build tool
- Docker (optional, if you want to run DB in container)
- IDE (IntelliJ, VSCode, Eclipse, etc.)

---

### Build and Run

Using Maven:

```
mvn clean install
mvn spring-boot:run
```

Using Docker:

```
docker build -t inditex-challenge . 
docker run -p "8080:8080" inditex-challenge
```

---

### Access API

- Base URL: `http://localhost:8080/api/prices`
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- H2 Console: `http://localhost:8080/h2-console`

---

## API Usage

### Find Price by Brand, Product, and Date

**Endpoint:**

```
GET /api/prices/search?brandId={brandId}&productId={productId}&date={date}
```

**Parameters:**

| Name      | Type     | Description                         | Example              |
|-----------|----------|-----------------------------------|----------------------|
| brandId   | Long     | ID of the brand                   | 1                    |
| productId | Long     | ID of the product                 | 35455                |
| date      | ISO Date | Date/time for price validity check| 2020-06-14T10:00:00  |

**Example Request:**

```http
GET http://localhost:8080/api/prices/search?brandId=1&productId=35455&date=2020-06-14T10:00:00
```

**Success Response (200):**

```json
{
  "id": 123,
  "brand": { "id": 1, "name": "BrandName" },
  "product": { "id": 35455, "name": "ProductName", "sku": "SKU123" },
  "priority": 1,
  "startDate": "2020-06-14T00:00:00",
  "endDate": "2020-12-31T23:59:59",
  "price": 35.50,
  "priceList": 1,
  "currency": "EUR"
}
```

## Postman

With the source code is provided a postman collection called `Inditex Challenge Api.postman_collection.json` with all the requests available in the api.

## Database

- The module uses Flyway for schema migrations.
- Default database is H2 in-memory for development.
- You can configure PostgreSQL or another database in `application.yaml`.

---

## Configuration

Example datasource configuration in `application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:pricing-db
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: none
    show-sql: true
  flyway:
    enabled: true
    locations: classpath:db/migration
```

---

## Testing

Run tests with:

```
./mvnw test
```

---

## Contribution

Contributions are welcome! Feel free to open issues or submit pull requests.

---

## License

[MIT License](LICENSE)
