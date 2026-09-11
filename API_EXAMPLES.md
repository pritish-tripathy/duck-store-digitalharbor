# Duck Store API Examples

Base URL:

```text
http://localhost:8080
```

---

## 1. Add Duck

### Request

```http
POST /api/ducks
Content-Type: application/json
```

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "price": 10.00,
  "quantity": 10
}
```

### Expected

```text
HTTP 200 OK
```

---

## 2. Add Same Duck Again

```http
POST /api/ducks
Content-Type: application/json
```

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "price": 10.00,
  "quantity": 5
}
```

The existing quantity should increase:

```text
10 + 5 = 15
```

There should still be only one active record for:

```text
RED + MEDIUM + $10
```

---

## 3. List Ducks

```http
GET /api/ducks
```

Expected:

```text
HTTP 200 OK
```

Deleted ducks must not appear.

Results are sorted by quantity ascending.

---

## 4. Update Duck

```http
PUT /api/ducks/1
Content-Type: application/json
```

```json
{
  "price": 12.50,
  "quantity": 20
}
```

Only price and quantity are changed.

---

## 5. Delete Duck

```http
DELETE /api/ducks/1
```

Expected:

```text
HTTP 204 No Content
```

The database row remains but is logically deleted.

---

## 6. Create Order

```http
POST /api/orders
Content-Type: application/json
```

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "quantity": 10,
  "destinationCountry": "India",
  "shippingMode": "LAND"
}
```

Expected for a `$10` warehouse price:

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

# Validation Tests

## Invalid Price

```http
POST /api/ducks
```

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "price": -10,
  "quantity": 5
}
```

Expected:

```text
HTTP 400 Bad Request
```

---

## Invalid Quantity

```http
POST /api/ducks
```

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "price": 10,
  "quantity": 0
}
```

Expected:

```text
HTTP 400 Bad Request
```

---

## Invalid Color

```http
POST /api/ducks
```

```json
{
  "color": "BLUE",
  "size": "MEDIUM",
  "price": 10,
  "quantity": 5
}
```

Expected:

```text
HTTP 400 Bad Request
```

---

# Pricing Tests

## Bulk Discount

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "quantity": 101,
  "destinationCountry": "USA",
  "shippingMode": "AIR"
}
```

Verify:

```text
20% bulk discount
```

---

## Air Over 1000

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "quantity": 1001,
  "destinationCountry": "India",
  "shippingMode": "AIR"
}
```

Air base charge:

```text
1001 × $30 = $30,030.00
```

15% reduction:

```text
$4,504.50
```

Final Air charge:

```text
$25,525.50
```

---

# Packaging Tests

Use an available warehouse duck for each size.

```text
XLARGE → WOOD
LARGE  → WOOD
MEDIUM → CARDBOARD
SMALL  → PLASTIC
XSMALL → PLASTIC
```

---

# Shipping Protection Tests

```text
LAND → POLYSTYRENE_BALLS

AIR + WOOD/CARDBOARD
→ POLYSTYRENE_BALLS

AIR + PLASTIC
→ BUBBLE_WRAP_BAGS

SEA
→ MOISTURE_ABSORBING_BEADS
→ BUBBLE_WRAP_BAGS
```

---

# Country Tests

Test each:

```text
USA      → +18%
Bolivia  → +13%
India    → +19%
Other    → +15%
```

---

# Concurrent Add Test

The important invariant is:

```text
One active row per color + size + price
```

For example, start with no active:

```text
RED + MEDIUM + $10
```

Then issue 100 concurrent requests:

```json
{
  "color": "RED",
  "size": "MEDIUM",
  "price": 10.00,
  "quantity": 1
}
```

Expected:

```text
Exactly one active RED/MEDIUM/$10 row

Quantity = 100
```

No quantity updates should be lost.

---

# Notes

The order API does not deduct warehouse quantity because inventory deduction is not explicitly required by the exercise.

If multiple active prices exist for the same color and size, the implementation selects the lowest active price because the order request does not specify a price.
