# Large Data Retrieval: JdbcTemplate | Hibernate 6 | Spring Data JPA

A comprehensive comparison of different data access layers for retrieving millions of records from a database, demonstrating performance optimization techniques with JdbcTemplate, Hibernate 6, and Spring Data JPA.

📘 Blog Post: [Large Data Retrieval](https://jarmx.blogspot.com/2022/12/retrieve-million-data-jdbctemplate.html)

## 📋 Table of Contents

- [Overview](#overview)
- [Key Considerations](#key-considerations)
- [Environment Setup](#environment-setup)
- [Database Setup](#database-setup)
- [Implementation Examples](#implementation-examples)
  - [JdbcTemplate](#jdbctemplate)
  - [Hibernate 6 with Jakarta 3.1](#hibernate-6-with-jakarta-31)
  - [Spring Data JPA](#spring-data-jpa)
- [Performance Results](#performance-results)
- [Technologies Used](#technologies-used)
- [References](#references)

## 🎯 Overview

This project demonstrates the most common data access layers that can help retrieve large datasets (10 million records) from a database using:

- **JdbcTemplate** - Spring's JDBC abstraction layer
- **Hibernate 6** - ORM framework with Jakarta Persistence 3.1
- **Spring Data JPA** - High-level abstraction over JPA

## 🔍 Key Considerations

### Fetch Size Optimization
- **Oracle**: Default fetch size is only 10, requiring multiple database roundtrips
- **PostgreSQL/MySQL**: JDBC drivers cache the entire result set upfront
- **Recommended**: Set fetch size to 1/2 or 1/4 of expected result size for optimal performance

### Memory Management
- **JdbcTemplate**: Can handle 10M records without batching
- **Hibernate/JPA**: Implements batch processing to prevent `OutOfMemoryError`
- **Batch Size**: 1M records per batch for large datasets

### Pagination Limitations
- Spring Data JPA supports `PageRequest.of(0, 1M)` 
- **Issue**: `Page offset exceeds Integer.MAX_VALUE` for subsequent pages
- **Solution**: Custom repository implementation using EntityManager

## 🐳 Environment Setup

### MySQL Docker Setup

```bash
# Pull MySQL image
docker pull mysql/mysql-server:latest

# Run MySQL container
docker run -d -p 3306:3306 --name mysql-docker-container \
  -e MYSQL_ROOT_PASSWORD=mypass \
  -e MYSQL_DATABASE=test_db \
  -e MYSQL_USER=test \
  -e MYSQL_PASSWORD=test_pass \
  mysql/mysql-server:latest
```

## 🗄️ Database Setup

### Create Table
```sql
CREATE TABLE customers (
    id BIGINT PRIMARY KEY,
    name VARCHAR(128)
);
```

### Insert 10M Records
```sql
INSERT INTO customers (id, name) 
SELECT n, CONCAT('Customer', n) 
FROM (
    select a.N + b.N * 10 + c.N * 100 + d.N * 1000 + e.N * 10000 + f.N * 100000 + g.N * 1000000 + 1 N 
    from (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) a,
         (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) b,
         (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) c,
         (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) d,
         (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) e,
         (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) f,
         (select 0 as N union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6 union all select 7 union all select 8 union all select 9) g
) t
```

## 🚀 Implementation Examples

### JdbcTemplate

**Technology Stack:**
- Spring Boot 3.0.0
- Spring JDBC 6
- Java 17
- MySQL 8

**Key Features:**
- Direct SQL execution
- No batch processing needed for 10M records
- Fastest execution time
- Minimal memory overhead

**Performance:** ~7.7 seconds for 10M records

### Hibernate 6 with Jakarta 3.1

**Technology Stack:**
- Hibernate 6.1.6.Final
- Jakarta Persistence 3.1
- Java 17
- MySQL 8

**Key Features:**
- Batch processing (1M records per batch)
- ORM mapping capabilities
- Memory-safe processing
- Multiple database queries

**Performance:** ~79.8 seconds for 10M records

### Spring Data JPA

**Technology Stack:**
- Spring Boot 3.0.1
- Spring Data JPA
- Java 17
- MySQL 8

**Key Features:**
- Stream processing with read-only hints
- Custom repository implementation
- Pagination support (with limitations)
- Transaction management

**Performance:** 
- 1M records: ~4.9 seconds
- 10M records: ~82.3 seconds (via EntityManager)

## 📊 Performance Results

| Technology | Records | Time (ms) | Memory Usage | Queries |
|------------|---------|-----------|--------------|---------|
| JdbcTemplate | 10M | 7,764 | Low | 1 |
| Hibernate 6 | 10M | 79,857 | Medium | 11 |
| Spring Data JPA | 1M | 4,905 | Low | 1 |
| Spring Data JPA | 10M | 82,283 | Medium | 11 |

## 🛠️ Technologies Used

- **Java 17**
- **Spring Boot 3.x**
- **Spring JDBC 6**
- **Hibernate 6.1.6.Final**
- **Jakarta Persistence 3.1**
- **MySQL 8.0**
- **Docker**
- **Maven**
- **Lombok**

## 📁 Project Structure

```
├── large-data-JdbcTemplate/
│   ├── src/main/java/com/henry/
│   │   ├── config/AppConfig.java
│   │   ├── dao/CustomerDAO.java
│   │   ├── dao/impl/CustomerDAOImpl.java
│   │   ├── model/Customer.java
│   │   └── App1.java
│   └── src/main/resources/db.properties
├── large-data-hibernate6/
│   ├── src/main/java/com/henry/
│   │   ├── model/Customer.java
│   │   └── App2.java
│   └── src/main/resources/META-INF/persistence.xml
└── large-data-spring-jpa/
    ├── src/main/java/com/henry/
    │   ├── dao/CustomerRepository.java
    │   ├── dao/CustomerRepositoryCustom.java
    │   ├── dao/impl/CustomerRepositoryCustomImpl.java
    │   ├── model/Customer.java
    │   ├── service/CustomerService.java
    │   ├── service/impl/CustomerServiceImpl.java
    │   └── resource/CustomerResource.java
    └── src/main/resources/application.yml
```

## 🏃‍♂️ Getting Started

1. **Clone the repository**
2. **Start MySQL Docker container**
3. **Create database table and insert test data**
4. **Choose your preferred implementation:**
   - JdbcTemplate: Run `App1.java`
   - Hibernate 6: Run `App2.java`  
   - Spring Data JPA: Run Spring Boot application and call endpoints

### API Endpoints (Spring Data JPA)

```bash
# Retrieve 1M records
curl http://localhost:9000/api/customers/findAll

# Retrieve 10M records
curl http://localhost:9000/api/customers/findAllWithEM
```

## 📈 Optimization Tips

1. **Set appropriate fetch size** (especially for Oracle)
2. **Use batch processing** for large datasets
3. **Consider read-only transactions** for query operations
4. **Monitor memory usage** and adjust batch sizes accordingly
5. **Use streaming** for very large result sets
6. **Choose the right tool** based on your performance requirements

## 🔗 References

- [Hibernate User Guide](https://docs.jboss.org/hibernate/orm/6.0/userguide/html_single/Hibernate_User_Guide.html)
- [JDBC Fetch Size Documentation](https://docs.oracle.com/middleware/1212/toplink/TLJPA/q_jdbc_fetch_size.htm)
- [Hibernate Performance Tuning Tips](https://vladmihalcea.com/hibernate-performance-tuning-tips/)



---

**Note:** Performance results may vary based on hardware, database configuration, and network conditions. Always benchmark in your specific environment.
