package Files;

import io.restassured.path.json.JsonPath;

public class ReUsableMethods {

        //Adding methods in Reusable of API

	public static JsonPath rawToJson(String response) {
		JsonPath js1 = new JsonPath(response);
		return js1;
	}

}
 