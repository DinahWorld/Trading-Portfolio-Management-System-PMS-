# 📝 Trading & Portfolio Management System (MVP)

A **Java Spring Boot MVP** simulating a **Portfolio Management System (PMS)** for trading and finance 🚀.  
Designed to showcase clean architecture, multithreading, and modern Java patterns.

---

## 🌟 Features

- 🗂️ **Create portfolios** with unique IDs  
- 💹 **Add financial instruments** (stocks, bonds, options)  
- 📈📉 **Record trades** (BUY / SELL)  
- ⚖️ **Calculate positions**: quantity & average price per instrument  
- 🌐 **Expose REST APIs** for all operations  

**Endpoints example**:

```http
POST /portfolios
POST /instruments
POST /trades
GET /portfolios/{id}/positions
