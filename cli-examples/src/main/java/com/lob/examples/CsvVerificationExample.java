package com.lob.examples;

import java.io.FileReader;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.lob.Lob;
import com.lob.model.USVerification;
import com.lob.net.LobResponse;

public class CsvVerificationExample extends BaseExample {
    public static void main(final String[] args) throws Exception {

	Lob.init(API_KEY);

	@SuppressWarnings("deprecation")
	CSVParser parser = new CSVParser(new FileReader("src/main/resources/addresses/input.csv"),
		CSVFormat.DEFAULT.withHeader());
	for (CSVRecord record : parser) {

	    LobResponse<USVerification> usVerificationRequest = new USVerification.RequestBuilder()
		    .setPrimaryLine(record.get("address1")).setSecondaryLine(record.get("address2"))
		    .setCity(record.get("city")).setState(record.get("state")).setZipCode(record.get("postcode"))
		    .verify();

	    try {
		USVerification usVerificationResponse = usVerificationRequest.getResponseBody();
		printResponse("Verified Address", usVerificationResponse);
	    } catch (final Exception e) {
		printResponse("Failed US verification", usVerificationRequest);
	    }

	}

	System.exit(0);
    }

}
