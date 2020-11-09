package com.myapiproject.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myapiproject.base.TestBase;
import com.myapiproject.pojo.Booking;
import com.myapiproject.pojo.Bookingdates;
import com.myapiproject.pojo.Bookingid;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateAndValidateBookingUsingPOJO extends TestBase {

	Response responseCreatePOJO;
	Booking booking;
	Bookingid bookingid;
	Bookingdates bookingdates;

	@BeforeClass
	public void createBookingUsingPOJO() {

		logger.info("----Creating Booking using POJO----");

		bookingdates = new Bookingdates("2020-12-24", "2020-12-25");

		booking = new Booking("Bob", "Marley", 225, false, bookingdates, "breakfast");

		// Get response of Post request
		responseCreatePOJO = httpRequest.contentType(ContentType.JSON).body(booking).post("/booking");

		responseCreatePOJO.prettyPrint();
		//return responseCreatePOJO;
		
		bookingid = responseCreatePOJO.as(Bookingid.class);
	} 

	@Test
	protected void checkResponseBody() {

		logger.info("Validating created POJO body");
		Assert.assertEquals(bookingid.getBooking().toString(), booking.toString());
		
		System.out.println("Request Booking =  "+booking.toString());
		System.out.println("Response Booking = "+bookingid.getBooking().toString());
	}

	@Test
	protected void ckeckingStatusCode() {
		logger.info("Checking status code");
		int actualStatusCode = responseCreatePOJO.getStatusCode();
		Assert.assertEquals(actualStatusCode, 200, "Wrong Status code");
	}

}
