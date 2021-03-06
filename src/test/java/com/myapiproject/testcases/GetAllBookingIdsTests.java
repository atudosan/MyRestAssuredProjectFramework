package com.myapiproject.testcases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.myapiproject.base.TestBase;

import io.restassured.http.Method;
import io.restassured.response.Response;

public class GetAllBookingIdsTests extends TestBase {
	
	Response response;

	@BeforeClass
	public void getAllBookingIds() {

		logger.info("************* Started GetAllBookingIds Tests **************");

		// Get Response
		response = httpRequest.request(Method.GET, "/booking");
		response.print();		
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
		if(actualResponseTime>3000) {
			logger.warn("Respose Time is "+ actualResponseTime);
		}
		else {logger.info("Response time = "+ actualResponseTime);}
		Assert.assertTrue(actualResponseTime<=3000, "Response time is greater then 3 seconds");
		
	}
	
	@AfterClass
	public void tearDown() {

		logger.info("************** Finished GetAllBookingIds Tests **************");
		
	}

	
	

}
