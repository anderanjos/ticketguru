package br.dev.a2.provider;

import br.dev.a2.Ticket;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@ApplicationScoped
public class ProviderClientService {

    @ConfigProperty(name = "external.company1.url")
    private String HOST_COMPANY_1;

    @ConfigProperty(name = "external.company2.url")
    private String HOST_COMPANY_2;


    public List<Ticket> retrieveTicketAvailabilityFromClient1() {
        ProviderClient companyProvider1 = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(HOST_COMPANY_1))
                .build(ProviderClient.class);

        return companyProvider1.retrieveTicketAvailability();
    }

    public List<Ticket> retrieveTicketAvailabilityFromClient2() {
        ProviderClient companyProvider2 = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(HOST_COMPANY_2))
                .build(ProviderClient.class);

        return companyProvider2.retrieveTicketAvailability();
    }

    public CompletableFuture<List<Ticket>> retrieveTicketAvailabilityFromClient1Async() {
        var future = new CompletableFuture<List<Ticket>>();

        ProviderClient companyProvider1 = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(HOST_COMPANY_1))
                .build(ProviderClient.class);

        companyProvider1.retrieveTicketAvailabilityAsync()
                .whenComplete((response, err) -> future.complete(response));

        return future;
    }

    public CompletableFuture<List<Ticket>> retrieveTicketAvailabilityFromClient2Async() {
        var future = new CompletableFuture<List<Ticket>>();

        ProviderClient companyProvider2 = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(HOST_COMPANY_2))
                .build(ProviderClient.class);

        companyProvider2.retrieveTicketAvailabilityAsync()
                .whenComplete((response, err) -> future.complete(response));

        return future;
    }

}
