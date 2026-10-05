package tests;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class PartialRegisterTest {

	@Test
	public void ExistingEmail() {
		// 1. Register sayfasına GET isteği gönder
        Response getResponse =
            given()
            .when()
                .get("http://127.0.0.1/opencart/upload/index.php?route=account/register&language=en-gb");

        // 2. GET response'undan session bilgisini al
        String sessionId = getResponse.getCookie("OCSESSID");

        // 3. GET response body'sinden register token'ı al
        String body = getResponse.getBody().asString();

        String registerToken =
                body.split("register_token=")[1]
                    .split("\"")[0];

        System.out.println("Session ID: " + sessionId);
        System.out.println("Register Token: " + registerToken);
        
        // 4. Register POST isteğini gönder
        Response postResponse =
            given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("OCSESSID", sessionId)
                .formParam("firstname", "ApiTest")
                .formParam("lastname", "ApiTest")
                .formParam("email", "apiTest123@gmail.com")
                .formParam("password", "ApiTest123")
                .formParam("newsletter", "1")
                .formParam("agree", "1")
            .when()
                .post("http://127.0.0.1/opencart/upload/index.php"
                    + "?route=account/register.register"
                    + "&language=en-gb"
                    + "&register_token=" + registerToken);


        // 5. Response'u kontrol et
        Assert.assertEquals(postResponse.getStatusCode(), 200);

        System.out.println(postResponse.getBody().asString());

        Assert.assertTrue(
            postResponse.getBody().asString().contains("Warning: E-Mail Address is already registered!")
        );
	}
	@Test(priority=2)
	public void requiredFiledsEmpty() {
		
		// 1. Register sayfasına GET isteği gönder
        Response getResponse =
            given()
            .when()
                .get("http://127.0.0.1/opencart/upload/index.php?route=account/register&language=en-gb");

        // 2. GET response'undan session bilgisini al
        String sessionId = getResponse.getCookie("OCSESSID");

        // 3. GET response body'sinden register token'ı al
        String body = getResponse.getBody().asString();

        String registerToken =
                body.split("register_token=")[1]
                    .split("\"")[0];

        System.out.println("Session ID: " + sessionId);
        System.out.println("Register Token: " + registerToken);
        
        // 4. Register POST isteğini gönder
        Response postResponse =
            given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("OCSESSID", sessionId)
                .formParam("firstname", "")
                .formParam("lastname", "")
                .formParam("email", "")
                .formParam("password", "")
                .formParam("newsletter", "0")
                .formParam("agree", "")
            .when()
                .post("http://127.0.0.1/opencart/upload/index.php"
                    + "?route=account/register.register"
                    + "&language=en-gb"
                    + "&register_token=" + registerToken);


        // 5. Response'u kontrol et
        Assert.assertEquals(postResponse.getStatusCode(), 200);

        System.out.println(postResponse.getBody().asString());

        Assert.assertTrue(
            postResponse.getBody().asString().contains("First Name must be between 1 and 32 characters!")
        );
        /*"firstname": "First Name must be between 1 and 32 characters!",
        "lastname": "Last Name must be between 1 and 32 characters!",
        "email": "E-Mail Address does not appear to be valid!",
        "password": "Password must be between 6 and 40 characters!",
        "warning": "Warning: You must agree to the Privacy Policy!"
*/
	}
	
	@Test(priority=3)
	public void InvalidEmail()
	{
		
		Response getResponse =
	            given()
	            .when()
	                .get("http://127.0.0.1/opencart/upload/index.php?route=account/register&language=en-gb");

	        String sessionId = getResponse.getCookie("OCSESSID");
	        String body = getResponse.getBody().asString();

	        String registerToken =
	                body.split("register_token=")[1]
	                    .split("\"")[0];

	        System.out.println("Session ID: " + sessionId);
	        System.out.println("Register Token: " + registerToken);
	        
	  
	        Response postResponse =
	            given()
	                .contentType("application/x-www-form-urlencoded")
	                .cookie("OCSESSID", sessionId)
	                .formParam("firstname", "ApiTest")
	                .formParam("lastname", "ApiTest")
	                .formParam("email", "api@com")
	                .formParam("password", "ApiTest123")
	                .formParam("newsletter", "1")
	                .formParam("newsletter", "1")
	                .formParam("agree", "1")
	            .when()
	                .post("http://127.0.0.1/opencart/upload/index.php"
	                    + "?route=account/register.register"
	                    + "&language=en-gb"
	                    + "&register_token=" + registerToken);
	        Assert.assertEquals(postResponse.getStatusCode(), 200);

	        System.out.println(postResponse.getBody().asString());

	        Assert.assertTrue(
	            postResponse.getBody().asString().contains("E-Mail Address does not appear to be valid!")
	        );

	}
	@Test(priority=4)
	public void passwordLenght() {
		
		Response getResponse =
	            given()
	            .when()
	                .get("http://127.0.0.1/opencart/upload/index.php?route=account/register&language=en-gb");

	        String sessionId = getResponse.getCookie("OCSESSID");
	        String body = getResponse.getBody().asString();

	        String registerToken =
	                body.split("register_token=")[1]
	                    .split("\"")[0];

	        System.out.println("Session ID: " + sessionId);
	        System.out.println("Register Token: " + registerToken);
	        
	  
	        Response postResponse =
	            given()
	                .contentType("application/x-www-form-urlencoded")
	                .cookie("OCSESSID", sessionId)
	                .formParam("firstname", "ApiTest")
	                .formParam("lastname", "ApiTest")
	                .formParam("email", "Denemeapi@gmail.com")
	                .formParam("password", "Api")
	                .formParam("newsletter", "1")
	                .formParam("newsletter", "1")
	                .formParam("agree", "1")
	            .when()
	                .post("http://127.0.0.1/opencart/upload/index.php"
	                    + "?route=account/register.register"
	                    + "&language=en-gb"
	                    + "&register_token=" + registerToken);
	        Assert.assertEquals(postResponse.getStatusCode(), 200);

	        System.out.println(postResponse.getBody().asString());

	        Assert.assertTrue(
	            postResponse.getBody().asString().contains("Password must be between 6 and 40 characters!")
	        );
	}
	
}
