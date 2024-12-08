package RESTFulTesting;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.Payload;
import Files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class DynamicJson {

	@Test(dataProvider = "BookData")
	public void addBook(String isbn, String aisle) {

		RestAssured.baseURI = "http://216.10.245.166";
		String addBookApi = given().log().all().header("Content-Type", "application/json")
				.body(Payload.addBook(isbn, aisle))

				.when().post("Library/Addbook.php")

				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		JsonPath js = ReUsableMethods.rawToJson(addBookApi);
		String res = js.get("ID");
		System.out.println(res);

	}

	@DataProvider(name = "BookData")
	public Object[][] getData() {
		return new Object[][] { { "wstrt", "1234" }, { "rfghh", "5678" }, { "ghjj", "6789" } };
	}

}
