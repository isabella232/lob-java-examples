package com.lob.examples;

import com.lob.Lob;
import com.lob.model.Address;
import com.lob.model.Postcard;
import com.lob.net.LobResponse;

public class PostcardExample extends BaseExample {
    public static void main(final String[] args) throws Exception {

	Lob.init(API_KEY);

	// Creating an Address Object
	LobResponse<Address> exampleAddressRequest = new Address.RequestBuilder().setDescription("Harry - Office")
		.setName("Harry Zhang").setCompany("Deluxe Virgina Realty").setLine1("210 King St")
		.setCity("San Francisco").setState("CA").setZip("94107").setCountry("US").setPhone("555-555-5555")
		.setEmail("harry@lob.com").create();

	Address exampleAddressResponse = exampleAddressRequest.getResponseBody();
	printResponse("Address Response", exampleAddressResponse);

	// Creating a Postcard
	LobResponse<Postcard> examplePostcardRequest = new Postcard.RequestBuilder().setDescription("Demo Postcard job")
		.setTo(exampleAddressResponse.getId()).setFrom(exampleAddressResponse.getId())
		.setFront("https://s3.us-west-2.amazonaws.com/public.lob.com/assets/6.25x4.24-Cats-Postcard-Front.png")
		.setBack("https://s3.us-west-2.amazonaws.com/public.lob.com/assets/6.25x4.24-Cats-Postcard-Back.png")
		.create();

	Postcard examplePostcardResponse = examplePostcardRequest.getResponseBody();

	printResponse("Postcard Response", examplePostcardResponse);

	System.exit(0);
    }
}
