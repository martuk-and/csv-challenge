package com.kyotu.largefilereadingchallenge.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class YearlyAverageTemperature {
    private String year;
    private float temperature;
}
