package RESTFulTesting;

import org.testng.Assert;
import org.testng.annotations.Test;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class SumOfValidation {

	@Test
	public void sumOfCourses() {
		JsonPath js = new JsonPath(Payload.coursePriceList());
		int count = js.getInt("courses.size()");
		int sum = 0;

		for (int i = 0; i < count; i++) {
			int price = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");
			int amount = price * copies;
			System.out.println(amount);
			sum = sum + amount;
		}
		System.out.println(sum);
		int purchase = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchase);

	}

}
