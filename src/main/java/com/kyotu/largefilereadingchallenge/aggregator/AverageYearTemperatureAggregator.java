package com.kyotu.largefilereadingchallenge.aggregator;

import com.kyotu.largefilereadingchallenge.models.YearlyAverageTemperature;

import java.util.*;

public class AverageYearTemperatureAggregator implements TemperatureAggregator {

    private Map<String, Map<String, TemperatureCounter>> result = new HashMap<>();

    @Override
    public void consume(String city, String year, Float temperature) {
        var countedTemperatureByYears = result.computeIfAbsent(city, city1 -> new TreeMap<>());
        var countedTemperature = countedTemperatureByYears.computeIfAbsent(year, year1 -> new TemperatureCounter());
        countedTemperature.add(temperature);
    }

    @Override
    public List<YearlyAverageTemperature> getYearTemperatures(String city) {
        if (!result.containsKey(city)) {
            return List.of();
        } else {
            var countedTemperatureByYears = result.get(city);
            List<YearlyAverageTemperature> yearlyAverageTemperatures = new ArrayList<>(countedTemperatureByYears.size());
            countedTemperatureByYears.forEach((year, temperature) -> {
                yearlyAverageTemperatures.add(new YearlyAverageTemperature(year, Math.round(10 * temperature.sum / temperature.count) * 0.1f));
            });
            return yearlyAverageTemperatures;
        }
    }

    private class TemperatureCounter {
        private float sum = 0;
        private int count = 0;

        void add(float temperature) {
            sum += temperature;
            count++;
        }
    }
}
