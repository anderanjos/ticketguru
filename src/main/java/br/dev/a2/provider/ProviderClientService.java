package br.dev.a2.provider;

import br.dev.a2.Ticket;
import io.quarkus.rest.client.reactive.QuarkusRestClientBuilder;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;
import java.util.List;

@ApplicationScoped
public class ProviderClientService {

    @ConfigProperty(name ="external.company1.url")
    private String HOST_COMPANY_1;

    @ConfigProperty(name ="external.company2.url")
    private String HOST_COMPANY_2;


    public List<Ticket> retrieveTicketAvailabilityFromClient1(){
//        System.out.printf("Company 1: %s \n", Thread.currentThread());
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        ProviderClient companyProvider1 = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(HOST_COMPANY_1))
                .build(ProviderClient.class);
        return companyProvider1.retrieveTicketAvailability();
    }

    public List<Ticket> retrieveTicketAvailabilityFromClient2(){
//        System.out.printf("Company 2: %s\n", Thread.currentThread());
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        ProviderClient companyProvider2 = QuarkusRestClientBuilder.newBuilder()
                .baseUri(URI.create(HOST_COMPANY_2))
                .build(ProviderClient.class);
        return companyProvider2.retrieveTicketAvailability();
    }

}
