package com.myapiproject.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myapiproject.base.TestBase;

import io.restassured.http.Method;
import io.restassured.response.Response;

public class UpdateBookingRecordTest extends TestBase {
	
	Response responseUpdate;

	@BeforeClass
	public void updateBookingRecord() {
	// Create booking
			Response responseCreate = createBooking();
			responseCreate.print();

			// Get id from newly created booking
			int bookingID = responseCreate.jsonPath().getInt("bookingid");
			System.out.println(bookingID);

			// Set path param
			//uri.pathParam("bookingId", bookingid);

			// Create an object in order to update with the older one
			JSONObject requestParam  = new JSONObject();
			
			requestParam.put("firstname", "Iury");
			requestParam.put("lastname", "Ivanov");
			requestParam.put("totalprice", 169);
			requestParam.put("depositpaid", true);

			JSONObject bookingdates = new JSONObject();
			bookingdates.put("checkin", "2018-01-01");
			bookingdates.put("checkout", "2018-01-02");

			requestParam.put("bookingdates", bookingdates);
			requestParam.put("additionalneeds", "baby crib");
						
			responseUpdate = httpRequest.header("Content-Type", "application/json")
										   .body(requestParam.toJSONString())			
										   .auth().preemptive().basic("admin", "password123")
										   .request(Method.PUT, "/booking/"+bookingID);
			
			responseUpdate.prettyPrint();
			}
	
	@Test
	public void checkingResponseTime() {
		logger.info("************** Checking Response Time  **************");
		long actualResponseTime = responseUpdate.getTime();
		if (actualResponseTime > 3000) {
			logger.warn("Respose Time is " + actualResponseTime);
		} else {
			logger.info("Response time = " + actualResponseTime);
		}
		Assert.assertTrue(actualResponseTime <= 3000, "Response time is greater then 3 seconds");

	}

	@Test
	public void checkResponseBody() {
			Assert.assertEquals(responseUpdate.getStatusCode(), 200);

			SoftAssert softAssert = new SoftAssert();
			String actualFirstname = responseUpdate.jsonPath().getString("firstname");
			softAssert.assertEquals(actualFirstname, "Iury");

			String actualLastname = responseUpdate.jsonPath().getString("lastname");
			softAssert.assertEquals(actualLastname, "Ivanov");

			int totalPrice = responseUpdate.jsonPath().getInt("totalprice");
			softAssert.assertEquals(totalPrice, 169);

			boolean depositPaid = responseUpdate.jsonPath().getBoolean("depositpaid");
			softAssert.assertTrue(depositPaid);

			String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
			softAssert.assertEquals(actualCheckin, "2018-01-01");

			String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
			softAssert.assertEquals(actualCheckout, "2018-01-02");

			String additionalNeeds = responseUpdate.jsonPath().getString("additionalneeds");
			softAssert.assertEquals(additionalNeeds, "baby crib");

			softAssert.assertAll();

			responseUpdate.print();

	}
}
