package CartApi;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class ProductDeleteTest {

	@Test
	public void deleteProduct() {
		
		
		Response getResponse =
	            given()
	            .when()
	                .get("http://127.0.0.1/opencart/upload/index.php"
	                    + "?route=account/login"
	                    + "&language=en-gb");

	        String sessionId = getResponse.getCookie("OCSESSID");

	        String body = getResponse.getBody().asString();

	        String loginToken =
	            body.split("login_token=")[1]
	                .split("\"")[0];

	        System.out.println("Session ID: " + sessionId);
	        System.out.println("Login Token: " + loginToken);


	        // 3. Login POST
	        Response loginResponse =
	            given()
	                .contentType("application/x-www-form-urlencoded")
	                .cookie("OCSESSID", sessionId)
	                .formParam("email", "apiTest123@gmail.com")
	                .formParam("password", "ApiTest123")
	              
	            .when()
	            .post("http://127.0.0.1/opencart/upload/index.php"
	            	    + "?route=account/login.login"
	            	    + "&language=en-gb"
	            	    + "&login_token=" + loginToken);
	        Assert.assertEquals(loginResponse.getStatusCode(), 200);

	        Assert.assertTrue(
	            loginResponse.getBody().asString()
	                .contains("route=account\\/account")
	        );

	        System.out.println("Login Response:");
	        System.out.println(loginResponse.getBody().asString());


	        Response cartlistResponse=
	       		 given()
	                .contentType("application/x-www-form-urlencoded")
	                .cookie("OCSESSID", sessionId)
	            .when()
	                .get("http://127.0.0.1/opencart/upload/index.php?route=common/cart.info&language=en-gb");
	        
	        Assert.assertEquals(cartlistResponse.getStatusCode(), 200);
	        System.out.println(cartlistResponse.getBody().asString());
	        Assert.assertTrue(
	               cartlistResponse.getBody().asString()
	                   .contains("item(s) - $")
	           );
	        
	        Response deletecart=
           		 given()
                    .contentType("application/x-www-form-urlencoded")
                    .cookie("OCSESSID", sessionId)
               	.queryParam("route", "checkout/cart.remove")
                   .queryParam("language", "en-gb")
                   
                   .queryParam("key", "77")
                   .when()
                    .post("http://127.0.0.1/opencart/upload/index.php");
            Assert.assertEquals(deletecart.getStatusCode(), 200);
            System.out.println(deletecart.getBody().asString());
            
            Assert.assertTrue(
                  deletecart.getBody().asString()
                       .contains("Success: You have removed an item from your shopping cart!")
               );
		
	}
	
}
