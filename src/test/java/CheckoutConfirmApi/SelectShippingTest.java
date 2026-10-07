package CheckoutConfirmApi;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class SelectShippingTest {

	@Test
	public void ShippingSelect() {
		
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
	        System.out.println("Initial OCSESSID: " + sessionId);
	        System.out.println("Login OCSESSID: " + loginResponse.getCookie("OCSESSID"));
	        System.out.println("Login Cookies: " + loginResponse.getCookies());
	        System.out.println("Login Response:");
	        System.out.println(loginResponse.getBody().asString());
	       
	        /* Checkout ödeme sayfasına geçiş işlemi*/
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
		        
		        Response selectaddressResponse=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		        		.when()
		        		.get("http://127.0.0.1/opencart/upload/index.php?route=checkout/shipping_address.address&language=en-gb&address_id=26");
		        Assert.assertTrue(selectaddressResponse.getBody().asString().
		        		contains("Success: You have changed shipping address!")
		        		);
		        
		        System.out.println(selectaddressResponse.getStatusCode());
		        /*seçim sonrası checkouta yönlenme*/
		        Response routecheckout=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		        .when() 
		        .get("http://127.0.0.1/opencart/upload/index.php?route=checkout/confirm.confirm&language=en-gb");
		        Assert.assertTrue(routecheckout.getBody().asString().
		        		contains("http://localhost/opencart/upload/index.php?route=product/product&amp")
		        		);
		        System.out.println("body :"+routecheckout.getBody().asString());
		        
		      /* Shipping adress seçimi */
		        	  Response selectshippingResponse=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		        		.when()
		        		.get("http://127.0.0.1/opencart/upload/index.php?route=checkout/shipping_method.quote&language=en-gb");
		        Assert.assertTrue(selectshippingResponse.getBody().asString().
		        		contains("Flat Shipping Rate")
		        		);
		        
		        System.out.println(selectshippingResponse.getStatusCode());
		        
		        

		        System.out.println("Flat shipping:"+selectshippingResponse.getBody().asString());
		        /* Shipping kayıt işlemi */
		        
		        Response saveshippingResponse=
		        		given()
		        		.contentType("application/x-www-form-urlencoded")
		                .cookie("OCSESSID", sessionId)
		                .formParam("shipping_method", "flat.flat")
		        		.when()
		        		.post("http://127.0.0.1/opencart/upload/index.php?route=checkout/shipping_method.save&language=en-gb");
		        Assert.assertTrue(saveshippingResponse.getBody().asString().
		        		contains("Success: You have changed shipping method!")
		        		);
		        
		        System.out.println(saveshippingResponse.getStatusCode());
		        
		        System.out.println(saveshippingResponse.getBody().asString());
		        
		        /*confirm get shipping */
		        given() 
		        .when() 
		        .get("http://127.0.0.1/opencart/upload/index.php?route=checkout/confirm.confirm&language=en-gb") 
		        .then() 
		        .statusCode(200); 
		   
		        
		        /*  Payment seçimi */
		  	  Response selectpaymentResponse=
		  		given()
		  		.contentType("application/x-www-form-urlencoded")
		        .cookie("OCSESSID", sessionId)
		  		.when()
		  		.get("http://127.0.0.1/opencart/upload/index.php?route=checkout/payment_method.getMethods&language=en-gb");
		  	  
		  	  
		  Assert.assertTrue(selectpaymentResponse.getBody().asString().
		  		contains("payment_methods")
		  		);

		  System.out.println("Select payment body:" +selectpaymentResponse.getBody().asString());


		  System.out.println("------------------------------------------");
		  /* Payment save */

		  Response savepaymentResponse=
		  		given()
		  		.contentType("application/x-www-form-urlencoded")
		        .cookie("OCSESSID", sessionId)
		        .formParam("payment_method", "cod.cod")
		  		.when()
		  		.post("http://127.0.0.1/opencart/upload/index.php?route=checkout/payment_method.save&language=en-gb");


		  Assert.assertTrue(savepaymentResponse.getBody().asString().
		  		contains("Success: You have changed payment method!")
		  		);

		  System.out.println(savepaymentResponse.getStatusCode());

		  System.out.println(savepaymentResponse.getBody().asString());
		  /*confirm get */
		  given() 
		  .when() 
		  .get("http://127.0.0.1/opencart/upload/index.php?route=checkout/confirm.confirm&language=en-gb") 
		  .then() 
		  .statusCode(200); 

		  
		  
		  Response confirmOrder=
		    		given()
		    		.contentType("application/x-www-form-urlencoded")
		            .cookie("OCSESSID", sessionId)
		            
		    		.when()
		    		.post("http://127.0.0.1/opencart/upload/index.php?route=checkout/success&language=en-gb");
		    
		    
		    Assert.assertTrue(confirmOrder.getBody().asString().
		    		contains("Your order has been placed!")
		    		);
		    
		    System.out.println(confirmOrder.getStatusCode());
		    
		    System.out.println(confirmOrder.getBody().asString());

	}
	
	
	
}
