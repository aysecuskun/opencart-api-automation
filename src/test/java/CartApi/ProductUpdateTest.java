package CartApi;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class ProductUpdateTest {

	@Test
    public void UpdateProduct() {
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
        
        Response updatecart=
          		 given()
                   .contentType("application/x-www-form-urlencoded")
                   .cookie("OCSESSID", sessionId)
                   .formParam("quantity", "7")
                   .formParam("key", "77")
               .when()
                   .post("http://127.0.0.1/opencart/upload/index.php"
                       + "?route=checkout/cart.edit"
                       + "&language=en-gb");
        Assert.assertEquals(updatecart.getStatusCode(), 200);
           System.out.println(updatecart.getBody().asString());
           Assert.assertTrue(
                  updatecart.getBody().asString()
                      .contains("Success: You have modified your shopping cart!")
              );
           
          
        
	}
}
