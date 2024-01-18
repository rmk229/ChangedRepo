package kz.yermek.FlightAnalysis;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {
    public List<FlightTicket> readJsonFile(String jsonFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(new File(jsonFilePath));

        List<FlightTicket> tickets = new ArrayList<>();
        for (JsonNode ticketNode : rootNode.get("tickets")) {
            FlightTicket ticket = objectMapper.treeToValue(ticketNode, FlightTicket.class);
            tickets.add(ticket);
        }

        return tickets;
    }

    public Map<String, Integer> calculateMinFlightTime(List<FlightTicket> tickets) throws ParseException {
        Map<String, Integer> minFlightTime = new HashMap<>();

        for (FlightTicket ticket : tickets) {
            String key = ticket.getCarrier();
            int flightTime = calculateFlightTime(ticket);

            if (!minFlightTime.containsKey(key) || flightTime < minFlightTime.get(key)) {
                minFlightTime.put(key, flightTime);
            }
        }

        return minFlightTime;
    }

    public int calculateFlightTime(FlightTicket ticket) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy HH:mm", Locale.ENGLISH);
        Date departureDateTime = format.parse(ticket.getDeparture_date() + " " + ticket.getDeparture_time());
        Date arrivalDateTime = format.parse(ticket.getArrival_date() + " " + ticket.getArrival_time());

        long timeDifference = arrivalDateTime.getTime() - departureDateTime.getTime();
        return (int) (timeDifference / (60 * 1000)); // конвертим миллисекунды в минуты
    }


    public double calculatePriceDifference(List<FlightTicket> tickets) {
        double[] prices = tickets.stream().mapToDouble(FlightTicket::getPrice).toArray();
        Arrays.sort(prices);

        double averagePrice = Arrays.stream(prices).average().orElse(0);
        double medianPrice = (prices.length % 2 == 0) ?
                (prices[prices.length / 2 - 1] + prices[prices.length / 2]) / 2 :
                prices[prices.length / 2];

        return averagePrice - medianPrice;
    }

    public List<FlightTicket> filterTickets(List<FlightTicket> tickets, String origin, String destination) {
        List<FlightTicket> filteredTickets = new ArrayList<>();
        for (FlightTicket ticket : tickets) {
            if (ticket.getOrigin().equals(origin) && ticket.getDestination().equals(destination)) {
                filteredTickets.add(ticket);
            }
        }
        return filteredTickets;
    }
}
