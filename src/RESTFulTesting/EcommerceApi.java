package RESTFulTesting;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import POJO.LoginResponses;
import POJO.Orders;
import POJO.OrdersDetails;
import POJO.RequestLogin;

public class EcommerceApi {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Login API
		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();

		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		RequestLogin requestLogin = new RequestLogin();
		requestLogin.setUserEmail("mohanselvam2013@gmail.com");
		requestLogin.setUserPassword("Mohan@123");

		RequestSpecification request = given().log().all().spec(reqSpec).body(requestLogin);
		LoginResponses responses = request.when().post("/api/ecom/auth/login").then().log().all().spec(resSpec)
				.extract().response().as(LoginResponses.class);

		System.out.println(responses.getToken());
		System.out.println(responses.getUserId());

		// Add Product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", responses.getToken()).build();

		RequestSpecification addProductRequest = given().spec(addProductBaseReq).param("productName", "Car")
				.param("productAddedBy", responses.getUserId()).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "11500")
				.param("productDescription", "Addias Originals").param("productFor", "women")
				.multiPart("productImage", new File("F://Reader//pexels-arturo-albarran-1951361020-29410748.jpg"));

		String addProductResponse = addProductRequest.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().response().asString();

		JsonPath js = new JsonPath(addProductResponse);
		String productID = js.get("productId");
		System.out.println(productID);

		// Create Order
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", responses.getToken()).setContentType(ContentType.JSON).build();

		OrdersDetails ordersDetails = new OrdersDetails();
		ordersDetails.setCountry("India");
		ordersDetails.setProductOrderedId("674c5d7ceb3c71ba792029be");

		List<OrdersDetails> listOfOrderDetails = new ArrayList<OrdersDetails>();
		listOfOrderDetails.add(ordersDetails);

		Orders order = new Orders();
		order.setOrders(listOfOrderDetails);

		RequestSpecification createOrderRequest = given().log().all().spec(createOrderBaseReq).body(order);
		String createOrderResponse = createOrderRequest.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();

		// Delete Product
		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", responses.getToken())
				.setContentType(ContentType.JSON).build();

		RequestSpecification deleteProduct = given().log().all().spec(deleteProductBaseReq).pathParam("productID",
				productID);

		String deleteResponse = deleteProduct.when().delete("/api/ecom/product/delete-product/{productID}").then().log()
				.all().extract().response().asString();

		JsonPath js2 = new JsonPath(deleteResponse);
		String message = js2.get("message");
		Assert.assertEquals(message, "Product Deleted Successfully");

	}

}
