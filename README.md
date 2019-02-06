
**WEATHER FORECAST API**
-
- This API retrieves weather metrics of specific city.

**Functionalities:**
-
- The weather API exposes "/data" endpoint to retrieve weather informartion as JSON object .
- The weather information is returned for next 3 days from today's date. 
- The JSON object contains averages of below metrics:
      - Average temperature (in Celsius)​ of the next 3 days from today’s date for ​Day time (06:00 – 18:00)​ and ​Night time (18:00 – 06:00)​. 
      -	Average of pressure for the next 3 days​ from today’s date.
- The "/data" endpoint includes 'city' parameter as input.

**Tools and Technologies**
-
- Java, Springboot, Swagger, Maven

**Dependencies**
-
- Application relies on website "​https://openweathermap.org/​" for fetching the weather information.

**Assumptions/Limitations:**
-
- The API does not takes into consideration daylight savings time while returning the weather metrics.
- The API does not takes into consideration Time zone while calculating the weather metrics.
 
**How to Run:**
-
- Use following command : mvn spring-boot:run in weather-forecast directory

**How to Test:**
-
- Use following curl command for testing the application : curl -X GET "http://localhost:8080/data?city=pune" -H "accept:application/json"

**Future enhancements and improvements :**
-
- Better junit coverage for robust code .
- Enhance the exception handling for better user experience.
- To improve the logging, it will provide better debugging and maintainence capability.
- To add caching capabilities for improved performance .
- Input validation improvements .
- The implementation will consider timezone and daylight savings for any input "city" before calculating weather information .

**Author Information:**
-
- Soumava Chatterjee