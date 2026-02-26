package com.weather_forcast.dto;

import java.time.LocalDateTime;

public class WeatherDto {
    private LocalDateTime dateTime;
    private Double temperature;
    private Double humidity;
    private Double pressure;
    private String weatherCondition;

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
    public Double getTemperature() {
        return temperature;
    }
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
    public Double getHumidity() {
        return humidity;
    }
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }
    public Double getPressure() {
        return pressure;
    }
    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }
    public String getWeatherCondition() {
        return weatherCondition;
    }
    public void setWeatherCondition(String weatherCondition) {
        this.weatherCondition = weatherCondition;
    }
}
