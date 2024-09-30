package com.kyotu.largefilereadingchallenge.service;

import com.kyotu.largefilereadingchallenge.aggregator.AverageYearTemperatureAggregator;
import com.kyotu.largefilereadingchallenge.aggregator.TemperatureAggregator;
import com.kyotu.largefilereadingchallenge.models.YearlyAverageTemperature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class TemperatureServiceImpl implements TemperatureService {

    @Value("classpath:data/example_file.csv")
    private Resource csvResource;
    @Value("${csv.file.path}")
    private String filePath;
    private long cacheVersion = 0;
    private TemperatureAggregator aggregator;

    @Override
    public List<YearlyAverageTemperature> getYearlyAverageTemperatures(String city) {
        processCSVFile();
        return aggregator.getYearTemperatures(city);
    }

    private synchronized void processCSVFile() {
        try {
            File file = getFile();
            if (cacheVersion != file.lastModified() ) {
                cacheVersion = file.lastModified();
                try (
                        InputStream inputStream = new FileInputStream(file);
                        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))
                ) {
                    aggregator = new AverageYearTemperatureAggregator();
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] fields = line.split(";");
                        aggregator.consume(fields[0], fields[1].substring(0, 4), Float.parseFloat(fields[2]));
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File getFile() throws IOException {
        if (Files.exists(Paths.get(filePath))) {
            return Paths.get(filePath).toFile();
        } else {
            return csvResource.getFile();
        }
    }
}
