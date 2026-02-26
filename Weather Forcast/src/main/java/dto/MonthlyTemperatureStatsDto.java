package com.weather_forcast.dto;

public class MonthlyTemperatureStatsDto {
    private Double high;
    private Double median;
    private Double minimum;

    public Double getHigh() {
        return high;
    }
    public void setHigh(Double high) {
        this.high = high;
    }
    public Double getMedian() {
        return median;
    }
    public void setMedian(Double median) {
        this.median = median;
    }
    public Double getMinimum() {
        return minimum;
    }
    public void setMinimum(Double minimum) {
        this.minimum = minimum;
    }
}
