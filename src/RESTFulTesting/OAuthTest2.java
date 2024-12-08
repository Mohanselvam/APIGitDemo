package RESTFulTesting;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.restassured.path.json.JsonPath;

public class OAuthTest2 {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AeanS0aZi4H7TET5fcyh603jgSwmtNDwPk9Lmj_6lE7u9HepV1omtRtoQEv8N71kBh_aSQ&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";

		WebDriver driver = new ChromeDriver();
		driver.get(
				"https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		driver.findElement(By.cssSelector("input[id='identifierId']")).sendKeys("mohanselvam2013@gmail.com");
		driver.findElement(By.cssSelector("input[id='identifierId']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("Mohan@1234567");
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		String url = driver.getCurrentUrl();
		String partialCode = url.split("code=")[1];
		String code = partialCode.split("&scope")[0];

		String response1 = given().queryParam("code", code)
				.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParam("grant_type", "authorization_code").when().log().all()
				.post("https://www.googleapis.com/oauth2/v4/token").then().log().all().extract().response().asString();

		JsonPath js = new JsonPath(response1);
		String accessToken = js.get("access_token");
		System.out.println(accessToken);

		String response2 = given().queryParam("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/getCourse.php").then().log().all().extract().response().asString();

		System.out.println(response2);

	}

}
