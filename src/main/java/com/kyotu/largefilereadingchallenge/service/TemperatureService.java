package com.kyotu.largefilereadingchallenge.service;

import com.kyotu.largefilereadingchallenge.models.YearlyAverageTemperature;

import java.util.List;

public interface TemperatureService {
    List<YearlyAverageTemperature> getYearlyAverageTemperatures(String city);
}
