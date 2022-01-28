package com.lob.examples;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lob.Lob;
import com.lob.model.Address;
import com.lob.model.BankAccount;
import com.lob.model.Check;
import com.lob.net.LobResponse;

public class CheckExample extends BaseExample {
    public static void main(final String[] args) throws Exception {

	Lob.init(API_KEY);

	LobResponse<BankAccount> exampleBankAccountResponse = new BankAccount.RequestBuilder()
		.setDescription("Test Bank Account").setRoutingNumber("322271627").setAccountNumber("123456789")
		.setSignatory("John Doe").setAccountType("company").create();

	BankAccount bankAccountResponse = exampleBankAccountResponse.getResponseBody();
	printResponse("Bank Account Response", bankAccountResponse.toString());

	List<Integer> bankAmountList = new ArrayList<Integer>();
	bankAmountList.add(25);
	bankAmountList.add(63);

	// Verify a the previously created bank account
	LobResponse<BankAccount> exampleBankAccountVerifyRequest = BankAccount.verify(bankAccountResponse.getId(),
		bankAmountList);
	BankAccount exampleBankAccountVerifyResponse = exampleBankAccountVerifyRequest.getResponseBody();

	printResponse("Bank Account Verify Response", exampleBankAccountVerifyResponse);

	// Creating an Address Object
	LobResponse<Address> exampleAddressRequest = new Address.RequestBuilder().setDescription("Harry - Office")
		.setName("Harry Zhang").setCompany("Lob").setLine1("210 King St").setLine2("# 6100")
		.setCity("San Francisco").setState("CA").setZip("94107").setCountry("US").setPhone("555-555-5555")
		.setEmail("harry@lob.com").create();

	Address exampleAddressResponse = exampleAddressRequest.getResponseBody();
	printResponse("Address Response", exampleAddressResponse);

	// Creating a Check using the previously created bank account and address
	Map<String, String> mergeVariables = new HashMap<>();
	mergeVariables.put("name", "Harry");

	LobResponse<Check> exampleCheckRequest = new Check.RequestBuilder().setDescription("Demo Check")
		.setCheckBottom("<h1 style='padding-top:4in;'>Demo Check for {{name}}</h1>")
		.setMergeVariables(mergeVariables).setAmount("22.50")
		.setLogo("https://s3-us-west-2.amazonaws.com/public.lob.com/assets/check_logo.png")
		.setTo(new Address.RequestBuilder().setName("Harry Zhang").setLine1("210 King St Ste 6100")
			.setCity("San Francisco").setState("CA").setZip("94107"))
		.setFrom(exampleAddressResponse.getId()).setBankAccount(exampleBankAccountVerifyResponse.getId())
		.create();

	Check exampleCheckResponse = exampleCheckRequest.getResponseBody();
	printResponse("Check Response", exampleCheckResponse);

	System.exit(0);
    }
}
