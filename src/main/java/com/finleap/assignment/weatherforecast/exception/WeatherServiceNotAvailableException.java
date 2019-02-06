package com.finleap.assignment.weatherforecast.exception;

public class WeatherServiceNotAvailableException extends RuntimeException {

    public WeatherServiceNotAvailableException(Exception e) {
        super(e);
    }
}
