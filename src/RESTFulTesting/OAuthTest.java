package RESTFulTesting;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import POJO.Api;
import POJO.GetCourse;
import POJO.WebAutomation;
import io.restassured.path.json.JsonPath;

public class OAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] listOfCoursesTitle = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		String responses = given().log().all()
				.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
				.formParam("scope", "trust").when()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();

		System.out.println(responses);

		JsonPath js = new JsonPath(responses);
		String token_responses = js.get("access_token");
		System.out.println(token_responses);

		GetCourse responses2 = given().log().all().queryParam("access_token", token_responses).when()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails?access_token=" + token_responses + "")
				.then().log().all().extract().response().as(GetCourse.class);

		System.out.println(responses2.getLinkedIn());
		System.out.println(responses2.getInstructor());

		String title = responses2.getCourses().getApi().get(1).getCourseTitle();
		System.out.println(title);

		List<Api> listOfCourses = responses2.getCourses().getApi();

		for (int i = 0; i < listOfCourses.size(); i++) {
			if (listOfCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(listOfCourses.get(i).getPrice());
			}

		}
		ArrayList<String> a = new ArrayList<String>();

		List<WebAutomation> listOfAutomationCourse = responses2.getCourses().getWebAutomation();

		for (int j = 0; j < listOfAutomationCourse.size(); j++) {
			a.add(listOfAutomationCourse.get(j).getCourseTitle());
		}

		List<String> actualCourses = Arrays.asList(listOfCoursesTitle);

		Assert.assertTrue(a.equals(actualCourses));

	}

}
