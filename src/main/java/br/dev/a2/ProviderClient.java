package br.dev.a2;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;
@Path("api")
public interface ProviderClient {
    @GET
    @Path("ticket")
    List<TicketMapper> retrieveTicketAvailability();
}
