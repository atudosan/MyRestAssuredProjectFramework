package com.myapiproject.base;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TestBase {
	
	protected static RequestSpecification httpRequest;
	protected RequestSpecification uri;
	protected CreateWorkingBookingForUs workingBooking;
	
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
	
	public void createWorkingBookingForUs() {
		workingBooking.createBooking();		
	}

}
