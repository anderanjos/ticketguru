package br.dev.a2;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Path("/api")
public class TicketController {

    private final TicketRepository repo;
    private final TicketService service;

    public TicketController(final TicketRepository repository, final TicketService service) {
        this.repo = repository;
        this.service = service;
    }


    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchTickets() {
        List<Ticket> tickets = service.retrieveTicketAvailability();
        return Response
                .status(Response.Status.OK)
                .entity(tickets)
                .build();
    }

    @GET
    @Path("search-vt")
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response searchTicketsVT() {
        List<Ticket> tickets = service.retrieveTicketAvailability();
        return Response
                .status(Response.Status.OK)
                .entity(tickets)
                .build();
    }

}
