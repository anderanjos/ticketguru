package br.dev.a2.provider;

import br.dev.a2.Ticket;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;
@Path("api")
public interface ProviderClient {
    @GET
    @Path("ticket")
    List<Ticket> retrieveTicketAvailability();
}
