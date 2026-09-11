# Duck Store Frontend

React frontend for the Duck Store warehouse management application.

## Tech Stack

* React
* Vite
* JavaScript
* CSS
* Fetch API

## Structure

```text
src/
├── components/
│   ├── AddDuckForm.jsx
│   ├── CreateOrderForm.jsx
│   ├── DuckTable.jsx
│   ├── EditDuckForm.jsx
│   └── OrderResult.jsx
│
├── services/
│   ├── duckService.js
│   └── orderService.js
│
├── App.jsx
├── App.css
└── main.jsx
```

### Components

* **AddDuckForm** — adds warehouse inventory.
* **EditDuckForm** — updates duck price and quantity.
* **DuckTable** — displays active warehouse inventory and provides edit/delete actions.
* **CreateOrderForm** — collects order details and requests price calculation.
* **OrderResult** — displays the calculated package, protection, total, and price breakdown.

### Services

API communication is kept separate from the UI components.

* `duckService.js` — communicates with the duck warehouse APIs.
* `orderService.js` — communicates with the order API.

## Backend URL

The frontend expects the Spring Boot backend to run on:

```text
http://localhost:8080
```

The frontend communicates with:

```text
GET    /api/ducks
POST   /api/ducks
PUT    /api/ducks/{id}
DELETE /api/ducks/{id}

POST   /api/orders
```

## Running the Frontend

Install dependencies:

```bash
npm install
```

Start the development server:

```bash
npm run dev
```

The application will normally be available at:

```text
http://localhost:5173
```

## Production Build

Create a production build:

```bash
npm run build
```

Preview the production build:

```bash
npm run preview
```

## Notes

* `node_modules/` is not included in the submission. It can be recreated using `npm install`.
* `package-lock.json` should be retained.
* The frontend does not access the database directly.
* Pricing calculations are performed by the backend.
