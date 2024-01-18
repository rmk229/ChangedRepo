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

            // Фильтруем билеты только на маршруте Владивосток - Тель-Авив
            List<FlightTicket> vvoToTlvTickets = main.filterTickets(tickets, "VVO", "TLV");

            // Рассчитываем минимальное время перелета для каждого авиаперевозчика
            Map<String, Integer> minFlightTimes = main.calculateMinFlightTime(vvoToTlvTickets);
            System.out.println("Минимальное время перелета между Владивостоком и Тель-Авивом для каждого авиаперевозчика:");
            minFlightTimes.forEach((carrier, minFlightTime) ->
                    System.out.println(carrier + ": " + minFlightTime + " минут"));
            double priceDifference = main.calculatePriceDifference(vvoToTlvTickets);
            System.out.println("\nРазница между средней ценой и медианой для полетов между Владивостоком и Тель-Авивом: " + priceDifference);



        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}


