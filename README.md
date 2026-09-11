# Duck Store — Warehouse & Order Pricing

A full-stack coding exercise implementing a duck warehouse and order-pricing system.

## Tech Stack

### Backend

* Java 17
* Spring Boot 4.0.8
* Spring Web
* Spring Data JPA
* PostgreSQL
* Flyway
* Jakarta Bean Validation
* Maven

### Frontend

* React
* Vite
* JavaScript

### Database

* PostgreSQL 17
* Docker Compose

---

# Project Structure

```text
duck-store/
├── src/
│   ├── main/
│   │   ├── java/com/duckstore/
│   │   │   ├── config/
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── repository/
│   │   │   └── service/
│   │   │
│   │   └── resources/
│   │       ├── application.yml
│   │       └── db/migration/
│   │
│   └── test/
│
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   └── services/
│   └── package.json
│
├── docker-compose.yml
├── pom.xml
└── README.md
```

---

# Prerequisites

Install:

* Java 17
* Maven
* Docker
* Node.js and npm

---

# Running PostgreSQL

PostgreSQL is provided through Docker Compose.

From the project root:

```bash
docker compose up -d
```

The database configuration is:

```text
Database: duck_store
Username: duckstore
Password: duckstore
Host: localhost
Port: 5432
```

To stop PostgreSQL:

```bash
docker compose down
```

The database uses a Docker volume so the data survives container restarts.

---

# Running the Backend

From the project root:

```bash
mvn spring-boot:run
```

The backend starts on:

```text
http://localhost:8080
```

Flyway automatically creates and validates the database schema during application startup.

---

# Running the Frontend

Open another terminal:

```bash
cd frontend
npm install
npm run dev
```

The frontend runs at:

```text
http://localhost:5173
```

---

# Warehouse API

## Add Duck

```http
POST /api/ducks
```

Example:

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "price": 10.00,
  "quantity": 10
}
```

### Merge behavior

If an active duck already exists with the same:

* color
* size
* price

the quantity is increased instead of creating another active row.

This operation is implemented using a PostgreSQL atomic upsert and a database uniqueness constraint so simultaneous add requests do not create duplicate active records or lose quantity updates.

---

# List Ducks

```http
GET /api/ducks
```

Only non-deleted ducks are returned.

Results are sorted by quantity in ascending order.

Example response:

```json
[
  {
    "id": 1,
    "color": "RED",
    "size": "MEDIUM",
    "price": 10.00,
    "quantity": 10
  }
]
```

---

# Update Duck

```http
PUT /api/ducks/{id}
```

Example:

```json
{
  "price": 12.50,
  "quantity": 20
}
```

Only price and quantity can be updated.

Color and size are intentionally not part of the update request.

---

# Delete Duck

```http
DELETE /api/ducks/{id}
```

Deletion is logical.

The database row remains, but the duck is marked as deleted and is no longer returned by the warehouse listing.

---

# Create Order

```http
POST /api/orders
```

Example:

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "quantity": 10,
  "destinationCountry": "India",
  "shippingMode": "LAND"
}
```

The order request does not contain a price.

The backend resolves the price from the active warehouse stock.

The response contains:

* package type
* protection type(s)
* total price
* itemized price breakdown

Example:

```json
{
  "packageType": "CARDBOARD",
  "protectionTypes": [
    "POLYSTYRENE_BALLS"
  ],
  "total": 217.81,
  "priceBreakdown": [
    {
      "description": "Base price",
      "amount": 100.00
    },
    {
      "description": "Cardboard packaging -1%",
      "amount": -1.00
    },
    {
      "description": "India destination +19%",
      "amount": 18.81
    },
    {
      "description": "Land shipping +$10/unit",
      "amount": 100.00
    }
  ]
}
```

---

# Packaging Rules

The packaging rules are implemented in `PackagingService`.

| Duck Size | Package   |
| --------- | --------- |
| XLarge    | Wood      |
| Large     | Wood      |
| Medium    | Cardboard |
| Small     | Plastic   |
| XSmall    | Plastic   |

Protection rules:

