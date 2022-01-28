package com.lob.examples;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.lob.Lob;
import com.lob.model.Address;
import com.lob.model.Letter;
import com.lob.net.LobResponse;

public class CsvLetterExample extends BaseExample {
    public static void main(final String[] args) throws Exception {

	Lob.init(API_KEY);

	CsvLetterExample obj = new CsvLetterExample();

	StringBuilder out = new StringBuilder();
	InputStream inputStream = obj.getClass().getClassLoader().getResourceAsStream("letters/letter_template.html");

	try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
	    String line;
	    while ((line = reader.readLine()) != null) {
		out.append(line);
	    }
	}

	// Creating an Address Object
	LobResponse<Address> exampleAddressRequest = new Address.RequestBuilder().setDescription("Harry - Office")
		.setName("Harry Zhang").setCompany("Deluxe Virgina Realty").setLine1("210 King St")
		.setCity("San Francisco").setState("CA").setZip("94107").setCountry("US").setPhone("555-555-5555")
		.setEmail("harry@lob.com").create();

	Address exampleAddressResponse = exampleAddressRequest.getResponseBody();
	printResponse("Address Response", exampleAddressResponse);

	@SuppressWarnings("deprecation")
	CSVParser parser = new CSVParser(new FileReader("src/main/resources/letters/input.csv"),
		CSVFormat.DEFAULT.withHeader());
	for (CSVRecord record : parser) {

	    Map<String, String> mergeVariables = new HashMap<>();
	    String today = new SimpleDateFormat("MMMM d YYYY").format(new Date());

	    mergeVariables.put("name", record.get("name"));
	    mergeVariables.put("date", today);
	    mergeVariables.put("amountDue", record.get("amount"));

	    LobResponse<Letter> letterResponse = new Letter.RequestBuilder().setDescription("Demo Letter")
		    .setFile(out.toString()).setColor(true).setMergeVariables(mergeVariables)
		    .setTo(new Address.RequestBuilder().setName(record.get("name")).setLine1(record.get("address1"))
			    .setCity(record.get("city")).setState(record.get("state")).setZip(record.get("zip")))
		    .setFrom(exampleAddressResponse.getId()).create();

	    try {
		Letter exampleLetterResponse = letterResponse.getResponseBody();
		printResponse("Letter", exampleLetterResponse);
	    } catch (final Exception e) {
		printResponse("Failed Letter", letterResponse);
	    }

	}
	parser.close();

	System.exit(0);
    }
}
