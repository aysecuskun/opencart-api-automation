package AddressApi;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class EmptyFieldsTest {

	@Test
	public void emptyAdreesTest() {
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
	       
	        /*Sepeti listeleme işlemi*/
	        
	        Response cartlistResponse=
		       		 given()
		                .contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		            .when()
		                .get("http://127.0.0.1/opencart/upload/index.php?route=common/cart.info&language=en-gb");
		        
		        Assert.assertEquals(cartlistResponse.getStatusCode(), 200);
		        System.out.println(cartlistResponse.getStatusCode());
		        System.out.println(cartlistResponse.getBody().asString());
		        Assert.assertTrue(
		               cartlistResponse.getBody().asString()
		                   .contains("item(s) - $")
		           );
	        /* Checkout butonuna istek gönderme ve ödeme sayfasına geçiş işlemi*/
		        Response checkoutResponse=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		        		.when()
		        		.get("http://127.0.0.1/opencart/upload/index.php?route=checkout/checkout&language=en-gb");
		        
		        Assert.assertEquals(checkoutResponse.getStatusCode(), 200);
		        System.out.println(checkoutResponse.getStatusCode());
		        
		        Assert.assertTrue(
		        		checkoutResponse.getBody().asString()
			                   .contains("Shipping Address")
			           );
		        System.out.println("...................................................");
		        /* Shipping Adres alanlarını boş bıraktıktan sonra continue butonuna istek atılması*/
		        
		        Response emptyResponse=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		                .formParam("firstname", " ")
		                .formParam("lastname", " ")
		                .formParam("company", " ")
		                .formParam("address_1", " ")
		                .formParam("address_2", " ")
		                .formParam("city", " ")
		                .formParam("postcode", " ")
		                .formParam("country_id", "222")
		                .formParam("zone_id", " ")
		                .when()
		                .post("http://127.0.0.1/opencart/upload/index.php?route=checkout/shipping_address.save&language=en-gb");
		        
		        Assert.assertTrue(emptyResponse.getBody().asString().
		        		contains("Address 1 must be between 3 and 128 characters!")
		        		);
		        
		        System.out.println(emptyResponse.getStatusCode());
	}
}
