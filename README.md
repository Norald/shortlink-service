# 📌 ShortLink Service – Test Task Specification

## 📖 Overview

Create an application like a **URL shortener**.

The Java application must be a **monolithic REST API** service built using **Spring Boot**, **Spring Web**, **Spring Data**.

The project must be implemented in **two stages**:
- **Stage 1:** short links are stored temporarily in **Redis**.
- **Stage 2:** short links are stored persistently in **PostgreSQL**.

---

## ✅ Application Description

The service must provide the following core functionality:

1. The User can **create a short link** for any valid URL.
    - When creating a short link, the User can optionally specify:
        - **custom alias** (optional, unique)
        - **expiration time** in minutes (optional)

2. The service must **generate a short unique code** if the alias is not provided.

3. The short link must **redirect the User** to the original URL.

4. There must be an **endpoint to delete** a short link by its alias.

5. There must be an **endpoint to list all active short links** with optional filters:
    - by **creation date range**
    - by **alias mask** (like search)

---

## 🔄 Stage 1 – Redis-only

- Short links must be stored in **Redis** with a TTL (time to live) if an expiration time is set.
- If no expiration is set, store indefinitely (for test purposes, default TTL = **1 day**).
- Redis must be used directly with **Spring Data Redis** or **Lettuce client**.
- The system must check for **alias uniqueness**.
- The system must **log each redirect attempt** (log to console).
- Caching and actual storage are the same (**Redis**).

---

## 🗃️ Stage 2 – PostgreSQL

- Migrate storage to **PostgreSQL** for persistent storage.
- Use **Liquibase** to manage DB schema (**XML**, **YAML**, **SQL**, or **JSON**).
- Tables must include:
    - `short_link`: `id`, `original URL`, `alias`, `creation date`, `expiration date/time`, `active status`.
    - `redirect_event`: `id`, `short_link_id`, `timestamp`, `IP address`, `User-Agent`.
- Implement **redirect logging**:
    - Each redirect must create a `redirect_event` record.
- Old short links must be **automatically disabled** when expired (can be done on access).

---

## ⚙️ Additional Requirements

- For both stages:
    - Use **Java 21** or higher.
    - Use **Gradle** as a build tool.
    - **No Spring Security** required.
    - Provide **Swagger/OpenAPI** documentation (dynamic or static).
    - Skip input/output validation (**optional**).
    - Add **10 predefined short links** via Liquibase changelog.
    - Generate **90 random short links** with random URLs at application startup.
    - Provide **unit and functional tests** for core models, controllers, and services:
        - Use **JUnit 5**, **Spring Test**.
    - The application must run **natively** (without containerizing the app itself).
    - Redis and PostgreSQL must run in **Docker containers**, described via **docker-compose.yml**.
    - **Cache hits/misses** and **redirect statistics** must be printed in logs.

---

## 🏆 Bonus (Optional)

- Implement a **cleanup task** that scans the DB and disables expired links.
- Add a **statistics endpoint** that returns:
    - total number of short links
    - number of active short links
    - total number of redirects per link

---

## 📦 Expected Deliverables

1. ✅ Complete project code in a **Git repository**
2. ✅ **Docker Compose** file to start PostgreSQL and Redis containers
3. ✅ **Liquibase changelog** for DB
4. ✅ Sample **README** with instructions
5. ✅ Working **tests**
6. ✅ Swagger documentation accessible at `/swagger-ui` or `/api-docs`

---

## 🧪 Additional Testing Requirements

- Tests must use **Testcontainers** for integration tests:
    - For **PostgreSQL** and **Redis** containers during tests.
    - Include at least **one test** that starts a real PostgreSQL container and checks persistence.
    - Include at least **one test** that starts a real Redis container and checks key expiration.
- Use **JUnit 5** for **Testcontainers integration**.
