Notifications Microservice

📋 Project Overview

This project implements a Customer Notification Address Facade System, which serves as a centralized microservice for managing customer contact information, addresses, and notification preferences. It provides a single source of truth for other services in the ecosystem to fetch, update, and track notification-related data.

🔑 Key Features

✅ User Management

Secure admin login using Spring Security.
Admins can manage customer records and system settings.
👤 Customer Management

Add new customers with personal and contact information.
Edit, update, and delete customer records.
View a list of all customers and their preferences.
🏠 Address Management

Store multiple address types (email, SMS, postal, etc.).

Admins can use a hybrid approach:

Choose from predefined address types.
Manually add new types for flexibility.
Remove or update outdated addresses.

🎯 Preference Management

Set notification preferences (opt-in/out for email, SMS, etc.).
View and update current preferences.
Hybrid approach for types: predefined + admin-defined.
🔁 Integration & REST API

RESTful API available for other systems to fetch and update customer data.
Batch updates supported.
📨 Notification Tracking

Track delivery status of notifications: delivered, failed, pending.
Endpoints to query status and generate delivery reports.
🔍 Search & Filtering

Search customers by name, surname, contact info, and preferences.
Sort and filter the customer list based on criteria.
📊 Reporting

Generate reports on customer data (e.g., opt-in stats).
Notification delivery rate statistics.
⚙️ Project Structure

User: Represents admins and customer data.

Address & AddressType: Stores various types of contact details.

Preference & PreferenceType: Represents user opt-in/out settings.

Notification: Tracks the status of message deliveries.

DTOs: Used for request/response data exchange.

Controllers: Manage web pages and REST endpoints.

Services: Contain business logic for user, address, and preference handling.

Repositories: Spring Data JPA for DB access.

SecurityConfig: Secures endpoints with role-based access.

🔐 API Security for Microservice Communication

As i saw the primary role was to manage customer contact data, addresses, and notification preferences.

/api/** Endpoints — why i used SYSTEM Role?

The following API endpoints are intended to be accessed only by external services within the ecosystem:

/api/notifications/** /api/preferences/** /api/address/**

I used security using .hasRole("SYSTEM")**, which means:

Only users or services authenticated with the SYSTEM role can access these endpoints. This ensures that only trusted backend services such as notification dispatchers can call sensitive endpoints like /track, /update, etc.

🔐 Example Security Configuration

.requestMatchers("/api/notifications/**").hasRole("SYSTEM")
Example SYSTEM User for Test

@Bean
public UserDetailsService userDetailsService(PasswordEncoder encoder) {
    UserDetails systemUser = User.withUsername("notifier")
        .password(encoder.encode("securepass"))
        .roles("SYSTEM")
        .build();

    return new InMemoryUserDetailsManager(systemUser);
}
⚙️ Setup

Clone the repository:
git clone https://github.com/phutko555/NotifyCenter.git
cd notifications
Configure the database connection in application.yml or application.properties:
spring.datasource.url=jdbc:postgresql://localhost:5432/notifications
spring.datasource.username=youruser
spring.datasource.password=yourpassword
Run the application:
./mvnw spring-boot:run
Access it in your browser:
Login: http://localhost:8666/login
Admin Dashboard: http://localhost:8666/admin/dashboard
