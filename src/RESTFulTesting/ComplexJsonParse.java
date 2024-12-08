package RESTFulTesting;

import Files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonPath js = new JsonPath(Payload.coursePriceList());

		// 1.Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// 2.Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmount);

		// 3.Print Title of the first course
		String title = js.getString("courses[0].title");
		System.out.println(title);

		// 4.Print All course titles and their respective Prices
		for (int i = 0; i < count; i++) {
			String courseTitle = js.getString("courses[" + i + "].title");
			System.out.println(courseTitle);
			int coursePrice = js.getInt("courses[" + i + "].price");
			System.out.println(coursePrice);
		}

		System.out.println("5.Print no of copies sold by RPA Course");
		for (int i = 0; i < count; i++) {
			String courseTitle = js.getString("courses[" + i + "].title");
			if (courseTitle.equalsIgnoreCase("RPA")) {
				int noOfCopies = js.getInt("courses[" + i + "].copies");
				System.out.println(noOfCopies);
				break;
			}
		}

	}

}
