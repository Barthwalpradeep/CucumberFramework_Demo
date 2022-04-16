package Resource;

public enum APIResources {
	
	AddPlaceAPI("maps/api/place/add/json"),
	getPlaceAPI("maps/api/place/get/json"),
	deletePlaceAPI("maps/api/place/delete/json");
	private String resource;
	
	//Create constructor for enum class which should have accept the argument as same as the constant method declare above
	
	//Notes::whenver this constructor being called it will always load the resource value with the value mapped
	//Notes::for e.g:: APIResources.valueof(AddPlaceAPI) will store  "("maps/api/place/add/json")"
	
	
	APIResources(String resource){
		//assigning current resource value with initiated one
		this.resource = resource;
	}
	
	
	public String getResource() {
		return resource;
	}
}
