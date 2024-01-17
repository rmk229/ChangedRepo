package kz.yermek.FlightAnalysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FlightAnalysisApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlightAnalysisApplication.class, args);

        if (args.length != 1) {
            System.out.println("Usage: java FlightAnalyzerApplication tickets.json");
            System.exit(1);
        }

        String jsonFilePath = args[0];

        try {
            Main main = new Main();
            List<FlightTicket> tickets = main.readJsonFile(jsonFilePath);

            Map<String, Integer> minFlightTimes = main.calculateMinFlightTime(tickets);
            System.out.println("Minimum flight times between VVO and TLV for each carrier:");

            minFlightTimes.forEach((carrier, minFlightTime) ->
                    System.out.println(carrier + ": " + minFlightTime + " minutes"));

            double priceDifference = main.calculatePriceDifference(tickets);
            System.out.println("\nDifference between average price and median price for VVO to TLV flights: " + priceDifference);


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}


