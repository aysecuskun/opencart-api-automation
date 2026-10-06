package RegisterApi;

import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;
import io.restassured.response.Response;

public class RegisterPostTest {
	
	@Test
	public void RegisterSuccess() {
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
            postResponse.getBody().asString().contains("account\\/success")
        );
	}
}