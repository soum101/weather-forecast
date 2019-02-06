package com.finleap.assignment.weatherforecast.validator;

import com.finleap.assignment.weatherforecast.exception.WeatherServiceException;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;
import java.util.regex.Pattern;

@Component
public class CityNameValidator {

    private final Pattern CITY_VALID_PATTERN = Pattern.compile("^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$");

    public final Predicate<String> CITY_PREDICATE = str -> CITY_VALID_PATTERN.matcher(str).find();
    public final Predicate<String> CITY_LENGTH_PREDICATE = str -> str.length() > 0 && str.length() <= 60;

    public boolean validate(String cityName){
        if( !CITY_PREDICATE
                .and(CITY_LENGTH_PREDICATE)
                .test(cityName)) {
            throw new WeatherServiceException("Invalid City Name: " + cityName);
        }
        return true;
    }
}
