package com.myapiproject.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.myapiproject.base.TestBase;
import com.myapiproject.pojo.Booking;
import com.myapiproject.pojo.Bookingdates;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingUsingPOJO extends TestBase {

	Response responseCreatePOJO;

	@BeforeClass
	public Response createBookingUsingPOJO() {

		logger.info("----Creating Booking using POJO----");

		Bookingdates bookingdates = new Bookingdates("2020-12-24", "2020-12-25");

		Booking booking = new Booking("Bob", "Marley", 225, false, bookingdates, "breakfast");

		// Get response of Post request
		responseCreatePOJO = httpRequest.contentType(ContentType.JSON).body(booking).post("/booking");

		responseCreatePOJO.prettyPrint();
		System.out.println(responseCreatePOJO.getStatusCode());

		return responseCreatePOJO;

	}

	@Test
	protected void checkResponseBody() {

		logger.info("Validating created POJO body");

		SoftAssert softAssert = new SoftAssert();
		String actualFirstname = responseCreatePOJO.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(actualFirstname, "Bob");

		String actualLastname = responseCreatePOJO.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(actualLastname, "Marley");

		int totalPrice = responseCreatePOJO.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(totalPrice, 225);

		boolean depositPaid = responseCreatePOJO.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertFalse(depositPaid);

		String actualCheckin = responseCreatePOJO.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2020-12-24");

		String actualCheckout = responseCreatePOJO.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2020-12-25");

		String additionalNeeds = responseCreatePOJO.jsonPath().getString("booking.additionalneeds");
		softAssert.assertEquals(additionalNeeds, "breakfast");

		softAssert.assertAll();

		responseCreatePOJO.print();
	}

	@Test
	protected void ckeckingStatusCode() {
		logger.info("Checking status code");
		int actualStatusCode = responseCreatePOJO.getStatusCode();
		Assert.assertEquals(actualStatusCode, 200, "Wrong Status code");
	}

}
