package br.dev.a2.provider;

import br.dev.a2.Ticket;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Path("api")
public interface ProviderClient {
    @GET
    @Path("ticket")
    List<Ticket> retrieveTicketAvailability();

    @GET
    @Path("ticket")
    CompletionStage<List<Ticket>> retrieveTicketAvailabilityAsync();

}
