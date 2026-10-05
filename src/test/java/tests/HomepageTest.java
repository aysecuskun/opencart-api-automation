package tests;

import org.testng.annotations.Test;

import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.containsString;

public class HomepageTest {
	
	@Test
    public void verifyHomepageStatusCode() {

	        given()
	        
	        .when()
	            .get("http://127.0.0.1/opencart/upload/index.php")
	        
	        .then()
	            .statusCode(200);
	    }
	
    @Test
    public void verifyHomepageContent() {

        given()

        .when()
            .get("http://127.0.0.1/opencart/upload/index.php")

        .then()
            .statusCode(200)
            .body(containsString("Your Store"));
    }

    @Test
    public void getHomepageResponse() {

        Response response =
                given()

                .when()
                    .get("http://127.0.0.1/opencart/upload/index.php");

        System.out.println("Status Code: " + response.getStatusCode());
        System.out.println("Response Body:");
        System.out.println(response.getBody().asString());
    }
    }


