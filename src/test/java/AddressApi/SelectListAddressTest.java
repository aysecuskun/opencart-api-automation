package AddressApi;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class SelectListAddressTest {

	@Test
	public void AddressList() {
		
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
	       
	        /* Checkout butonuna istek gönderme ve ödeme sayfasına geçiş işlemi*/
		        Response checkoutResponse=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		        		.when()
		        		.get("http://127.0.0.1/opencart/upload/index.php?route=checkout/checkout&language=en-gb");
		        
		        Assert.assertEquals(checkoutResponse.getStatusCode(), 200);
		        System.out.println(checkoutResponse.getStatusCode());
		        System.out.println("--Please Select--"+checkoutResponse.getBody().asString());
		        Assert.assertTrue(
		        		checkoutResponse.getBody().asString()
			                   .contains("Please Select")
			           );
		        
		        Response selectaddressResponse=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		                
		        		.when()
		        		.get("http://127.0.0.1/opencart/upload/index.php?route=checkout/shipping_address.address&language=en-gb&address_id=28");
		        Assert.assertTrue(selectaddressResponse.getBody().asString().
		        		contains("Success: You have changed shipping address!")
		        		);
		        
		        System.out.println("Select:"+selectaddressResponse.getBody().asString());
		        
		        Response routecheckout=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		        .when() 
		        .get("http://127.0.0.1/opencart/upload/index.php?route=checkout/confirm.confirm&language=en-gb");
		        System.out.println("Select after checkoutpage :"+routecheckout.getBody().asString());
		        
		      
	}
}
