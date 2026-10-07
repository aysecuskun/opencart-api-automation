# OpenCart API Automation

API testing and automation project for **OpenCart** using **Postman, RestAssured, Java and TestNG**.

This project was created to practice API testing on an e-commerce application and to automate important user, product, shopping cart and checkout flows at API level.

The project started with **API exploration and manual testing in Postman**. The validated scenarios were then converted into automated tests using **RestAssured and Java**.

---

# 🇬🇧 English

## Project Overview

This project focuses on API testing and automation of an OpenCart e-commerce application.

The API testing process follows this approach:

```text
Browser Network Analysis
        ↓
API Request Identification
        ↓
Postman Testing
        ↓
Test Scenario Validation
        ↓
RestAssured Automation
        ↓
TestNG Assertions
```

The project covers:

* User registration
* User login
* Authentication and token handling
* Product search and product information
* Shopping cart operations
* Address management
* Shipping method selection
* Payment method selection
* Checkout flow
* Order creation
* Request and response validation
* Session-dependent API flows
* Positive, negative and validation scenarios

---

## Technologies & Tools

* **Java 17** – Automation language
* **RestAssured 6.0.1** – API automation
* **TestNG 7.12.0** – Test execution and assertions
* **Maven** – Dependency and project management
* **Postman** – API exploration and manual API testing
* **Git & GitHub** – Version control
* **OpenCart** – Application Under Test
* **Eclipse** – Development environment

---

## API Testing Approach

The API requests were first investigated through the browser's **Network / Fetch-XHR** section.

This helped identify:

* Request URL
* HTTP method
* Query parameters
* Request body
* Headers
* Cookies / session information
* Dynamic tokens
* Response data

The identified requests were then recreated and validated in **Postman**.

After validating the scenarios in Postman, the automation phase was implemented using **RestAssured, Java and TestNG**.

This approach helped reproduce the actual request flow used by the OpenCart application instead of treating the API endpoints as isolated requests.

---

## API Test Scenarios

### 1. Homepage

**GET**

The homepage endpoint is validated using RestAssured.

Checks include:

* HTTP status code
* Response body
* Expected page content

---

### 2. User Registration

**GET + POST**

The registration flow requires retrieving the registration page first because the application generates session-related information and a dynamic registration token.

The automated flow is:

```text
GET Registration Page
        ↓
Get OCSESSID
        ↓
Extract register_token
        ↓
POST Registration Request
        ↓
Validate Response
```

Scenarios include:

* Successful registration
* Required field validation
* Invalid registration data
* Registration response validation
* Session and token handling

---

### 3. User Login

**GET + POST**

The login flow also requires retrieving the login page before sending the POST request.

The automated flow is:

```text
GET Login Page
        ↓
Get OCSESSID
        ↓
Extract login_token
        ↓
POST Login Request
        ↓
Validate Response
```

Scenarios include:

* Successful login
* Invalid email
* Invalid password
* Empty email
* Empty password
* Login response validation
* Session and token handling

Successful login responses are validated by checking the redirect to the customer account page.

---

## Authentication & Token Handling

Authentication and session handling are important parts of this project.

OpenCart generates dynamic tokens such as:

```text
login_token
register_token
```

The automation retrieves these values from the corresponding GET response and uses them in the following POST request.

The session cookie is also maintained between requests.

For example:

```text
GET
  ↓
OCSESSID
  +
login_token / register_token
  ↓
POST
  ↓
Response validation
```

This approach allows the automated tests to reproduce the actual request flow used by the OpenCart application.

---

## Shopping Cart Testing

The shopping cart is **session-dependent**.

This means that the same session must be maintained when performing sequential cart operations.

The tested flow includes:

```text
Add Product
      ↓
Add Another Product
      ↓
Update Quantity
      ↓
Get Cart
      ↓
Remove Product
```

Different product IDs were used during testing to verify that multiple products could be managed within the same shopping cart session.

Example:

```text
Product ID: 44
Product ID: 40
```

The cart API uses the OpenCart session to maintain the cart state.

---

## Address API Testing

Address-related checkout operations were investigated through browser Network / Fetch-XHR requests and automated with RestAssured.

The tested flow includes:

```text
Login
   ↓
Checkout
   ↓
Select Shipping Address
   ↓
Validate Response
```

The address selection response is validated at application level.

Example:

