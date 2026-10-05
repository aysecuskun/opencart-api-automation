# OpenCart API Automation

API testing and automation project for **OpenCart** using **Postman, RestAssured, Java and TestNG**.

This project was created to practice API testing on an e-commerce application and to automate important user, product and shopping cart flows at API level.

The project started with **API exploration and manual testing in Postman**. The validated scenarios are then being converted into automated tests using **RestAssured and Java**.

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

The project covers different API scenarios including:

* User registration
* User login
* Authentication and token handling
* Product search and product information
* Shopping cart operations
* Address-related scenarios
* Request and response validation

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

After validating the scenarios in Postman, the automation phase was started with **RestAssured**.

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

Current scenarios include:

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

Current scenarios include:

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

The session cookie is also maintained between the GET and POST requests.

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

For example:

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

During testing, different product IDs were added within the same session to verify that multiple products could belong to the same cart.

Example:

```text
Product ID: 44
Product ID: 40
```

The cart API uses the OpenCart session to maintain the cart state.

---

## Important API Testing Observations

### Session Dependency

Cart operations depend on the current OpenCart session.

The session must be maintained when testing multiple sequential requests.

### Dynamic Tokens

Registration and login requests use dynamically generated tokens.

These tokens are retrieved from the preceding GET request instead of being hard-coded.

### Product ID

The `product_id` parameter determines which product is added to the cart.

Example:

```text
product_id=47
```

### Request Headers

Some OpenCart requests require specific headers.

For example:

```text
X-Requested-With: XMLHttpRequest
```

These requirements were identified by inspecting browser Network / Fetch-XHR requests.

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

This is an important aspect of API testing because a successful HTTP request does not always mean that the business operation was successful.

---

## Current RestAssured Automation

The RestAssured automation currently includes:

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
```

The tests are implemented using **RestAssured + TestNG**.

The project currently uses `Response` objects to store API responses and then validates status codes and response bodies through TestNG assertions.

Example approach:

```java
Response response =
    given()
    .when()
        .get(url);

Assert.assertEquals(response.getStatusCode(), 200);
Assert.assertTrue(response.getBody().asString().contains("expected content"));
```

---

## Postman Collection

The `postman/` directory contains the Postman collection created during the API exploration and manual testing phase.

The Postman collection is used as the starting point for the RestAssured automation.

This creates a clear progression:

```text
Postman
   ↓
Validated API Scenarios
   ↓
RestAssured
   ↓
Automated API Tests
```

---

## Test Data

The `test-data/` directory contains the API test scenario documentation used during the project.

The test scenarios cover positive, negative and validation cases for the OpenCart API.

---

## Project Structure

Current project structure:

```text
opencart-api-automation
│
├── postman
│   └── OpenCart API Automation.postman_collection.json
│
├── test-data
│   ├── API_Test_Scenarios..xlsx
│   └── .gitkeep
│
├── screenshots
│   ├── Loginpostsuccess.png
│   └── registerapisuccess.png
│
├── src
│   └── test
│       └── java
│           └── tests
│               ├── HomepageTest.java
│               ├── RegisterPageTest.java
│               ├── RegisterPostTest.java
│               ├── PartialRegisterTest.java
│               ├── LoginPageTest.java
│               └── LoginPostTest.java
│
├── pom.xml
├── .gitignore
└── README.md
```

---

## Automation Roadmap

### Completed

* [x] OpenCart API exploration
* [x] Browser Network / Fetch-XHR analysis
* [x] API request identification
* [x] API requests recreated in Postman
* [x] Registration scenarios in Postman
* [x] Login scenarios in Postman
* [x] Authentication and token investigation
* [x] Product API investigation
* [x] Shopping cart API investigation
* [x] Address API investigation
* [x] Maven project setup
* [x] RestAssured dependency integration
* [x] TestNG integration
* [x] Homepage GET automation
* [x] Registration GET automation
* [x] Registration POST automation
* [x] Registration validation scenarios
* [x] Login GET automation
* [x] Login POST automation
* [x] Login negative scenarios
* [x] Session and dynamic token handling
* [x] Response-based assertions

### Next Steps

* [ ] Automate product search scenarios with RestAssured
* [ ] Automate product detail scenarios
* [ ] Automate add-to-cart scenarios
* [ ] Automate cart update scenarios
* [ ] Automate get-cart scenarios
* [ ] Automate remove-from-cart scenarios
* [ ] Automate address scenarios
* [ ] Improve reusable request/session handling
* [ ] Improve test data management
* [ ] Add reusable authentication handling
* [ ] Improve project structure
* [ ] Add API test reporting
* [ ] Integrate the project with CI/CD

---

## Related UI Automation Projects

This API automation project is part of a broader OpenCart testing portfolio.

Other projects include:

* Selenium WebDriver automation
* Playwright automation
* API testing with Postman
* API automation with RestAssured

The goal is to demonstrate testing at different layers of an application:

```text
UI Testing
├── Selenium
└── Playwright

