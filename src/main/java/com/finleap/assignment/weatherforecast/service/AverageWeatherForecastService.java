package com.finleap.assignment.weatherforecast.service;

import com.finleap.assignment.weatherforecast.rest.WeatherForecastResponse;

public interface AverageWeatherForecastService {

   WeatherForecastResponse getAvgData(String cityName);
}
