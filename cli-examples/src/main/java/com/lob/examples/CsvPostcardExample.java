package com.lob.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.lob.Lob;
import com.lob.model.Address;
import com.lob.model.Postcard;
import com.lob.net.LobResponse;

public class CsvPostcardExample extends BaseExample {
    public static void main(final String[] args) throws Exception {

	Lob.init(API_KEY);

	// Creating an Address Object
	LobResponse<Address> exampleAddressRequest = new Address.RequestBuilder().setDescription("Harry - Office")
		.setName("Harry Zhang").setCompany("Deluxe Virgina Realty").setLine1("210 King St")
		.setCity("San Francisco").setState("CA").setZip("94107").setCountry("US").setPhone("555-555-5555")
		.setEmail("harry@lob.com").create();

	Address exampleAddressResponse = exampleAddressRequest.getResponseBody();
	printResponse("Address Response", exampleAddressResponse);

	CsvLetterExample obj = new CsvLetterExample();

	StringBuilder frontHTML = new StringBuilder();
	InputStream frontInputStream = obj.getClass().getClassLoader()
		.getResourceAsStream("postcards/postcard_front.html");

	try (BufferedReader reader = new BufferedReader(new InputStreamReader(frontInputStream))) {
	    String line;
	    while ((line = reader.readLine()) != null) {
		frontHTML.append(line);
	    }
	}

	StringBuilder backHTML = new StringBuilder();
	InputStream backInputStream = obj.getClass().getClassLoader()
		.getResourceAsStream("postcards/postcard_back.html");

	try (BufferedReader reader = new BufferedReader(new InputStreamReader(backInputStream))) {
	    String line;
	    while ((line = reader.readLine()) != null) {
		backHTML.append(line);
	    }
	}

	@SuppressWarnings("deprecation")
	CSVParser parser = new CSVParser(new FileReader("src/main/resources/postcards/input.csv"),
		CSVFormat.DEFAULT.withHeader());
	for (CSVRecord record : parser) {

	    Map<String, String> mergeVariables = new HashMap<>();
	    mergeVariables.put("background_color", record.get("background_color"));
	    mergeVariables.put("background_image", record.get("background_image"));
	    mergeVariables.put("name", record.get("name"));
	    mergeVariables.put("car", record.get("car"));
	    mergeVariables.put("mileage", record.get("mileage"));

	    LobResponse<Postcard> examplePostcardRequest = new Postcard.RequestBuilder()
		    .setDescription("Demo Postcard job")
		    .setTo(new Address.RequestBuilder().setName(record.get("name")).setLine1(record.get("address1"))
			    .setLine2(record.get("address2")).setCity(record.get("city")).setState(record.get("state"))
			    .setZip(record.get("postcode")).setCountry(record.get("country")))
		    .setFrom(exampleAddressResponse.getId()).setFront(frontHTML.toString()).setBack(backHTML.toString())
		    .setMergeVariables(mergeVariables).create();

	    try {
		Postcard examplePostcardResponse = examplePostcardRequest.getResponseBody();
		printResponse("Postcard", examplePostcardResponse);
	    } catch (final Exception e) {
		printResponse("Failed Postcard", examplePostcardRequest);
	    }
	}

	System.exit(0);
    }

}
