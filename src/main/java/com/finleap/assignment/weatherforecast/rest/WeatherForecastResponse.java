package com.finleap.assignment.weatherforecast.rest;

import java.io.Serializable;

public class WeatherForecastResponse implements Serializable {
    private static final long serialVersionUID = -6425018895874698790L;

    Double avgDayTemperature;
    Double avgNightTemperature;
    Double avgPressure;

    public WeatherForecastResponse(Double avgDayTemperature, Double avgNightTemperature, Double avgPressure) {
        this.avgDayTemperature = avgDayTemperature;
        this.avgNightTemperature = avgNightTemperature;
        this.avgPressure = avgPressure;
    }

    public Double getAvgDayTemperature() {
        return avgDayTemperature;
    }

    public void setAvgDayTemperature(Double avgDayTemperature) {
        this.avgDayTemperature = avgDayTemperature;
    }

    public Double getAvgNightTemperature() {
        return avgNightTemperature;
    }

    public void setAvgNightTemperature(Double avgNightTemperature) {
        this.avgNightTemperature = avgNightTemperature;
    }

    public Double getAvgPressure() {
        return avgPressure;
    }

    public void setAvgPressure(Double avgPressure) {
        this.avgPressure = avgPressure;
    }
}
