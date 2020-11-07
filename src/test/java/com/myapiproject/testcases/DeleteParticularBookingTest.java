package com.myapiproject.testcases;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import com.myapiproject.base.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DeleteParticularBookingTest extends TestBase {
	
	Response deleteResponse;
	int idBooking;
	
	@BeforeClass
	public void deleteBookingTests() {
		
		Response response = createBooking();
		response.print();
		
		idBooking = response.jsonPath().getInt("bookingid");
		
		//Set path param
		//uri.pathParam("bookingId", idBooking);

		
		deleteResponse = httpRequest.auth().preemptive().basic("admin", "password123")
											 .delete("/booking/"+idBooking);
		deleteResponse.print();
		
		
	}
		
		@Test
		public void checkStatusCode() {
		int statusCode = deleteResponse.getStatusCode();
		Assert.assertEquals(statusCode, 201);
		}
		
		
		@Test
		public void checkGetResposeForDeletedId() {
		//Verifing response body when we look for deleted Id
		Response responseGetDeletedId = httpRequest.get("/booking/"+idBooking);
		
		String actualResponseBody = responseGetDeletedId.getBody().asString();
		Assert.assertEquals(actualResponseBody, "Not Found");	
		}
		
		@Test
		public void checkIdListForDeletedId() {
		
		//Verifing if there is deleted ID in the List of ids
		Response responseGetIds = httpRequest.get("/booking");
		responseGetIds.print();
		List<Integer> bookingIds = responseGetIds.jsonPath().getList("bookingid");
		Assert.assertFalse(bookingIds.contains(idBooking));
		
		}

}
