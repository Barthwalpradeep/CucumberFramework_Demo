Feature: Validating place API's'

@AddPlace
Scenario Outline: verify if place is being successfully added using add place API
   Given Add place payload with "<name>","<language>","<address>", "<website>"
   When user calls "AddPlaceAPI" using "POST" http request
   Then the api call is success with staus code 200
   And "status" in response description is "OK"
   And "scope" in response description is "APP"
   And verify place_id created is mapped to "<name>" using "getPlaceAPI"
   
   Examples:
    |name      |language|address            |website                |
    |MadanMohan|Hindi   |Saint bernard Cross|https://www.chennai.com|
  #  |damyanti  |German  |Saint Anthony Rd   |https://www.delhin.com |
   
   
@DeletePlace   
 Scenario: verify if place is being successfully deleted using deletePlace API
   Given deletePlaceAPI payload is given
   When user calls "deletePlaceAPI" using "POST" http request
   Then the api call is success with staus code 200
   And "status" in response description is "OK"
   