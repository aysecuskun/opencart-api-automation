package RegisterApi;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class RegisterPageTest {

	@Test
	public void verifyRegister() {
		
	Response response=
	given().
	
	when().
	get("http://127.0.0.1/opencart/upload/index.php?route=account/register&language=en-gb");
	Assert.assertTrue(response.getBody().asString().contains("Register Account"));
	
	System.out.println(response.getStatusCode());
	System.out.println(response.getBody().asString().contains("Register Account"));
	System.out.println(response.getCookies());
	System.out.println(response.getBody().asString());
	
	
	
	
}
}
