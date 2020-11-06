package com.myapiproject.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myapiproject.base.*;

import io.restassured.http.Method;
import io.restassured.response.Response;

public class GetSingleBookingRecord extends TestBase {

	Response response;
	SoftAssert softAssert;

	@BeforeClass
	public void getBookingData() {

		logger.info("************* Started GetSingleBookingRecord Tests **************");

		Response responseCreate = createBooking();
		logger.info("we've created a working booking for this test");
		responseCreate.prettyPrint();

		int myBookingId = responseCreate.jsonPath().getInt("bookingid");
		logger.info("exctracting id from newly created booking");

		// uri.pathParam("bookingid", myBookingId);
		// logger.info("setting path parameter");

		response = httpRequest.request(Method.GET, "/booking/" + myBookingId);
		logger.info("sending our get request");

	}

	@Test
	public void checkStatusCode() {
		logger.info("************** Checking Status Code  **************");
		int actualStatusCode = response.getStatusCode();
		System.out.println("Satus Code ---> " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200, "Status code is wrong");
	}

	@Test
	public void checkResponseBody() {
		softAssert = new SoftAssert();
		String actualFirstname = response.jsonPath().getString("firstname");
		softAssert.assertEquals(actualFirstname, "Alexandru");

		String actualLastname = response.jsonPath().getString("lastname");
		softAssert.assertEquals(actualLastname, "Tudosan");

		int totalPrice = response.jsonPath().getInt("totalprice");
		softAssert.assertEquals(totalPrice, 199);

		boolean depositPaid = response.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(depositPaid);

		String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2020-12-01");

		String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2020-12-02");

		softAssert.assertAll();

	}

	@Test
	public void checkHeaderServerInfo() {
		logger.info("************** Checking Headers  **************");
		String actualServerName = response.getHeader("Server");
		Assert.assertEquals(actualServerName, "Cowboy", "Server Name Mismatch");
		String actualContentType = response.getHeader("Content-Type");
		Assert.assertEquals(actualContentType, "application/json; charset=utf-8", "Content Type Mismatch");
	}

	@Test
	public void checkingResponseTime() {
		logger.info("************** Checking Response Time  **************");
		long actualResponseTime = response.getTime();
		if (actualResponseTime > 3000) {
			logger.warn("Respose Time is " + actualResponseTime);
		} else {
			logger.info("Response time = " + actualResponseTime);
		}
		Assert.assertTrue(actualResponseTime <= 3000, "Response time is greater then 3 seconds");

	}

	@AfterClass
	public void tearDown() {

		logger.info("************** Finished GetAllBookingIds Tests **************");

	}

}
