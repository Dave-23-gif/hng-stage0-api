# HNG Stage 0 - Backend Task

## API Overview
This project is a Spring Boot backend API that integrates with the Genderize API to classify names by gender.

---

## Live API
Base URL:
https://tinfoil-garnet-atypical.ngrok-free.dev

Example Endpoint:
GET /api/classify?name=David

Full URL:
https://tinfoil-garnet-atypical.ngrok-free.dev/api/classify?name=David

---

## 📥 Request
Method: GET  
Endpoint: /api/classify  
Query Parameter:
- name (string)

---

## 📤 Response (Success)

```json
{
  "status": "success",
  "data": {
    "name": "david",
    "gender": "male",
    "probability": 0.99,
    "sample_size": 1234,
    "is_confident": true,
    "processed_at": "2026-04-13T12:00:00Z"
  }
}

##Tech Stack
Java
Spring Boot
Maven
Genderize API
