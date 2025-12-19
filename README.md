# Vert.x Core Starter (Java 21 + Docker)

This repository demonstrates the **core features of Vert.x**, built with **Java 21 (LTS)** and prepared for **Docker-based deployment**.

It is designed as a **learning and reference project** for understanding Vert.x Core concepts such as the event loop, verticles, and non-blocking asynchronous programming — without adding frameworks on top.

---

## 🚀 Key Features

- **Vert.x Core fundamentals**
  - Verticles
  - Event Loop model
  - Asynchronous & non-blocking APIs
  - HTTP server using Vert.x Core

- **Modern Java**
  - Built with **Java 21 (LTS)**
  - Runs on Eclipse Temurin Java runtime

- **Docker support**
  - Packaged as a **fat JAR**
  - Runs inside a lightweight Java 21 Docker image
  - Suitable for containerized environments (Docker, ECS, Kubernetes)

---

## 🧱 Tech Stack

- **Java:** 21 (LTS)
- **Vert.x:** >5.0.6 (Core)
- **Build Tool:** Maven
- **Containerization:** Docker
- **Base Image:** eclipse-temurin:21-jre

---


---

## ▶️ Running the Application

### 1️⃣ Run locally (development)

```bash
mvn clean compile
mvn vertx:run
```

## 2️⃣ Run as a fat JAR
```bash
mvn clean package
java -jar target/*-fat.jar
```


## 3️⃣ Run with Docker
Build the Docker image
```bash
docker build -t my-vertx-app:latest .
docker run --rm -p 8888:8888 my-vertx-ap:latest
docker stop my-vertx-app
```

## Application will be available at:

http://localhost:8888


## 🧠 Notes

- Hot redeploy is intended only for local development
- The project intentionally avoids frameworks on top of Vert.x to keep core concepts clear

##  📌 License

This project is provided for learning and experimentation purposes.
