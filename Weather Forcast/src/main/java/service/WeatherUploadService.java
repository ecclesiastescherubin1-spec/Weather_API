package com.weather_forcast.service;

import com.weather_forcast.model.Weather;
import org.springframework.stereotype.Service;
import com.weather_forcast.repository.WeatherRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherUploadService {
    private static final DateTimeFormatter CSV_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HH:mm");
    private final WeatherRepository repository;
    public WeatherUploadService(WeatherRepository repository)
    {
        this.repository=repository;
    }
    public void upload(java.io.InputStream inputStream) throws Exception
    {
        BufferedReader br= new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        String headerLine = br.readLine();
        if (headerLine == null) {
            return;
        }
        String[] header = headerLine.split(",");
        Map<String, Integer> idx = buildIndexMap(header);
        List<Weather> list= new ArrayList<>();
        while((line=br.readLine()) !=null)
        {
            String[] data= line.split(",");
            if (data.length <= getRequiredMaxIndex(idx)) {
                continue;
            }

            Weather weather=new Weather();
            weather.setDateTime(LocalDateTime.parse(data[idx.get("datetime_utc")].trim(), CSV_DATE_FORMAT));
            weather.setTemperature(parse(data[idx.get("_tempm")]));
            weather.setHumidity(parse(data[idx.get("_hum")]));
            weather.setPressure(parse(data[idx.get("_pressurem")]));
            weather.setWeatherCondition(data[idx.get("_conds")].trim());
            list.add(weather);
        }
        repository.saveAll(list);
    }

    private Map<String, Integer> buildIndexMap(String[] header) {
        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < header.length; i++) {
            indexMap.put(header[i].trim(), i);
        }
        requireColumn(indexMap, "datetime_utc");
        requireColumn(indexMap, "_tempm");
        requireColumn(indexMap, "_hum");
        requireColumn(indexMap, "_pressurem");
        requireColumn(indexMap, "_conds");
        return indexMap;
    }

    private void requireColumn(Map<String, Integer> indexMap, String column) {
        if (!indexMap.containsKey(column)) {
            throw new IllegalArgumentException("Missing required CSV column: " + column);
        }
    }

    private int getRequiredMaxIndex(Map<String, Integer> idx) {
        int max = idx.get("datetime_utc");
        max = Math.max(max, idx.get("_tempm"));
        max = Math.max(max, idx.get("_hum"));
        max = Math.max(max, idx.get("_pressurem"));
        max = Math.max(max, idx.get("_conds"));
        return max;
    }

    private Double parse(String value)
    {
        try{
            if (value==null||value.isEmpty()||value.trim().equals("-9999"))
                return null;
            return Double.parseDouble(value.trim());
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
