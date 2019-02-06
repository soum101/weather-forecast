package com.finleap.assignment.weatherforecast.service.impl;

import com.finleap.assignment.weatherforecast.WeatherForecastApplication;
import com.finleap.assignment.weatherforecast.model.Forecast;
import com.finleap.assignment.weatherforecast.model.Main;
import com.finleap.assignment.weatherforecast.model.Record;
import com.finleap.assignment.weatherforecast.rest.OpenWeatherMapService;
import com.finleap.assignment.weatherforecast.rest.WeatherForecastResponse;
import com.finleap.assignment.weatherforecast.validator.CityNameValidator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class AverageWeatherForecastServiceImplTest {

    @Spy
    @InjectMocks
    private AverageWeatherForecastServiceImpl averageWeatherForecastService;

    @Mock
    private OpenWeatherMapService openWeatherMapService;

    @Mock
    private CityNameValidator cityNameValidator;

    @Mock
    private Forecast forecast;


    @Test
    public void whenValidCityNameInGetAvgDataThenExpectSuccess() {

        Record record = new Record();
        record.setDt(Instant.now().getEpochSecond());
        Main main = new Main();
        main.setPressure(900.00);
        main.setTemp(12.5);
        main.setTempMax(30.0);
        main.setTempMin(10.00);
        record.setMain(main);

        List<Record> recordList = Arrays.asList(record);
        when(cityNameValidator.validate(anyString())).thenReturn(true);
        when(openWeatherMapService.getData(anyString())).thenReturn(forecast);
        when(forecast.getRecords()).thenReturn(recordList);

        WeatherForecastResponse response = averageWeatherForecastService.getAvgData("pune");
        Assert.assertNotNull(response);
        Assert.assertTrue(response.getAvgDayTemperature().doubleValue() > 0);
        Assert.assertTrue(response.getAvgNightTemperature().isNaN());
        Assert.assertTrue(response.getAvgPressure().doubleValue() > 0);
    }


}
