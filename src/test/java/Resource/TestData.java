package Resource;

import java.util.ArrayList;
import java.util.List;

import pojo.AddPlace;
import pojo.Location;

public class TestData {
	
	public AddPlace addPlace_payload(String name, String language, String address, String website) {

		AddPlace p= new AddPlace();
		
		p.setAccuracy(90);
		p.setAddress(address);
		p.setLanguage(language);
		p.setPhone_number("(+91) 983 893 3937");
		p.setWebsite(website);
		p.setName(name);
		List<String> myList =new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");

		p.setTypes(myList);
		Location l =new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		
		return p;
	}
	
	public String deleteAPIpayload(String place_id) 
	{
		
		return "{\r\n    \"place_id\":\""+place_id+"\"\r\n}";
	}
	

}
