package br.dev.a2;

import br.dev.a2.provider.ProviderClientService;
import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.enterprise.context.RequestScoped;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@RequestScoped
public class TicketService {

    private final ProviderClientService client;
    private final ExecutorService executorService;

    public TicketService(final ProviderClientService client,
                         @VirtualThreads final ExecutorService executorService) {
        this.client = client;
        this.executorService = executorService;
    }


    public List<Ticket> retrieveTickets(final String mode) {
        List<Ticket> ticketsAvailable =
                Objects.equals(mode, "concurrent") ? fetchTicketsConcurrent() : fetchTicketsSequential();

        Map<String, List<BigDecimal>> priceByLocation = ticketsAvailable.stream()
                .collect(Collectors.groupingBy(Ticket::destination,
                        Collectors.mapping(Ticket::price, Collectors.toList())));

        return priceByLocation
                .entrySet()
                .stream()
                .map(item ->
                        new Ticket(item.getKey(),
                                item.getValue()
                                        .stream()
                                        .min(BigDecimal::compareTo)
                                        .orElse(null))
                )
                .toList();
    }


    /**
     * Fetch list os tickets, perform requests sequentially
     *
     * @return List<Ticket>
     */
    private List<Ticket> fetchTicketsSequential() {

        // retrieve information from two different companies
        List<Ticket> ticketsFromCompany1 = client.retrieveTicketAvailabilityFromClient1();
        List<Ticket> ticketsFromCompany2 = client.retrieveTicketAvailabilityFromClient2();

        // join companies results
        ticketsFromCompany1.addAll(ticketsFromCompany2);
        return ticketsFromCompany1;
    }


    /**
     * Fetch list os tickets, perform requests asynchronously using CompletableFutures
     *
     * @return List<Ticket>
     */
    public CompletableFuture<List<Ticket>> fetchTicketsAsync() {
        var future = new CompletableFuture<List<Ticket>>();

        List<CompletableFuture<List<Ticket>>> ticketAvailabilityFromAllCompanies = List.of(
                client.retrieveTicketAvailabilityFromClient1Async(),
                client.retrieveTicketAvailabilityFromClient2Async()
        );

        CompletableFuture
                .allOf(ticketAvailabilityFromAllCompanies.toArray(new CompletableFuture[0]))
                .thenApply(tickets -> ticketAvailabilityFromAllCompanies.stream()
                        .map(CompletableFuture::join)
                        .flatMap(List::stream)
                        .toList()
                )
                .whenComplete((response, err) -> {
                    var futureResponse = response.stream()
                            .collect(Collectors.groupingBy(Ticket::destination,
                                    Collectors.mapping(Ticket::price, Collectors.toList())))
                            .entrySet()
                            .stream()
                            .map(item ->
                                    new Ticket(item.getKey(),
                                            item.getValue()
                                                    .stream()
                                                    .min(BigDecimal::compareTo)
                                                    .orElse(null))
                            )
                            .toList();
                    future.complete(futureResponse);
                })
                .exceptionally(err -> {
                    future.completeExceptionally(err);
                    return null;
                });

        return future;
    }


    /**
     * Fetch list os tickets, perform requests asynchronously using Virtual Threads
     *
     * @return List<Ticket>
     */
    private List<Ticket> fetchTicketsConcurrent() {
        List<Future<List<Ticket>>> futures;

        try {
            futures = executorService
                    .invokeAll(List.of(client::retrieveTicketAvailabilityFromClient1, client::retrieveTicketAvailabilityFromClient2));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return futures.stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .toList();

    }
}
