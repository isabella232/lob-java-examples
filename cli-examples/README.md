# Java Examples
Here we have put together a hand full of java examples to help get you started. As always feel free to [file an issue](https://github.com/lob/lob-java-examples/issues) if you have any questions on implementation.


## Getting started

Start off by cloning the repo to your preferred location.  

### Get your API keys

To run these code examples we need our own API keys from your Lob account. If you don't have an account, [register for your free account](https://dashboard.lob.com/#/register) now.

Retrieve your API keys from your Lob dashboard by clicking on the Settings menu on the sidebar, then clicking on the API Keys tab, as this screenshot illustrates:

![Lob API Keys](https://raw.githubusercontent.com/lob/lob-java-examples/main/cli-examples/apikeys.png)

### Set your API key in the BaseExample file.

Copy and paste your test_* API key into  BaseExample.java located in src/main/java/com/lob/examples

```bash
protected static final String API_KEY = "YOUR_TEST_API_KEY"; 
```

All examples extend the BaseExample class and utilze this API_KEY.

### Compile the code

Open your terminal and navigate to the `lob-java-examples/cli-examples` directory and run the clean package command to build your project.

```bash
lob$ cd lob-java-examples/cli-examples
cli-examples$ mvn clean package
```

## Examples

### Create letters from CSV

An example showing how to dynamically create letters from a CSV using HTML, a custom font, variable data, and Lob's [Letter API](https://docs.lob.com/#tag/Letters). 


```bash
cli-examples$mvn exec:java -Dexec.mainClass="com.lob.examples.CsvLetterExample"
```

### Create postcards from CSV

An example showing how to dynamically create postcards from a CSV using HTML, a custom font, variable data, and Lob's [Postcard API](https://docs.lob.com/#tag/Postcards). 

```bash
cli-examples$ mvn exec:java -Dexec.mainClass="com.lob.examples.CsvPostcardExample"
```

### Verify addresses from CSV

An example showing how to validate and cleanse a CSV spreadsheet full of shipping addresses using Lob's [US Address Verification API](https://docs.lob.com/#tag/US-Verifications). 


```bash
cli-examples$ mvn exec:java -Dexec.mainClass="com.lob.examples.CsvVerificationExample"
```

### Create a check

An example showing how to create a single check using Lob's [Bank Account](https://docs.lob.com/#tag/Bank-Accounts) & [Checks API](https://docs.lob.com/#tag/Checks).


```bash
cli-examples$ mvn exec:java -Dexec.mainClass="com.lob.examples.CheckExample"
```

### Create a postcard
An example showing how to create a single check using Lob's [Postcard API](https://docs.lob.com/#tag/Postcards).

```bash
cli-examples$ mvn exec:java -Dexec.mainClass="com.lob.examples.PostcardExample"
```
