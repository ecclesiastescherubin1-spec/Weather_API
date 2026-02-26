package com.weather_forcast.service;

import com.weather_forcast.dto.MonthlyTemperatureStatsDto;
import com.weather_forcast.dto.WeatherDto;
import com.weather_forcast.model.Weather;
import org.springframework.stereotype.Service;
import com.weather_forcast.repository.WeatherRepository;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Service
public class WeatherQueryService {

    private final WeatherRepository repository;

    public WeatherQueryService(WeatherRepository repository) {
        this.repository = repository;
    }

    public List<WeatherDto> getByDateAcrossYears(String date) {
        String[] parts = date.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Date must be in MM-dd format, for example 11-01");
        }

        int month;
        int day;
        try {
            month = Integer.parseInt(parts[0]);
            day = Integer.parseInt(parts[1]);
            LocalDate.of(2000, month, day);
        } catch (NumberFormatException | DateTimeException ex) {
            throw new IllegalArgumentException("Invalid date. Use MM-dd, for example 11-01");
        }

        List<Weather> allData = repository.findAll();
        List<WeatherDto> result = new ArrayList<>();
        for (Weather weather : allData) {
            if (weather.getDateTime() != null
                    && weather.getDateTime().getMonthValue() == month
                    && weather.getDateTime().getDayOfMonth() == day) {
                result.add(toDto(weather));
            }
        }
        return result;
    }

    public List<WeatherDto> getByMonthAcrossYears(int month) {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        List<Weather> allData = repository.findAll();
        List<WeatherDto> result = new ArrayList<>();
        for (Weather weather : allData) {
            if (weather.getDateTime() != null
                    && weather.getDateTime().getMonthValue() == month) {
                result.add(toDto(weather));
            }
        }
        return result;
    }

    public Map<String, MonthlyTemperatureStatsDto> getYearlyMonthlyTemperatureStats(int year) {
        Map<String, MonthlyTemperatureStatsDto> result = new LinkedHashMap<>();

        for (int month = 1; month <= 12; month++) {
            LocalDateTime start = LocalDate.of(year, month, 1).atStartOfDay();
            LocalDateTime end = start.plusMonths(1);

            List<Weather> monthData = repository.findByDateTimeBetween(start, end);
            List<Double> temperatures = new ArrayList<>();
            for (Weather weather : monthData) {
                if (weather.getTemperature() != null) {
                    temperatures.add(weather.getTemperature());
                }
            }

            Collections.sort(temperatures);

            MonthlyTemperatureStatsDto stats = new MonthlyTemperatureStatsDto();
            if (temperatures.isEmpty()) {
                stats.setHigh(null);
                stats.setMedian(null);
                stats.setMinimum(null);
            } else {
                double high = temperatures.get(temperatures.size() - 1);
                double minimum = temperatures.get(0);

                double median;
                int size = temperatures.size();
                int middle = size / 2;
                if (size % 2 == 0) {
                    median = (temperatures.get(middle - 1) + temperatures.get(middle)) / 2.0;
                } else {
                    median = temperatures.get(middle);
                }

                stats.setHigh(high);
                stats.setMedian(median);
                stats.setMinimum(minimum);
            }

            result.put(String.format("%02d", month), stats);
        }

        return result;
    }

    private WeatherDto toDto(Weather weather) {
        WeatherDto dto = new WeatherDto();
        dto.setDateTime(weather.getDateTime());
        dto.setTemperature(weather.getTemperature());
        dto.setHumidity(weather.getHumidity());
        dto.setPressure(weather.getPressure());
        dto.setWeatherCondition(weather.getWeatherCondition());
        return dto;
    }
}
