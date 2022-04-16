package Resource;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

//import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	// we have to make our req variable as static so that it should not create another instance 
	//else it will again behave the same and not keeping the previous tc data
	public static RequestSpecification req;
	
	public RequestSpecification requestSpecification() throws IOException {
		
		//Notes:: when multiple testdata TCs are running its replaces the log file with latest one not keeping all the testcases data so we added if condition 
		//that it will create log file once and append the subsequent test data in log file
		
		
		
		if(req == null) {
			
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		
		//getting base url from properties file
		req = new RequestSpecBuilder().setBaseUri(getConfig("baseURL")).addQueryParam("key", "qaclick123")
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				
				  .setContentType(ContentType.JSON).build();
		  
		  return req;
		}
		return req;
	}
	
	public static String getConfig(String key) throws IOException {
		Properties prop = new Properties();
		FileInputStream ip = new FileInputStream("/Users/pradeepbarthwal/eclipse-workspace/Cucumber_API/src/test/java/Resource/GlobalConfig.properties");
		prop.load(ip);
		return (String) prop.get(key);
		//return prop;
	}
	
	
	//write this utility method to fetch any jsonkey value from reponse
	
	public String getJsonpathVal(Response response,String key) {
		String resp = response.asString();
		JsonPath js = new JsonPath(resp);
		return js.get(key).toString();
		
	}

}
