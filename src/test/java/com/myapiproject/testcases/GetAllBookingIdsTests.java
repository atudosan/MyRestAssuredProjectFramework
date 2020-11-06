package com.myapiproject.testcases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myapiproject.base.TestBase;

import io.restassured.http.Method;
import io.restassured.response.Response;

public class GetAllBookingIdsTests extends TestBase {
	
	Response response;

	@BeforeClass
	public void getAllBookingIds() {

		// logger.info("************** Started GetAllBookingIdsTests **************");

		// Get Response
		response = httpRequest.request(Method.GET, "/booking");
		response.print();
		System.out.println(response.getStatusCode());
	}

	@Test
	public void checkResponseBody() {
		logger.info("************** Checking Response Body **************");
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		Assert.assertTrue(responseBody != null, "No resposnse Body");
	}

	@Test
	public void checkListOfBookindIDs() {
		logger.info("************** Checking Booking ID's List  **************");
		List<Integer> bookingIds = response.jsonPath().getList("bookingid");
		System.out.println("Size of Booking ID's List = " + bookingIds.size());
		Assert.assertFalse(bookingIds.isEmpty(), "Our Booking ID's List is Empty");
	}

	@Test
	public void checkStatusCode() {
		logger.info("************** Checking Status Code  **************");
		int actualStatusCode = response.getStatusCode();
		System.out.println("Satus Code ---> " + actualStatusCode);
		Assert.assertEquals(actualStatusCode, 200, "Status code is wrong");
	}

}
