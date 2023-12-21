package br.dev.a2;

import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.RequestScoped;

import java.net.URI;
import java.util.List;

@RequestScoped
public class ProviderClientService {


    private final ProviderClient companyProvider1;
    private final ProviderClient companyProvider2;


    public ProviderClientService() {
        this.companyProvider1 = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create("http://localhost:8081"))
                .build(ProviderClient.class);

        this.companyProvider2 = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create("http://localhost:8082"))
                .build(ProviderClient.class);

    }

    public List<TicketMapper> retrieveTicketAvailabilityFromClient1(){
        return companyProvider1.retrieveTicketAvailability();
    }

    public List<TicketMapper> retrieveTicketAvailabilityFromClient2(){
        return companyProvider2.retrieveTicketAvailability();
    }

}
