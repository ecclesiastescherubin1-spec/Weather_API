package com.weather_forcast.repository;

import com.weather_forcast.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface WeatherRepository extends JpaRepository<Weather,Long> {
    List<Weather> findByDateTimeBetween(
            LocalDateTime start,
            LocalDateTime end
    );

}
