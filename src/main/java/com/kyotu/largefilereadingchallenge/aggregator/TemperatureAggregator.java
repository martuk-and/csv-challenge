package com.kyotu.largefilereadingchallenge.aggregator;

import com.kyotu.largefilereadingchallenge.models.YearlyAverageTemperature;

import java.util.List;

public interface TemperatureAggregator {
    void consume(String city, String year, Float temperature);
    List<YearlyAverageTemperature> getYearTemperatures(String city);
}