API Testing
├── Postman
└── RestAssured
```

---

## Author

**Ayşe Cuşkun**

Software Test Engineer | Manual Testing | Test Automation | API Testing

---

# 🇹🇷 Türkçe

## Proje Hakkında

Bu proje, **OpenCart** e-ticaret uygulamasının API'lerini test etmek ve otomasyonunu geliştirmek amacıyla oluşturulmuştur.

Proje başlangıçta **Postman ile API keşfi ve manuel API testleri** yapılarak geliştirilmiş, doğrulanan senaryolar daha sonra **Java, RestAssured ve TestNG** kullanılarak otomasyona aktarılmaya başlanmıştır.

Projenin temel yaklaşımı:

```text
Browser Network Analizi
        ↓
API Request'lerin Belirlenmesi
        ↓
Postman ile Test
        ↓
Test Senaryolarının Doğrulanması
        ↓
RestAssured Otomasyonu
        ↓
TestNG Assertion'ları
```

---

## Kullanılan Teknolojiler

* **Java 17** – Otomasyon dili
* **RestAssured 6.0.1** – API otomasyonu
* **TestNG 7.12.0** – Test çalıştırma ve assertion
* **Maven** – Dependency ve proje yönetimi
* **Postman** – API keşfi ve manuel API testleri
* **Git & GitHub** – Versiyon kontrolü
* **OpenCart** – Test edilen uygulama
* **Eclipse** – Geliştirme ortamı

---

## API Test Yaklaşımı

API istekleri öncelikle tarayıcının **Network / Fetch-XHR** bölümünden incelenmiştir.

Bu analiz sırasında:

* Request URL
* HTTP method
* Query parametreleri
* Request body
* Headers
* Cookie / session bilgileri
* Dinamik token'lar
* Response verileri

incelenmiştir.

Daha sonra bu istekler Postman'de yeniden oluşturularak test edilmiştir.

Postman'de doğrulanan senaryoların RestAssured ve Java ile otomasyonu gerçekleştirilmektedir.

---

## API Test Senaryoları

### 1. Homepage

**GET**

OpenCart ana sayfa endpoint'i RestAssured kullanılarak test edilmektedir.

Kontroller:

* HTTP status code
* Response body
* Beklenen sayfa içeriği

---

### 2. Kullanıcı Kaydı

**GET + POST**

Kullanıcı kayıt işlemi öncesinde registration sayfasına GET isteği gönderilmektedir.

Bu istek sonucunda session bilgisi ve dinamik registration token alınmaktadır.

Akış:

```text
GET Registration Page
        ↓
OCSESSID alınır
        ↓
register_token alınır
        ↓
POST Registration
        ↓
Response doğrulanır
```

Mevcut senaryolar:

* Başarılı kayıt
* Zorunlu alan validasyonları
* Hatalı kayıt verileri
* Registration response kontrolü
* Session ve token yönetimi

---

### 3. Kullanıcı Girişi

**GET + POST**

Login işlemi öncesinde login sayfasına GET isteği gönderilmektedir.

Akış:

```text
GET Login Page
        ↓
OCSESSID alınır
        ↓
login_token alınır
        ↓
POST Login
        ↓
Response doğrulanır
```

Mevcut senaryolar:

* Başarılı login
* Hatalı email
* Hatalı password
* Boş email
* Boş password
* Login response kontrolü
* Session ve token yönetimi

---

## Authentication ve Token Yönetimi

OpenCart login ve registration işlemlerinde dinamik token'lar kullanmaktadır.

Örneğin:

```text
login_token
register_token
```

Bu değerler GET isteğinin response body'sinden alınarak POST isteğinde kullanılmaktadır.

Aynı zamanda GET ve POST arasında session cookie korunmaktadır.

Genel akış:

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

Bu yöntem sayesinde otomasyon, uygulamanın gerçek request akışını taklit etmektedir.

---

## Sepet Testleri

OpenCart sepet işlemlerinin **session-dependent** olduğu gözlemlenmiştir.

Yani birden fazla sepet işlemini test ederken aynı session'ın korunması gerekmektedir.

Örneğin:

```text
Ürün Ekle
      ↓
Başka Ürün Ekle
      ↓
Miktarı Güncelle
      ↓
Sepeti Getir
      ↓
