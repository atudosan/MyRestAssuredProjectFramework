package com.myapiproject.utilities;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomBuldingData {
	
	static String finalFirstname;
	
	public static String firstname() {
		String generatedString = RandomStringUtils.randomAlphabetic(2).toLowerCase();
		String finalFirstname = "Ca"+ generatedString+"on";
		return finalFirstname;
	}
	
	public static String lastname() {
		String generatedString = RandomStringUtils.randomAlphabetic(2).toLowerCase();
		String finalLastname = "T"+generatedString+"man";
		return finalLastname;
	}
	
	public static int totalprice() {
	Random random = new Random();
	int randomTotalPrice = random.nextInt(180)+160;
	return randomTotalPrice;	
	}

}
