# 📬 Customer Notification Address Facade System

This project is a Spring Boot microservice designed to centralize and manage customer contact information, notification preferences, and notification delivery statuses. It acts as the **single source of truth** for all recipient addresses and their delivery states, enabling other microservices to efficiently fetch and update customer delivery data.

---

## 🚀 Key Features

### 👤 User Management
- Admin account creation and management
- Secure login for administrators

### 👥 Customer Management
- Add, update, delete customer records
- Store customer details (name, contact info, etc.)
- Display a list of customers with notification preferences

### 🏠 Address Management
- Manage multiple address types (email, SMS, postal)
- Update/remove outdated addresses
- Associate multiple addresses with a single customer

### ✅ Preference Management
- Manage opt-in/opt-out preferences for different notification types
- Update preferences on customer request
- Display current preferences per customer

### 🔌 Integration and API
- RESTful API for address and preference access
- Secure, authenticated API access
- Batch update support

### 📡 Notification Tracking
- Track notification status: delivered, failed, or pending
- Endpoints for querying notification status
- Reports for delivery success rates

### 🔍 Search and Filtering
- Search customers by name, contact info, or preferences
- Sort and filter customer lists

### 📊 Reporting
- Generate opt-in statistics and notification reports
- Visual delivery metrics (success/failure rates)

---

## 🛠️ Tech Stack

- **Java .**
- **Spring Boot 3**
- **Spring Data JPA**
- **Spring Security**
- **Thymeleaf** (for admin UI)
- **Postgres** 
- **Tailwind CSS / Bootstrap**

- **Maven**

---

## ⚙️ Setup Instructions

### 1. Clone the Repository

```bash
git clone https://github.com/phutko555/NotifyCenter.git
cd NotifyCenter


> ℹ️ By default, the application runs on **port 8666**. 
> ```properties
> server.port=8666
> ```
