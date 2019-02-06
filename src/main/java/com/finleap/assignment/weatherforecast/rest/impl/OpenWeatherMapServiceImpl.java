package com.finleap.assignment.weatherforecast.rest.impl;

import com.finleap.assignment.weatherforecast.exception.CityNotFoundException;
import com.finleap.assignment.weatherforecast.exception.WeatherServiceNotAvailableException;
import com.finleap.assignment.weatherforecast.model.Forecast;
import com.finleap.assignment.weatherforecast.rest.OpenWeatherMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class OpenWeatherMapServiceImpl implements OpenWeatherMapService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openweathermap.base.url}")
    private String baseURL;

    @Value("${openweathermap.app.id}")
    private String appId;

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public String getAppId() {
        return appId;
    }

    @Override
    public Forecast getData(String city) {

        UriComponentsBuilder builder = null;
        try {
            builder = UriComponentsBuilder.fromHttpUrl(getBaseURL())
                    .queryParam("appid", getAppId())
                    .queryParam("q", URLEncoder.encode(city, "UTF-8"))
                    .queryParam("units", "metric");
        } catch (UnsupportedEncodingException e) {
           throw new WeatherServiceNotAvailableException(e);
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/json");
            HttpEntity entity = new HttpEntity(headers);
            ResponseEntity<Forecast> response = getRestTemplate().exchange(builder.toUriString(), HttpMethod.GET, entity, Forecast.class);
            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc ) {
            if(HttpStatus.NOT_FOUND.equals(httpClientOrServerExc.getStatusCode())) {
                throw new CityNotFoundException("City not found");
            }
            throw new WeatherServiceNotAvailableException(httpClientOrServerExc);
        }

    }
}
