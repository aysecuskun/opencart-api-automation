# OpenCart API Automation

API testing and automation project for **OpenCart** using **Postman** and **RestAssured**.

This project was created to practice API testing on an e-commerce application and to validate important user and shopping cart flows at API level.

## Project Overview

The project covers API test scenarios for the OpenCart application, including:

* User registration
* User login
* Authentication and token handling
* Product search and product information
* Adding products to the shopping cart
* Updating cart quantities
* Retrieving cart contents
* Removing products from the cart
* Address-related API scenarios

The API tests were initially developed and validated using **Postman**. The next stage of the project is to automate the same scenarios using **RestAssured** and Java.

---

## Technologies & Tools

* **Postman** – API testing and collection development
* **Java** – automation language
* **RestAssured** – API automation
* **Maven** – dependency and project management
* **TestNG** – test execution and assertions
* **Git & GitHub** – version control
* **OpenCart** – application under test

---

## API Test Scenarios

### 1. Homepage

**GET**

Validates that the OpenCart homepage API is accessible and returns a successful response.

* Verify HTTP status code
* Verify response body
* Verify page title/content

---

### 2. User Registration

**POST**

Tests the customer registration flow.

Scenarios include:

* Successful registration
* Required field validation
* Invalid registration data
* Duplicate user-related validation

The registration flow also includes validation of the authentication/token response returned by the application.

---

### 3. User Login

**POST**

Tests customer authentication.

Scenarios include:

* Successful login
* Invalid email
* Invalid password
* Empty credentials
* Authentication response validation
* Token handling

The authentication token is used when required by subsequent authenticated API requests.

---

### 4. Product Search / Product Information

**GET**

Tests product-related API functionality.

Scenarios include:

* Search for an existing product
* Search for a non-existing product
* Validate product information
* Validate response status and body

Example products used during testing include OpenCart products such as **HP LP3065** and **iPhone**.

---

### 5. Add Product to Cart

**POST**

Tests adding a product to the shopping cart.

Scenarios include:

* Add a product to an empty cart
* Add another product to the same cart/session
* Add the same product again
* Validate product ID
* Validate quantity
* Validate successful response

The cart API uses the OpenCart session to maintain cart state.

---

### 6. Update Cart Quantity

**POST**

Tests updating the quantity of products already present in the cart.

Scenarios include:

* Increase product quantity
* Decrease product quantity
* Update quantity for an existing product
* Validate updated cart data

---

### 7. Get Cart Contents

**GET**

Validates the products currently stored in the shopping cart.

Checks include:

* Product ID
* Product name
* Quantity
* Price
* Cart contents
* Response status

---

### 8. Remove Product from Cart

**POST**

Tests removing products from the shopping cart.

Scenarios include:

* Remove a product
* Remove one product while keeping other products in the cart
* Validate the cart after removal
* Validate an empty cart

---

### 9. Address

Tests address-related API functionality for the customer.

Scenarios include:

* Address information
* Required address fields
* Valid address data
* Validation of API responses

---

## Authentication & Token Handling

Authentication is an important part of this project.

The registration and login flows return authentication-related information that can be used by subsequent requests.

The Postman collection is designed to handle the authentication token and use it in requests that require an authenticated customer/session.

This allows the API tests to represent a realistic customer flow:

```text
Register
   ↓
Login
   ↓
Authentication / Token
   ↓
Product
   ↓
Add to Cart
   ↓
Update Cart
   ↓
Get Cart
   ↓
Remove Product
```

---

## Cart Session Testing

One of the important observations during API testing was that the shopping cart is **session-dependent**.

For example, adding:

```text
Product ID: 44
```

and then adding:

```text
Product ID: 40
```

within the same session results in both products being associated with the same cart.

This was tested to verify that cart operations work correctly across multiple requests and are not limited to a single product request.

---

## Important API Testing Observations

During the testing process, several application behaviors were identified and documented.

### Session Dependency

Cart operations depend on the current OpenCart session.

The same session must be maintained when testing sequential cart operations such as:

```text
Add Product
→ Add Another Product
→ Update Quantity
→ Get Cart
→ Remove Product
```

### Product ID

The `product_id` parameter determines which product is added to the cart.

For example:

```text
product_id=47
```

was used during testing with the OpenCart cart API.

### Request Headers

Some OpenCart requests require specific headers, such as:

```text
X-Requested-With: XMLHttpRequest
```

The required headers were identified by inspecting the browser network requests and then reproducing the requests in Postman.

### Browser Network Analysis

The API requests were also investigated through the browser's **Network / Fetch-XHR** tab.

This helped identify:

* Request URL
* HTTP method
* Query parameters
* Request body
* Headers
* Session-related information
* Response data

The identified requests were then recreated and tested in Postman.

---

## Example Cart Request

One of the observed OpenCart cart requests follows this structure:

```http
POST
route=checkout/cart.add
language=en-gb
```

Example request parameters:

```text
product_id=47
quantity=1
```

Depending on the product, additional parameters/options may also be required.

---

## Postman Collection

The Postman collection contains the API requests and test scenarios developed during the manual API testing phase.

The collection will be maintained in this repository so that the API tests can be executed independently from the browser UI tests.

---

## Automation Roadmap

### Completed

* [x] OpenCart API exploration
* [x] Browser Network analysis
* [x] API requests identified
* [x] API requests recreated in Postman
* [x] Registration scenarios
* [x] Login scenarios
* [x] Token/authentication handling
* [x] Product scenarios
* [x] Add-to-cart scenarios
* [x] Cart quantity update scenarios
* [x] Get cart scenarios
* [x] Remove-from-cart scenarios
* [x] Address scenarios

### Next Steps

* [ ] Add Postman collection to the repository
* [ ] Add Postman environment configuration
* [ ] Create Maven project
* [ ] Add RestAssured dependencies
* [ ] Convert Postman scenarios to RestAssured
* [ ] Add TestNG test classes
* [ ] Create reusable request specifications
* [ ] Add reusable authentication/token handling
* [ ] Add response validation and assertions
* [ ] Generate test reports
* [ ] Integrate tests with CI/CD

---

## Project Structure

The planned automation structure is:

```text
opencart-api-automation
│
├── postman
│   ├── OpenCart-API-Collection.json
│   └── OpenCart-API-Environment.json
│
├── src
│   ├── main
│   │   └── java
│   │       ├── base
│   │       ├── endpoints
│   │       ├── models
│   │       └── utilities
│   │
│   └── test
│       └── java
│           ├── tests
│           └── testData
│
├── pom.xml
└── README.md
```

---

## Related UI Automation Projects

This API project is part of a broader OpenCart testing portfolio that also includes UI automation.

* Selenium WebDriver automation
* Playwright automation
* API testing with Postman
* API automation with RestAssured

The goal is to demonstrate testing at different layers of the application rather than relying only on UI automation.

---

## Author

**Ayşe Cuşkun**

Software Test Engineer | Manual Testing | Test Automation | API Testing

