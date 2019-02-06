package com.finleap.assignment.weatherforecast.exception;

public class WeatherServiceException extends RuntimeException {

    public WeatherServiceException(String message){
        super(message);
    }
}
