# **Citronix: Lemon Farm Management Application**

## **Project Overview**
Citronix is a management application designed for lemon farms. It enables farmers to track their production, harvests, and sales while optimizing tree productivity based on their age.

---

## **Technologies**
- **Backend**: Spring Boot (RESTful APIs, JPA for data management, Hibernate for ORM).
- **Database**: PostgreSQL (H2 for development/testing).
- **Testing**: JUnit, Mockito
- **Others**: Docker, Swagger (API documentation).

---

## **Setup Instructions**

### **Backend**
1. Clone the repository.
2. Navigate to the backend directory and build the project:
   ```bash
   mvn clean install
   ```
3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

### **Database**
- Configure the database connection in the backend `application.yml` file.

---