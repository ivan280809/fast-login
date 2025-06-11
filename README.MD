
# 🚀 Fast Login - User Authentication Microservice

**Fast Login** is a lightweight, self-contained user authentication microservice built with Java 21 and Spring Boot 3.x. It allows you to:

- ✅ Register users
- 🔐 Authenticate users and return a JWT
- 🔁 Update user passwords (secured via JWT)

Designed for fast setup, Docker-first deployment, and reuse in larger systems or side projects.

---

## 📦 Technologies

- Java 21
- Spring Boot 3.x
- PostgreSQL
- Docker & Docker Compose
- JWT (JJWT library)

---

## 🧱 Project Structure

```
.
├── user-service/             # Spring Boot microservice
│   ├── Dockerfile
│   └── src/...
├── docker-compose.yml        # Fast Login + PostgreSQL
├── .env                      # Configuration variables
├── README.md
```

---

## ⚙️ Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/ivan280809/fast-login.git
cd fast-login
```

### 2. Create `.env` file with example values

```env
# .env

# PostgreSQL DB config
POSTGRES_DB=user_service
POSTGRES_USER=admin
POSTGRES_PASSWORD=1234

# App DB access config (inside container)
DB_HOST=postgres
DB_PORT=5432
DB_NAME=user_service
DB_USER=admin
DB_PASSWORD=1234

# JWT Secret (must be >= 32 characters for HS256)
SECRET_JWT=N84fK!sF92aJ7V!zLp3mR9xW!sD4gQeT
```

### 3. Build and start with Docker Compose

```bash
docker compose up --build
```

Your app will be running at:  
📍 `http://localhost:8080`

---

## 🐳 docker-compose.yml (included)

```yaml
version: '3.8'

services:
  postgres:
    image: postgres:15
    container_name: postgres-fast-login
    environment:
      POSTGRES_DB: ${POSTGRES_DB}
      POSTGRES_USER: ${POSTGRES_USER}
      POSTGRES_PASSWORD: ${POSTGRES_PASSWORD}
    ports:
      - "5432:5432"
    volumes:
      - pgdata_fast_login:/var/lib/postgresql/data
    networks:
      - fast-login-net

  user-service:
    image: ivan2808/user-service:latest
    container_name: fast-login-service
    environment:
      DB_HOST: ${DB_HOST}
      DB_PORT: ${DB_PORT}
      DB_NAME: ${DB_NAME}
      DB_USER: ${DB_USER}
      DB_PASSWORD: ${DB_PASSWORD}
      SECRET_JWT: ${SECRET_JWT}
    ports:
      - "8080:8080"
    depends_on:
      - postgres
    networks:
      - fast-login-net

volumes:
  pgdata_fast_login:

networks:
  fast-login-net:
```

---

## 🔐 API Endpoints

### ▶️ Register

**POST /register**

```bash
curl -X POST http://localhost:8080/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test",
    "email": "test@mail.com",
    "password": "12345678aA!"
}'
```

### ▶️ Login

**POST /login**

```bash
curl -X POST http://localhost:8080/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test",
    "password": "12345678aA!"
}'
```

📥 Response:
```json
{
  "token": "JWT_TOKEN_HERE",
  "username": "test",
  "role": "USER"
}
```

### 🔁 Change Password

**PUT /me**

```bash
curl -X PUT http://localhost:8080/me \
  -H "Authorization: Bearer JWT_TOKEN_HERE" \
  -H "Content-Type: application/json" \
  -d '{
    "currentPassword": "12345678aA!",
    "newPassword": "NuevaPassSegura2024!"
}'
```

---

## 🛠️ Build your own image

To rebuild and tag a custom version:

```bash
docker build -t youruser/fast-login:latest ./user-service
docker push youruser/fast-login:latest
```

Then update `docker-compose.yml` to use your tag.

---

## 📄 License

MIT License

```text
MIT License

Copyright (c) 2025

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the “Software”), to deal
in the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
OR OTHER DEALINGS IN THE SOFTWARE.
```
