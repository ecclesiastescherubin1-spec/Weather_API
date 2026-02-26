package com.weather_forcast.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class WeatherCsvRepresentation {
    @CsvBindByName(column = "datetime_utc" )
    private LocalDateTime dateTime;
    @CsvBindByName(column = "_tempm" )
    private Double temperature;
    @CsvBindByName(column = "_hum" )
    private Double humidity;
    @CsvBindByName(column = "_pressure" )
    private Double pressure;
    @CsvBindByName(column = "_conds" )
    private String weatherCondition;

}
