package com.finleap.assignment.weatherforecast.service.impl;

import com.finleap.assignment.weatherforecast.model.Forecast;
import com.finleap.assignment.weatherforecast.model.Record;
import com.finleap.assignment.weatherforecast.rest.OpenWeatherMapService;
import com.finleap.assignment.weatherforecast.rest.WeatherForecastResponse;
import com.finleap.assignment.weatherforecast.service.AverageWeatherForecastService;
import com.finleap.assignment.weatherforecast.validator.CityNameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Service
public class AverageWeatherForecastServiceImpl implements AverageWeatherForecastService {

    @Autowired
    private OpenWeatherMapService openWeatherMapService;

    @Autowired
    private CityNameValidator cityNameValidator;

    private final LocalTime DAY_START = LocalTime.of(5, 59, 59);
    private final LocalTime DAY_STOP = LocalTime.of(18, 0, 0);


    @Override
    public WeatherForecastResponse getAvgData(String cityName) {

        cityNameValidator.validate(cityName);

        Forecast data = openWeatherMapService.getData(cityName);
        Map<Boolean, List<Record>> dayAndNightData = data.getRecords()
                .stream()
                .filter(record -> isValidDateRange(record.getDt()))
                .collect(Collectors.partitioningBy(t -> isDayTime(t.getDt()))
                );
        OptionalDouble dayAverageTemp = getAverage(dayAndNightData.get(true));
        OptionalDouble nightAverageTemp = getAverage(dayAndNightData.get(false));
        OptionalDouble pressureAverage = data.getRecords()
                .stream()
                .mapToDouble(s -> s.getMain().getPressure())
                .average();

        return new WeatherForecastResponse(dayAverageTemp.orElse(Double.NaN)
                , nightAverageTemp.orElse(Double.NaN), pressureAverage.orElse(Double.NaN));

    }

    protected OptionalDouble getAverage(List<Record> data) {
        return data.stream()
                .map(s -> s.getMain())
                .mapToDouble(s -> (s.getTemp() + s.getTempMin() + s.getTempMax()) / 3)
                .average();
    }

    protected boolean isDayTime(long timestamp) {

        LocalTime time = getLocalDateTime(timestamp).toLocalTime();
        return time.isAfter(DAY_START) && time.isBefore(DAY_STOP);
    }

    protected LocalDateTime getLocalDateTime(long timestamp) {

        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp),
                TimeZone.getDefault().toZoneId());
    }

    protected boolean isValidDateRange(long timestamp) {
        LocalDateTime time = getLocalDateTime(timestamp);
        return time.isBefore(LocalDateTime.now().plusDays(3));
    }
}
