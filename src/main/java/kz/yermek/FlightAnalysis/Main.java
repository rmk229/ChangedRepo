package kz.yermek.FlightAnalysis;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.util.*;

public class Main {
    public void calculateMinFlightTime(JsonNode rootNode) {
        Map<String, Double> minFlightTimes = new HashMap<>();

        JsonNode flightsNode = rootNode.get("flights");
        if (flightsNode.isArray()) {
            for (JsonNode flight : flightsNode) {
                String airline = flight.get("airline").asText();
                String route = flight.get("route").asText();
                double flightTime = flight.get("flightTime").asDouble();

                if (route.equals("Vladivostok-TelAviv")) {
                    if (!minFlightTimes.containsKey(airline) || flightTime < minFlightTimes.get(airline)) {
                        minFlightTimes.put(airline, flightTime);
                    }
                }
            }

            System.out.print("Minimum time to flight between Vladivostok and TelAviv: ");
            System.out.println(findMinValue(minFlightTimes) + " hours");
        } else {
            System.out.println("Invalid JSON format. 'flights' array not found");
        }
    }

    private static double findMinValue(Map<String, Double> map) {
        double minValue = Double.POSITIVE_INFINITY;

        for (double value : map.values()) {
            if (value < minValue) {
                minValue = value;
            }
        }
        return minValue;
    }

    public void calculatePriceDifference(JsonNode rootNode) {
        List<Double> prices = new ArrayList<>();

        JsonNode flightsNode = rootNode.get("flights");
        if (flightsNode.isArray()) {
            for (JsonNode flight : flightsNode) {
                String route = flight.get("route").asText();
                double price = flight.get("price").asDouble();

                if (route.equals("Vladivostok-TelAviv")) {
                    prices.add(price);
                }
            }

            // Calculate the average price
            double averagePrice = calculateAverage(prices);

            // Calculate the median
            double medianPrice = calculateMedian(prices);

            //Show results
            System.out.println("Average price: " + averagePrice + " rubles");
            System.out.println("Median of the prices: " + medianPrice + " rubles");
            System.out.println("Difference between average price and median: " + (averagePrice - medianPrice) + " rubles");
        } else {
            System.out.println("Invalid JSON format. 'flights' array not found.");
        }
    }

    public double calculateAverage(List<Double> prices) {
        DescriptiveStatistics statistics = new DescriptiveStatistics();
        prices.forEach(statistics::addValue);
        return statistics.getMean();
    }

    public double calculateMedian(List<Double> prices) {
        Collections.sort(prices);
        int size = prices.size();
        if (size % 2 == 0) {
            int middle = size / 2;
            return (prices.get(middle - 1) + prices.get(middle)) / 2.0;
        } else {
            return prices.get(size / 2);
        }
    }
}
