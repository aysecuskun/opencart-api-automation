package ProductApi;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;

public class ProductSearchTest {
   
	@Test(priority=1)
	public void searchProduct() {
		
	Response response=
	given().
	
	when().
	get("http://127.0.0.1/opencart/upload/index.php?route=product/search&language=en-gb&search=macbook");
	Assert.assertTrue(response.getBody().asString().contains("Search - macbook"));
	
	System.out.println(response.getStatusCode());
	System.out.println(response.getBody().asString());

	}
	
	@Test(priority=2)
	public void invalidSearchProduct() {
		
	Response response=
	given().
	
	when().
	get("http://127.0.0.1/opencart/upload/index.php?route=product/search&language=en-gb&search=hdhdhhdhdhhdhd");
	Assert.assertTrue(response.getBody().asString().contains("Products meeting the search criteria"));
	
	System.out.println(response.getStatusCode());
	System.out.println(response.getBody().asString());

	}
	
	@Test(priority=3)
	public void emptySearchProduct() {
		
	Response response=
	given().
	
	when().
	get("http://127.0.0.1/opencart/upload/index.php?route=product/search&language=en-gb&search=hdhdhhdhdhhdhd");
	Assert.assertTrue(response.getBody().asString().contains("Search - "));
	
	System.out.println(response.getStatusCode());


	}
	@Test(priority=4)
	public void SearchProductDetails() {
		
	Response response=
	given()
	.queryParam("route", "product/product")
    .queryParam("language", "en-gb")
    .queryParam("product_id", "44")
    .queryParam("search", "macbook")
	.when()
	.get("http://127.0.0.1/opencart/upload/index.php");
	
	System.out.println(response.getStatusCode());
	System.out.println(response.getBody().asString());
	Assert.assertTrue(response.getBody().asString().contains("MacBook Air"));
	}
	
	
}
