package com.finleap.assignment.weatherforecast.controller;

import com.finleap.assignment.weatherforecast.service.AverageWeatherForecastService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "/", description = "weather forecast data")
public class WeatherForecastController {

    @Autowired
    AverageWeatherForecastService forecastService;
    @ApiOperation(value = "get average weather data",
            notes = "Api to get average weather  forecast data of a city for 3 days",
            response = ResponseEntity.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 404, message = "City not found"),
            @ApiResponse(code = 500, message = "Internal Error"),
            @ApiResponse(code = 503, message = "Service Unavailable")})
    @GetMapping(value ="/data", produces = "application/json")
    public ResponseEntity<?> getAverageWeatherData(@RequestParam("city") String city) {
        return new ResponseEntity<>(forecastService.getAvgData(city.trim()), HttpStatus.OK);
    }
}
