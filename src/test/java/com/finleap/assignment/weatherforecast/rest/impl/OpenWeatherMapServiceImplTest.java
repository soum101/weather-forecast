package com.finleap.assignment.weatherforecast.rest.impl;

import com.finleap.assignment.weatherforecast.exception.CityNotFoundException;
import com.finleap.assignment.weatherforecast.exception.WeatherServiceNotAvailableException;
import com.finleap.assignment.weatherforecast.model.Forecast;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
public class OpenWeatherMapServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @Spy
    @InjectMocks
    OpenWeatherMapServiceImpl openWeatherMapService;

    private String baseURL;

    private String baseURLWithAppId;

    private String appId ;

    @Before
    public void setup() {
        baseURL = "http://api.openweathermap.org/data/2.5/forecast";
        appId = "abcd1234";
        baseURLWithAppId = baseURL + "?appid=" + appId;
        Mockito.doReturn(baseURL).when(openWeatherMapService).getBaseURL();
        Mockito.doReturn(appId).when(openWeatherMapService).getAppId();
        MockitoAnnotations.initMocks(openWeatherMapService);
    }

    @Test
    public void getDataWhenValidCityNameIsProvided() {

        String validCityName = "pune";
        Forecast forecast = Mockito.mock(Forecast.class);
        ResponseEntity<Forecast> responseEntity = Mockito.mock(ResponseEntity.class);
        Mockito.when(responseEntity.getBody()).thenReturn(forecast);

        Mockito.when(restTemplate.exchange(getFullURL(validCityName), HttpMethod.GET,getHttpEntity(),Forecast.class)).thenReturn(responseEntity);

        Forecast forecastResponse = openWeatherMapService.getData(validCityName);
        Assert.assertEquals(forecast,forecastResponse);

    }

    public String getFullURL(String city){
        return  baseURLWithAppId + "&q=" + city + "&units=metric";
    }

    public HttpEntity getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity entity = new HttpEntity(headers);
        return  entity;
    }

    @Test(expected = CityNotFoundException.class)
    public void getDataWhenNonExistentCityNameIsProvided() {

        String validCityName = "puneeee";
        Forecast forecast = Mockito.mock(Forecast.class);
        ResponseEntity<Forecast> responseEntity = Mockito.mock(ResponseEntity.class);
        Mockito.when(responseEntity.getBody()).thenReturn(forecast);

        Mockito.when(restTemplate.exchange(getFullURL(validCityName), HttpMethod.GET,getHttpEntity(),Forecast.class)).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        openWeatherMapService.getData(validCityName);
    }

    @Test(expected = WeatherServiceNotAvailableException.class)
    public void getDataWhenWeatherServiceIsNotAvailable() {

        String validCityName = "pune";
        Forecast forecast = Mockito.mock(Forecast.class);
        ResponseEntity<Forecast> responseEntity = Mockito.mock(ResponseEntity.class);
        Mockito.when(responseEntity.getBody()).thenReturn(forecast);

        Mockito.when(restTemplate.exchange(getFullURL(validCityName), HttpMethod.GET,getHttpEntity(),Forecast.class)).thenThrow(new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR));

        openWeatherMapService.getData(validCityName);
    }

}
