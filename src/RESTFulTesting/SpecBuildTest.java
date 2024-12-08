package RESTFulTesting;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;

public class SpecBuildTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

// Adding the place in API server
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlace addPlace = new AddPlace();
		addPlace.setAccuracy(50);
		addPlace.setAddress("29, side layout, cohen 09");
		addPlace.setLanguage("French-IN");
		addPlace.setName("Frontline house");
		addPlace.setPhone_numbe("(+91) 983 893 3937");
		addPlace.setWebsite("http://google.com");
		List<String> l = new ArrayList<String>();
		l.add("shoe park");
		l.add("shop");
		addPlace.setTypes(l);
		Location loc = new Location();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		addPlace.setLocation(loc);

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		String responses = given().log().all().spec(reqSpec).body(addPlace).when().post("/maps/api/place/add/json")
				.then().log().all().spec(resSpec).extract().response().asString();
		System.out.println(responses);

	}

}
