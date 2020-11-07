package com.myapiproject.testcases;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myapiproject.base.TestBase;

import io.restassured.http.Header;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class GetSingleBookingRecordXML extends TestBase {

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

		Header xml = new Header("Accept", "application/xml");
		

		response = httpRequest.header(xml)
							  .request(Method.GET, "/booking/" + myBookingId);
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
		String actualFirstname = response.xmlPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstname, randomFirstName);

		String actualLastname = response.xmlPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastname, randomLastName);

		int totalPrice = response.xmlPath().getInt("booking.totalprice");
		softAssert.assertEquals(totalPrice, randomFinalPrice);

		boolean depositPaid = response.xmlPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(depositPaid);

		String actualCheckin = response.xmlPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2020-12-01");

		String actualCheckout = response.xmlPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2020-12-02");

		softAssert.assertAll();

	}

	@Test
	public void checkHeaderServerInfo() {
		logger.info("************** Checking Headers  **************");
		String actualServerName = response.getHeader("Server");
		Assert.assertEquals(actualServerName, "Cowboy", "Server Name Mismatch");
		String actualContentType = response.getHeader("Content-Type");
		Assert.assertEquals(actualContentType, "text/html; charset=utf-8", "Content Type Mismatch");
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
