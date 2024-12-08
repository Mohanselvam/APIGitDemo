package RESTFulTesting;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.io.File;

public class BugTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://mohanselvam2013.atlassian.net/";

		// Creating the bug in Jira
		String apiResponses = given().log().all().headers("Content-Type", "application/json").headers("Authorization",
				"Basic bW9oYW5zZWx2YW0yMDEzQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBwWEZyb2RRZVl6UC1hLVdrS2ZQQ0VrVHpVanowV2RWcjhDbFFxXy12T2ZSUk8yakpLR0lGSkEtTEZvUi1WR1MxcnlVOWcwODFXWkJkRFB3anV1MjBQQ1NXcVNsWHlqQ1BrcE1hc2pMa3J3X3pSNWI5bkRFNmhUWmY0M2ZaYlFjSlRkR3FxN0Q3aTNLNURDZUJnSVNyU1FNSWFCanhCZElHRExsZ1lVQUo2UU09QzhCQjdBNjQ")
				.body("{\r\n" + "    \"fields\": {\r\n" + "       \"project\":\r\n" + "       {\r\n"
						+ "          \"key\": \"SCRUM\"\r\n" + "       },\r\n"
						+ "       \"summary\": \"AUTOMATION TEST - Data is not populated in the webPage.\",\r\n"
						+ "       \"description\": \"Creating of an issue using project keys and issue type names using the REST API\",\r\n"
						+ "       \"issuetype\": {\r\n" + "          \"name\": \"Bug\"\r\n" + "       }\r\n"
						+ "   }\r\n" + "}")
				.when().post("rest/api/2/issue").then().log().all().assertThat().statusCode(201).extract().response()
				.asString();

		JsonPath js = new JsonPath(apiResponses);
		String responseId = js.getString("id");
		System.out.println(responseId);

		// Adding the attachment in Jira issue - Bug
		given().log().all().pathParam("Key", responseId).headers("Authorization",
				"Basic bW9oYW5zZWx2YW0yMDEzQGdtYWlsLmNvbTpBVEFUVDN4RmZHRjBwWEZyb2RRZVl6UC1hLVdrS2ZQQ0VrVHpVanowV2RWcjhDbFFxXy12T2ZSUk8yakpLR0lGSkEtTEZvUi1WR1MxcnlVOWcwODFXWkJkRFB3anV1MjBQQ1NXcVNsWHlqQ1BrcE1hc2pMa3J3X3pSNWI5bkRFNmhUWmY0M2ZaYlFjSlRkR3FxN0Q3aTNLNURDZUJnSVNyU1FNSWFCanhCZElHRExsZ1lVQUo2UU09QzhCQjdBNjQ")
				.headers("X-Atlassian-Token", "no-check")
				.multiPart("file", new File("F://Reader//pexels-ellie-burgin-1661546-29127453.jpg")).when()
				.post("rest/api/2/issue/{Key}/attachments").then().log().all().assertThat().statusCode(200).extract()
				.response().asString();

	}

}
