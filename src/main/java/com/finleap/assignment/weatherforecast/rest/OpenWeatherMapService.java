package com.finleap.assignment.weatherforecast.rest;

import com.finleap.assignment.weatherforecast.model.Forecast;

public interface OpenWeatherMapService {

   Forecast getData(String city);
}