| Shipping | Package        | Protection                                  |
| -------- | -------------- | ------------------------------------------- |
| Air      | Wood/Cardboard | Polystyrene balls                           |
| Air      | Plastic        | Bubble-wrap bags                            |
| Land     | Any            | Polystyrene balls                           |
| Sea      | Any            | Moisture-absorbing beads + bubble-wrap bags |

---

# Pricing Rules

Pricing is calculated using `BigDecimal` so monetary calculations are performed accurately to cents.

The calculation sequence is:

```text
Base price
    ↓
Bulk discount
    ↓
Packaging adjustment
    ↓
Country adjustment
    ↓
Shipping charge
    ↓
Final total
```

## Base Price

```text
quantity × warehouse price
```

## Bulk Discount

More than 100 units:

```text
20% discount
```

## Packaging Adjustment

```text
Wood       +5%
Plastic    +10%
Cardboard  -1%
```

## Destination Adjustment

```text
USA       +18%
Bolivia   +13%
India     +19%
Other     +15%
```

## Shipping

```text
Sea   +$400
Land  +$10/unit
Air   +$30/unit
```

For Air shipping with more than 1000 units, the Air shipping charge is reduced by 15%.

---

# Pricing Example

For:

```text
Price: $10
Quantity: 101
Package: Cardboard
Country: USA
Shipping: Air
```

The calculation is:

```text
Base:
101 × $10 = $1,010.00

20% bulk discount:
-$202.00

Running total:
$808.00

Cardboard -1%:
-$8.08

Running total:
$799.92

USA +18%:
+$143.99

Running total:
$943.91

Air:
101 × $30 = $3,030.00

Final:
$3,973.91
```

---

# Multiple Warehouse Prices

The order request contains color and size but does not contain a price.

The exercise does not specify which price should be selected if multiple active warehouse records exist for the same color and size with different prices.

The implementation therefore uses a deterministic assumption:

> When multiple active prices exist for the requested color and size, the lowest active price is selected.

This behavior is implemented by ordering matching active ducks by price ascending and selecting the first result.

---

# Validation

Warehouse and order requests use Jakarta Bean Validation.

Examples of invalid requests include:

* missing required fields
* price less than or equal to zero
* quantity less than the allowed minimum
* invalid enum values

Validation failures return:

```text
HTTP 400 Bad Request
```

---

# Error Handling

The backend uses a centralized `GlobalExceptionHandler`.

Examples:

```text
404 Not Found
```

for a missing duck or unavailable warehouse item.

```text
400 Bad Request
```

for validation failures.

```text
409 Conflict
```

for database integrity conflicts.

---

# Concurrency

The warehouse add operation is designed to be safe under simultaneous requests.

The database has a unique constraint for active records based on:

```text
color + size + price
```

The insert uses PostgreSQL:

```sql
ON CONFLICT ... DO UPDATE
```

with:

```text
existing quantity + incoming quantity
```

Therefore, concurrent additions for the same active color/size/price combination are merged atomically by PostgreSQL.

---

# Design

The backend separates responsibilities into:

```text
Controller
    ↓
Service
    ↓
Repository
    ↓
PostgreSQL
```

Pricing and packaging are separated into their own services:

```text
OrderService
    ├── PackagingService
    ├── PricingService
    └── DuckRepository
```

The frontend similarly separates:

```text
Components
    ↓
API Services
    ↓
Spring Boot REST API
```

Pricing logic is kept on the backend rather than duplicated in React.

---

# Important Scope Decisions

The implementation intentionally does not:

* physically delete warehouse rows
* expose deleted ducks in the listing
* allow color/size changes through the update API
* put price calculation logic in the frontend
* introduce a separate store-management UI
* reduce warehouse quantity when an order is created

The exercise does not explicitly require inventory deduction when an order is created, so the implementation does not invent that behavior.

---

# Stopping the Application

Stop the Spring Boot application normally.

Stop PostgreSQL with:

```bash
docker compose down
```

To remove the database volume as well:

```bash
docker compose down -v
```

**Warning:** removing the volume deletes the PostgreSQL data.
