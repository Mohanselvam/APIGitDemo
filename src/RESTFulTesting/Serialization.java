package RESTFulTesting;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;

public class Serialization {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

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

		String responses = given().log().all().queryParam("key ", "qaclick123").body(addPlace).when()
				.post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).extract().response()
				.asString();
		System.out.println(responses);

	}

}
