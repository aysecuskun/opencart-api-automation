package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;
public class LoginPostTest {

	
	@Test(priority=1)
	public void LoginSucces() 

	{
		
		Response getResponse=
				given().
				when(). 
				get("http://127.0.0.1/opencart/upload/index.php?route=account/login&language=en-gb");
		
		String sessionId=getResponse.getCookie("OCSESSID");
		
		String body = getResponse.getBody().asString();

        String loginToken =
                body.split("login_token=")[1]
                    .split("\"")[0];

        Response postResponse= given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("OCSESSID", sessionId)
                .formParam("email", "apiTest123@gmail.com")
                .formParam("password", "ApiTest123")
                
            .when()
                .post("http://127.0.0.1/opencart/upload/index.php"
                    + "?route=account/login.login"
                    + "&language=en-gb"
                    + "&login_token=" + loginToken);
        
        
        Assert.assertEquals(postResponse.getStatusCode(), 200);

        System.out.println(postResponse.getBody().asString());

        Assert.assertTrue(
            postResponse.getBody().asString().contains("route=account\\/account")
        );
	}
	
	@Test(priority=2)
	public void LoginWrongEmail(){
		Response getResponse=
				given().
				when(). 
				get("http://127.0.0.1/opencart/upload/index.php?route=account/login&language=en-gb");
		
		String sessionId=getResponse.getCookie("OCSESSID");
		
		String body = getResponse.getBody().asString();

        String loginToken =
                body.split("login_token=")[1]
                    .split("\"")[0];

        Response postResponse= given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("OCSESSID", sessionId)
                .formParam("email", "apiTest12@gmail.com")
                .formParam("password", "ApiTest123")
                
            .when()
                .post("http://127.0.0.1/opencart/upload/index.php"
                    + "?route=account/login.login"
                    + "&language=en-gb"
                    + "&login_token=" + loginToken);
        
        
        Assert.assertEquals(postResponse.getStatusCode(), 200);

        System.out.println(postResponse.getBody().asString());

        Assert.assertTrue(
            postResponse.getBody().asString().contains("Warning: No match for E-Mail Address and")
        );
		
	}
	@Test(priority=3)
	public void LoginWrongPassword(){
		Response getResponse=
				given().
				when(). 
				get("http://127.0.0.1/opencart/upload/index.php?route=account/login&language=en-gb");
		
		String sessionId=getResponse.getCookie("OCSESSID");
		
		String body = getResponse.getBody().asString();

        String loginToken =
                body.split("login_token=")[1]
                    .split("\"")[0];

        Response postResponse= given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("OCSESSID", sessionId)
                .formParam("email", "apiTest123@gmail.com")
                .formParam("password", "Test123")
                
            .when()
                .post("http://127.0.0.1/opencart/upload/index.php"
                    + "?route=account/login.login"
                    + "&language=en-gb"
                    + "&login_token=" + loginToken);
        
        
        Assert.assertEquals(postResponse.getStatusCode(), 200);

        System.out.println(postResponse.getBody().asString());

        Assert.assertTrue(
            postResponse.getBody().asString().contains("Warning: No match for E-Mail Address and")
        );
		
	}
	@Test(priority=4)
	public void LoginBlankEmail(){
		
		Response getResponse=
				given().
				when(). 
				get("http://127.0.0.1/opencart/upload/index.php?route=account/login&language=en-gb");
		
		String sessionId=getResponse.getCookie("OCSESSID");
		
		String body = getResponse.getBody().asString();

        String loginToken =
                body.split("login_token=")[1]
                    .split("\"")[0];

        Response postResponse= given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("OCSESSID", sessionId)
                .formParam("email", "")
                .formParam("password", "ApiTest123")
                
            .when()
                .post("http://127.0.0.1/opencart/upload/index.php"
                    + "?route=account/login.login"
                    + "&language=en-gb"
                    + "&login_token=" + loginToken);
        
        
        Assert.assertEquals(postResponse.getStatusCode(), 200);

        System.out.println(postResponse.getBody().asString());

        Assert.assertTrue(
            postResponse.getBody().asString().contains("Warning: Your account has exceeded allowed number of login attempts. Please try again in 1 hour.")
        );
	}
	@Test(priority=5)
	public void LoginBlankPassword(){
		Response getResponse=
				given().
				when(). 
				get("http://127.0.0.1/opencart/upload/index.php?route=account/login&language=en-gb");
		
		String sessionId=getResponse.getCookie("OCSESSID");
		
		String body = getResponse.getBody().asString();

        String loginToken =
                body.split("login_token=")[1]
                    .split("\"")[0];

        Response postResponse= given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("OCSESSID", sessionId)
                .formParam("email", "apiTest123@gmail.com")
                .formParam("password", "")
                
            .when()
                .post("http://127.0.0.1/opencart/upload/index.php"
                    + "?route=account/login.login"
                    + "&language=en-gb"
                    + "&login_token=" + loginToken);
        
        
        Assert.assertEquals(postResponse.getStatusCode(), 200);

        System.out.println(postResponse.getBody().asString());

        Assert.assertTrue(
            postResponse.getBody().asString().contains("Warning: No match for E-Mail Address and")
        );
		
	}
}
