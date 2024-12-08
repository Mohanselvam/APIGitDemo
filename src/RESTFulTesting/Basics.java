package RESTFulTesting;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;

import Files.Payload;
import Files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class Basics {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String response = given().log().all().queryParam("key", "qaclick123")
				.headers("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("F:\\Reader\\Addplace.json"))))

				.when().post("maps/api/place/add/json")

				
				.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
				.header("Content-Type", "application/json;charset=UTF-8").header("Server", "Apache/2.4.52 (Ubuntu)")
				.extract().response().asString();

		System.out.println(response);
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);

		String newAddress = "70 Summer walk, Russia";

		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "\"place_id\":\"" + placeId + "\",\r\n" + "\"address\":\"" + newAddress + "\",\r\n"
						+ "\"key\":\"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		String getPlaceResponses = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.headers("Connection", "keep-alive")

				.when().get("maps/api/place/get/json")

				.then().log().all().assertThat().statusCode(200).extract().response().asString();

		JsonPath js1 = ReUsableMethods.rawToJson(getPlaceResponses);
		String actualAdderess = js1.getString("address");
		System.out.println(actualAdderess);
		Assert.assertEquals(actualAdderess, newAddress);

	}

}
