package com.finleap.assignment.weatherforecast;

import com.finleap.assignment.weatherforecast.exception.CityNotFoundException;
import com.finleap.assignment.weatherforecast.exception.WeatherServiceException;
import com.finleap.assignment.weatherforecast.exception.WeatherServiceNotAvailableException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WeatherServiceExceptionHandler  extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ CityNotFoundException.class })
    public ResponseEntity<String> handleCityNotFoundException(CityNotFoundException exception) {
       logger.error(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({ WeatherServiceNotAvailableException.class })
    public ResponseEntity<String> handleWeatherServiceNotAvailableException(WeatherServiceNotAvailableException exception) {
        logger.error(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler({ WeatherServiceException.class })
    public ResponseEntity<String> handleWeatherServiceException(WeatherServiceException exception) {
        logger.error(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ Exception.class })
    public ResponseEntity<String> handleAllExceptions(Exception exception) {
        logger.error(exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
