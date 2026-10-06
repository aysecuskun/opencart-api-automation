package LoginApi;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class LoginPageTest {

 	@Test
 	public void verifyLogin() {
 		
 	Response response=
 	given().
 	
 	when().
 	get("http://127.0.0.1/opencart/upload/index.php?route=account/login&language=en-gb");
 	Assert.assertTrue(response.getBody().asString().contains(""));
 	
 	System.out.println(response.getStatusCode());
 	System.out.println(response.getBody().asString().contains("Account Login"));
 	System.out.println(response.getBody().asString());
 	
 	
 	}
}
