# Weather Forecast API

Spring Boot REST API for uploading weather observations from CSV and querying historical weather data.

## Features

- Upload weather data from CSV file
- Query records by month/day across all years
- Query records by month across all years
- Get monthly temperature stats (high, median, minimum) for a specific year

## Tech Stack

- Java 17
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- MySQL (default runtime database)
- H2 (used in tests)
- Maven

## Project Structure

- `src/main/java/controller` - REST controller
- `src/main/java/service` - Upload and query business logic
- `src/main/java/repository` - JPA repository
- `src/main/java/model` - Entity/models
- `src/main/java/dto` - Response DTOs
- `src/main/resources/application.properties` - app configuration

## Prerequisites

- JDK 17+
- Maven 3.9+ (or use `mvnw` / `mvnw.cmd`)
- MySQL running locally (unless overriding datasource settings)

## Configuration

Default config is in `src/main/resources/application.properties`.

Environment variables supported:

- `DB_URL` (default: `jdbc:mysql://localhost:3306/weatherforecast?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`)
- `DB_DRIVER` (default: `com.mysql.cj.jdbc.Driver`)
- `DB_USERNAME` (default: `cheru`)
- `DB_PASSWORD` (default: `cheru123`)
- `SERVER_PORT` (default: `9090`)

## Run Locally

Using Maven wrapper (Windows):

```powershell
.\mvnw.cmd spring-boot:run
```

Using Maven wrapper (macOS/Linux):

```bash
./mvnw spring-boot:run
```

App starts on: `http://localhost:9090`

## Run Tests

```powershell
.\mvnw.cmd test
```

Tests use in-memory H2 config from `src/test/resources/application.properties`.

## API Endpoints

Base path: `/api/weather`

### 1) Upload CSV

`POST /api/weather/upload`

Form field:

- `file` (multipart file)

Example:

```powershell
curl -X POST "http://localhost:9090/api/weather/upload" `
  -F "file=@C:\path\to\weather.csv"
```

Response:

`Weather data uploaded successfully`

### 2) Query by Date Across Years

`GET /api/weather/by-date?date=MM-dd`

Example:

```text
GET /api/weather/by-date?date=11-01
```

Returns all entries matching month/day across all years.

### 3) Query by Month Across Years

`GET /api/weather/by-month?month=1..12`

Example:

```text
GET /api/weather/by-month?month=11
```

Returns all entries in the given month across all years.

### 4) Yearly Monthly Temperature Stats

`GET /api/weather/yearly-temperature-stats?year=YYYY`

Example:

```text
GET /api/weather/yearly-temperature-stats?year=1997
```

Returns a map keyed by month (`"01"` to `"12"`), each containing:

- `high`
- `median`
- `minimum`

## CSV Format

The upload parser requires these column names in the header:

- `datetime_utc` (format: `yyyyMMdd-HH:mm`)
- `_tempm`
- `_hum`
- `_pressurem`
- `_conds`

Rows with `-9999`, blank, or invalid numeric values are treated as `null` for numeric fields.

## Notes

- Table name: `weather_forecast`
- JPA DDL mode is `update` in main config
- Default multipart size limit is `50MB`
