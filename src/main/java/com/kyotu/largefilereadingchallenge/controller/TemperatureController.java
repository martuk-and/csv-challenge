package com.kyotu.largefilereadingchallenge.controller;

import com.kyotu.largefilereadingchallenge.models.YearlyAverageTemperature;
import com.kyotu.largefilereadingchallenge.service.TemperatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TemperatureController {
    private final TemperatureService temperatureService;

    @GetMapping("v1/temperatures")
    public ResponseEntity<List<YearlyAverageTemperature>> getYearlyAverageTemperatures(@RequestParam String city) {
        return ResponseEntity.ok(temperatureService.getYearlyAverageTemperatures(city));
    }
}
