package kz.yermek.FlightAnalysis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class FlightAnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightAnalysisApplication.class, args);

        String jsonFilePath = args[0];

        try {
            Main main = new Main();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));

            // Minimum flight time between Vladivostok and TelAviv for each carrier
            main.calculateMinFlightTime(rootNode);

            // Difference between average price and median price for a flight between Vladivostok and TelAviv
            main.calculatePriceDifference(rootNode);

        } catch (IOException e) {
            System.err.println("Error reading JSON file: " + e.getMessage());
        }
    }
}


