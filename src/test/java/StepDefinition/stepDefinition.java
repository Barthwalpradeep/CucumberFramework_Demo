package StepDefinition;

//import java.io.FileNotFoundException;
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;

import Resource.APIResources;
import Resource.TestData;
import Resource.Utils;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
//import io.restassured.RestAssured;
//import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

//import pojo.AddPlace;
//import pojo.Location;

public class stepDefinition extends Utils{
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	TestData td = new TestData();
	static String place_id;//static has been added so that all test cases will refer the value assign it in by first test case till execution completed
	
	//hooks implementation.. we can als do this by creating anothe class file with hooks and maintain all hooks there also
	@Before("@DeletePlace")
	public void beforscenario() throws IOException {
		if(place_id== null) {
		add_place_payload_with("Navedeep","kaithi","Punjab Rd","www.nav.com");
		user_calls_using_post_http_request("AddPlaceAPI","POST");
		verify_place_id_created_is_mapped_to_using("Navedeep","getPlaceAPI");
		}
		
	}
	
	@Given("Add place payload with {string},{string},{string}, {string}")
	public void add_place_payload_with(String name, String language, String address, String website) throws IOException {
	    
		  //payload is passed from resource folder TestData class file by creating object of TestData class(td) at global level)
		  //extends UTils class so that we can use all methods present in Utility directly for all our TCs
		  //res--> is request specification variable which contains our basic request variable to create a request
		//this method is for creating request specification
		 
		RestAssured.useRelaxedHTTPSValidation();   //Added this command for handling exception-->  sun.security.validator.ValidatorException: PKIX   
		
		res=given().spec(requestSpecification())
		           .body(td.addPlace_payload(name,language,address,website));
	    
	}
	
	
	@When("user calls {string} using {string} http request")
	public void user_calls_using_post_http_request(String endpoint ,String method) {
		
		//USed ENUm class constructor to fetch values of endpoint here
		APIResources Endpointurl = APIResources.valueOf(endpoint);
		System.out.println(Endpointurl.getResource());
		
		//if we dont define resspec here it will through Null pointer exception
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("POST"))
		response =res.when().post(Endpointurl.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response =res.when().get(Endpointurl.getResource());
	}
	
	
	@Then("the api call is success with staus code {int}")
	public void the_api_call_is_success_with_staus_code(Integer int1) {
	    assertEquals(response.getStatusCode(),200);
	   
	}
	
	
	@Then("{string} in response description is {string}")
	public void in_response_description_is(String Key, String Val) {
	    // Write code here that turns the phrase above into concrete action
		assertEquals(getJsonpathVal(response,Key),Val);
		
	   }
	
	@Then("verify place_id created is mapped to {string} using {string}")
	public void verify_place_id_created_is_mapped_to_using(String name, String endpoint) throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		place_id = getJsonpathVal(response,"place_id");
		res=given().spec(requestSpecification()).queryParam("place_id", place_id);
		//calling user hit the uri method
		user_calls_using_post_http_request(endpoint , "GET");
		String respName = getJsonpathVal(response,"name");
		assertEquals(respName,name);
	   
	}
	
	@Given("deletePlaceAPI payload is given")
	public void delete_place_api_payload_is_given() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
		//place_id is already been fetched in above test case 
		res=given().spec(requestSpecification()).body(td.deleteAPIpayload(place_id));
	    
	}

}
