package com.myapiproject.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import com.myapiproject.utilities.RandomBuldingData;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	
	protected static RequestSpecification httpRequest;
	protected RequestSpecification uri;
	protected Response response;
	
	protected String randomFirstName = RandomBuldingData.firstname();
	protected String randomLastName = RandomBuldingData.lastname();
	protected int randomFinalPrice = RandomBuldingData.totalprice();
	
	public Logger logger;
	
	@BeforeClass
	public void logSetUp() {
		logger = Logger.getLogger("MyAPIproject");  //assign a name 
		PropertyConfigurator.configure("Log4j.properties"); // specify location of your Log4j.properties file
		logger.setLevel(Level.DEBUG);  // set the log level of your logs
		}
	
	@BeforeClass
	public void setUp(){
		uri = new RequestSpecBuilder().
				setBaseUri("https://restful-booker.herokuapp.com").
				build();
		httpRequest = RestAssured.given(uri);
		}
	
	
	
	protected Response createBooking() {
		JSONObject body = new JSONObject();
		body.put("firstname", randomFirstName);
		body.put("lastname", randomLastName);
		body.put("totalprice", randomFinalPrice);
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