Ürünü Sil
```

Aynı session içerisinde farklı ürünlerin sepete eklenmesi test edilmiştir.

Örnek:

```text
Product ID: 44
Product ID: 40
```

Bu sayede birden fazla ürünün aynı shopping cart session'ı içerisinde yönetilebildiği doğrulanmıştır.

---

## API Testlerinde Önemli Gözlemler

### Session Dependency

Sepet işlemleri mevcut OpenCart session'ına bağlıdır.

Bu nedenle ardışık API isteklerinde session bilgisinin korunması gerekir.

### Dynamic Token

Registration ve login işlemlerinde dinamik token'lar kullanılmaktadır.

Token'lar sabit olarak yazılmak yerine önceki GET response'undan alınmaktadır.

### Product ID

Sepete hangi ürünün ekleneceğini `product_id` belirlemektedir.

Örnek:

```text
product_id=47
```

### Request Headers

Bazı OpenCart API isteklerinde özel header'lar gerekmektedir.

Örneğin:

```text
X-Requested-With: XMLHttpRequest
```

Bu gereksinimler tarayıcı Network / Fetch-XHR istekleri incelenerek belirlenmiştir.

### HTTP Status Code ve Business Result Farkı

API testleri sırasında önemli bir nokta gözlemlenmiştir:

Bir işlem başarısız olsa bile HTTP status code `200` olabilir.

Örneğin hatalı login isteği:

```text
200 OK
```

dönerken response body içerisinde:

```text
Warning: No match for E-Mail Address and/or Password.
```

mesajı bulunabilmektedir.

Bu nedenle testlerde yalnızca HTTP status code değil, **response body içerisindeki application-level sonuçlar da** kontrol edilmektedir.

---

## Mevcut RestAssured Otomasyonu

RestAssured tarafında şu anda:

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
```

senaryoları otomatikleştirilmiştir.

Testler **RestAssured + TestNG** kullanılarak yazılmıştır.

API response'ları `Response` değişkenlerinde tutularak daha sonra TestNG assertion'ları ile kontrol edilmektedir.

Örneğin:

```java
Response response =
    given()
    .when()
        .get(url);

Assert.assertEquals(response.getStatusCode(), 200);
Assert.assertTrue(response.getBody().asString().contains("expected content"));
```

---

## Postman Collection

`postman/` klasörü API keşfi ve manuel test aşamasında oluşturulan Postman collection'ını içermektedir.

Postman collection, RestAssured otomasyonunun başlangıç noktası olarak kullanılmaktadır.

Projenin yaklaşımı:

```text
Postman
   ↓
Doğrulanmış API Senaryoları
   ↓
RestAssured
   ↓
Otomatik API Testleri
```

---

## Test Data

`test-data/` klasörü API test senaryolarının dokümantasyonunu içermektedir.

Senaryolarda:

* Positive testler
* Negative testler
* Validation testleri

yer almaktadır.

---

## Proje Yapısı

Mevcut proje yapısı:

```text
opencart-api-automation
│
├── postman
│   └── OpenCart API Automation.postman_collection.json
│
├── test-data
│   ├── API_Test_Scenarios..xlsx
│   └── .gitkeep
│
├── screenshots
│   ├── Loginpostsuccess.png
│   └── registerapisuccess.png
│
├── src
│   └── test
│       └── java
│           └── tests
│               ├── HomepageTest.java
│               ├── RegisterPageTest.java
│               ├── RegisterPostTest.java
│               ├── PartialRegisterTest.java
│               ├── LoginPageTest.java
│               └── LoginPostTest.java
│
├── pom.xml
├── .gitignore
└── README.md
```

---

## Otomasyon Yol Haritası

### Tamamlananlar

* [x] OpenCart API keşfi
* [x] Browser Network / Fetch-XHR analizi
* [x] API request'lerinin belirlenmesi
* [x] API request'lerinin Postman'de oluşturulması
* [x] Registration senaryoları
* [x] Login senaryoları
* [x] Authentication ve token araştırması
* [x] Product API araştırması
* [x] Shopping cart API araştırması
* [x] Address API araştırması
* [x] Maven projesinin oluşturulması
* [x] RestAssured dependency entegrasyonu
* [x] TestNG entegrasyonu
* [x] Homepage GET otomasyonu
* [x] Registration GET otomasyonu
* [x] Registration POST otomasyonu
* [x] Registration validation senaryoları
* [x] Login GET otomasyonu
* [x] Login POST otomasyonu
* [x] Login negative senaryoları
* [x] Session ve dinamik token yönetimi
* [x] Response-based assertion'lar

### Sıradaki Adımlar

* [ ] Product search senaryolarını RestAssured ile otomatikleştirmek
* [ ] Product detail senaryolarını otomatikleştirmek
* [ ] Add-to-cart senaryolarını otomatikleştirmek
* [ ] Cart update senaryolarını otomatikleştirmek
* [ ] Get-cart senaryolarını otomatikleştirmek
* [ ] Remove-from-cart senaryolarını otomatikleştirmek
* [ ] Address senaryolarını otomatikleştirmek
* [ ] Reusable request/session yapısını geliştirmek
* [ ] Test data yönetimini geliştirmek
* [ ] Reusable authentication handling eklemek
* [ ] Proje yapısını geliştirmek
* [ ] API test reporting eklemek
* [ ] CI/CD entegrasyonu

---

## İlgili UI Automation Projeleri

Bu API otomasyon projesi, daha geniş OpenCart test otomasyon portföyünün bir parçasıdır.

Diğer projeler:

* Selenium WebDriver automation
* Playwright automation
* Postman API testing
* RestAssured API automation

Amaç, uygulamanın farklı test katmanlarında deneyim göstermektir:

```text
UI Testing
├── Selenium
└── Playwright

API Testing
├── Postman
└── RestAssured
```

---

## Yazar

**Ayşe Cuşkun**

Software Test Engineer | Manual Testing | Test Automation | API Testing
