package br.dev.a2;

import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequestScoped
public class TicketService {

    private final ProviderClientService client;


    public TicketService(final ProviderClientService client) {
        this.client = client;
    }


    public List<Ticket> retrieveTicketAvailability() {

        // retrieve information from two different companies
        List<TicketMapper> ticketsFromCompany1 = client.retrieveTicketAvailabilityFromClient1();
        List<TicketMapper> ticketsFromCompany2 = client.retrieveTicketAvailabilityFromClient2();

        // join companies results
        ticketsFromCompany1.addAll(ticketsFromCompany2);


        // group by
        Map<String, List<BigDecimal>> priceByLocation = ticketsFromCompany1.stream()
                .collect(Collectors.groupingBy(TicketMapper::destination,
                        Collectors.mapping(TicketMapper::price, Collectors.toList())));

        List<Ticket> ticketList = priceByLocation.entrySet().stream()
                .map(item ->
                        new Ticket(item.getKey(),
                                item.getValue()
                                        .stream()
                                        .min(BigDecimal::compareTo)
                                        .orElse(null))
                )
                .toList();

        return ticketList;
    }


}
