# FarmFlow - Backend

Spring Boot REST API powering **FarmFlow**, a farm management app for tracking crop seasons, farm details, and income/expense transactions.

**Live API:** https://farmflow-backend-ir2w.onrender.com
**Frontend repo:** [farm_flow_frontend](https://github.com/Shubham-860/farm_flow_frontend)

> Hosted on Render's free tier - the first request after inactivity may take 30–90s to respond while the server wakes up.

---

## Features

- JWT-based authentication (register/login)
- Farm management - create and manage multiple farms per user
- Crop season tracking - start/end dates, active/complete status
- Income & expense transactions per crop season, categorized (seeds, fertilizer, labour, machinery, etc.)
- Role-based access control (`USER` / `ADMIN`)
- RESTful API with clean layered architecture (Controller → Service → Repository)

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 4 |
| Security | Spring Security + JWT |
| Database | MySQL (hosted on [Aiven](https://aiven.io)) |
| ORM | Spring Data JPA / Hibernate |
| Build Tool | Maven |
| Containerization | Docker |
| Hosting | Render |

## Project Structure

```
src/main/java/com/shubham/farmflow_backend/
├── config/        # Security & CORS configuration
├── controller/     # REST endpoints
├── dto/           # Request/response data transfer objects
├── entity/        # JPA entities (Farm, CropSeason, SeasonTransaction, User)
├── repository/    # Spring Data JPA repositories
└── service/       # Business logic + JWT filter
```

## Data Model

- **User** - account holder (email, name, hashed password, role)
- **Farm** - belongs to a user, has a name and area (in acres)
- **CropSeason** - belongs to a farm, tracks a single crop cycle (crop name, dates, active/complete status)
- **SeasonTransaction** - belongs to a crop season, an income or expense entry with category, amount, quantity, and unit price

## Running Locally

### Prerequisites
- Java 21
- Maven (or use the included `./mvnw` wrapper)
- A running MySQL instance (local or cloud)

### Setup

```bash
git clone https://github.com/Shubham-860/FarmFlow_Backend.git
cd FarmFlow_Backend
```

Set the database environment variables:

```bash
DB_URL=jdbc:mysql://localhost:3306/farmflow_backend?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
DB_USERNAME=root
DB_PASSWORD=your_local_password
```

Run:

```bash
./mvnw spring-boot:run
```

On Windows PowerShell, run:

```powershell
.\mvnw.cmd spring-boot:run
```

The API will be available at `http://localhost:8080`.

## Environment Variables

| Variable | Description | Required |
|---|---|---|
| `DB_URL` | Full JDBC connection string | Yes in production |
| `DB_USERNAME` | Database username | Yes in production |
| `DB_PASSWORD` | Database password | Yes in production |
| `PORT` | Server port (Render sets this automatically) | No |

## API Overview

| Method | Endpoint | Description | Auth Required |
|---|---|---|---|
| POST | `/auth/register` | Register a new user | No |
| POST | `/auth/login` | Log in, receive JWT | No |
| GET | `/actuator/health` | Health check | No |
| GET/POST/PUT/DELETE | `/farm/**` | Manage farms | Yes |
| GET/POST/PUT/DELETE | `/cropseason/**` | Manage crop seasons | Yes |
| GET/POST/PUT/DELETE | `/seasontransaction/**` | Manage income/expense records | Yes |
| GET/PUT | `/user/**` | View and update user details | Yes |
| GET | `/report/**` | Dashboard and filtered farm reports | Yes |
| GET/DELETE | `/admin/**` | Administrative dashboard and user management | Admin only |


## Deployment

This backend is deployed on **Render** using the included `Dockerfile`, with the database hosted on **Aiven MySQL**. The health endpoint is available at [`/actuator/health`](https://farmflow-backend-ir2w.onrender.com/actuator/health).
