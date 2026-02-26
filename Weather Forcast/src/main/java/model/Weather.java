package com.weather_forcast.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
@Table(name="weather_forecast")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateTime;
    private Double temperature;
    private Double humidity;
    private Double pressure;
    private String weatherCondition;

}
