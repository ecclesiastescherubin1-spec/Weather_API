package com.weather_forcast.controller;

import com.weather_forcast.dto.MonthlyTemperatureStatsDto;
import com.weather_forcast.dto.WeatherDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.weather_forcast.service.WeatherQueryService;
import com.weather_forcast.service.WeatherUploadService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/weather")
public class WeatherController {
    private final WeatherUploadService uploadService;
    private final WeatherQueryService queryService;

    public WeatherController(WeatherUploadService uploadService, WeatherQueryService queryService)
    {
        this.uploadService=uploadService;
        this.queryService=queryService;
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws Exception
    {
        uploadService.upload((file.getInputStream()));
        return "Weather data uploaded successfully";
    }
    @GetMapping("/by-date")
    public List<WeatherDto> getByDate(@RequestParam String date) {
        return queryService.getByDateAcrossYears(date);
    }

    @GetMapping("/by-month")
    public List<WeatherDto> getByMonth(@RequestParam int month) {
        return queryService.getByMonthAcrossYears(month);
    }

    @GetMapping("/yearly-temperature-stats")
    public Map<String, MonthlyTemperatureStatsDto> getYearlyStats(@RequestParam int year) {
        return queryService.getYearlyMonthlyTemperatureStats(year);
    }
}