```json
{
  "success": "Success: You have changed shipping address!"
}
```

The test maintains the same `OCSESSID` throughout the flow to preserve the authenticated session.

---

## Shipping Method Testing

The shipping method flow was automated using the same authenticated session.

The flow includes:

```text
Get Shipping Methods
        ↓
Validate Available Shipping Method
        ↓
Save Shipping Method
        ↓
Validate Response
```

The tested shipping option includes:

```text
Flat Shipping Rate
```

The save operation returns an application-level success response:

```json
{
  "success": "Success: You have changed shipping method!"
}
```

The shipping cost was also validated as part of the checkout flow.

---

## Payment Method Testing

The payment method flow was automated after the shipping method was selected.

The flow includes:

```text
Get Payment Methods
        ↓
Validate Available Payment Method
        ↓
Save Payment Method
        ↓
Validate Response
```

The tested payment method was:

```text
Cash On Delivery
```

The save operation returns:

```json
{
  "success": "Success: You have changed payment method!"
}
```

---

## End-to-End Checkout & Order Creation

One of the main completed scenarios is the end-to-end checkout flow.

The automated flow is:

```text
Login
   ↓
Cart
   ↓
Checkout
   ↓
Shipping Address
   ↓
Shipping Method
   ↓
Payment Method
   ↓
Confirm Order
   ↓
Order Successfully Created
```

The test maintains the same authenticated session throughout the checkout process.

The final response is validated using both HTTP status and application-level response content.

The successful order response contains:

```text
Your order has been placed!

Your order has been successfully processed!
```

The checkout flow was also validated with the expected order total.

Example:

```text
Product: MacBook Pro
Product Total: $2,000.00
Shipping: $5.00
Order Total: $2,005.00
```

This confirms that the API automation can execute a complete e-commerce checkout flow and verify the resulting business outcome.

---

## Important API Testing Observations

### Session Dependency

Several OpenCart operations are session-dependent.

For example, login, cart and checkout operations require maintaining the same session between sequential requests.

Therefore, the automation explicitly manages the `OCSESSID` cookie when required.

---

### Dynamic Tokens

Registration and login operations use dynamically generated tokens.

These tokens are retrieved from the preceding GET response instead of being hard-coded.

Examples:

```text
login_token
register_token
```

---

### HTTP Status vs Application Result

During testing, it was observed that an unsuccessful business operation can still return HTTP `200`.

For example, an invalid login can return:

```text
200 OK
```

while the response body contains:

```text
Warning: No match for E-Mail Address and/or Password.
```

Therefore, the automated tests validate both:

* HTTP status code
* Application-level response content

This is an important API testing principle because a successful HTTP request does not necessarily mean that the business operation was successful.

---

### Request Headers

Some OpenCart requests require specific headers.

For example:

```text
X-Requested-With: XMLHttpRequest
```

These requirements were identified by inspecting browser Network / Fetch-XHR requests.

---

### OpenCart Uses POST for Some Update and Remove Operations

An important API design observation was identified during testing.

Although conventional RESTful API design commonly uses:

```text
PUT / PATCH → Update
DELETE      → Delete
```

some OpenCart operations use:

```text
POST → Update
POST → Remove
```

For example, shopping cart update and remove operations were implemented using POST requests.

This was treated as an **application-specific API design choice**, not as an automation error.

This observation demonstrates the importance of testing the actual API contract of the application rather than assuming that every endpoint follows conventional REST semantics.

---

### Response-Based Testing

The project uses `Response` objects to inspect API responses before performing assertions.

Example:

```java
Response response =
    given()
    .when()
        .get(url);

Assert.assertEquals(response.getStatusCode(), 200);

Assert.assertTrue(
    response.getBody()
        .asString()
        .contains("expected content")
);
```

This approach allows the tests to validate both technical response information and business-level results.

---

## Completed RestAssured Automation

The RestAssured automation covers the following areas:

```text
Homepage
   ↓
Registration
   ├── Successful registration
   └── Validation scenarios
   ↓
Login
   ├── Successful login
   ├── Invalid email
   ├── Invalid password
   └── Blank credentials
   ↓
Shopping Cart
   ├── Add product
   ├── Get cart
   ├── Update cart
   └── Remove product
   ↓
Address
   └── Select shipping address
   ↓
Checkout
   ├── Shipping method
   ├── Payment method
   └── Order creation
```

The tests are implemen
