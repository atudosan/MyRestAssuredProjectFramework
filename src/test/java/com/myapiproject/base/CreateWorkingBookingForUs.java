package com.myapiproject.base;

import org.json.JSONObject;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateWorkingBookingForUs extends TestBase{
	
	//protected static RequestSpecification spec;
	
	public Response createBooking() {
		JSONObject body = new JSONObject();
		body.put("firstname", "Alexandru");
		body.put("lastname", "Tudosan");
		body.put("totalprice", 199);
		body.put("depositpaid", true);

		
		JSONObject bookingdates = new JSONObject();
		bookingdates.put("checkin", "2020-12-01");
		bookingdates.put("checkout", "2020-12-02");

		body.put("bookingdates", bookingdates);
		body.put("additionalneeds", "baby crib");

		// Get response of Post request
		Response response = RestAssured.given(uri).contentType(ContentType.JSON).body(body.toString())
				.post("/booking");
		return response;
	}


}
