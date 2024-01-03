package br.dev.a2;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.swing.text.html.Option;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

@Path("/api")
public class TicketController {

    private final TicketService service;

    public TicketController(final TicketService service) {
        this.service = service;
    }


    @GET
    @Path("search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchTickets() {

        List<Ticket> tickets = service.retrieveTickets("");
        return Response
                .status(Response.Status.OK)
                .entity(tickets)
                .build();
    }

    @GET
    @Path("search-async")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchTicketsAsync() {

        List<Ticket> tickets = service.fetchTicketsAsync();
        return Response
                .status(Response.Status.OK)
                .entity(tickets)
                .build();
    }

    @GET
    @Path("search-vt")
    @Produces(MediaType.APPLICATION_JSON)
    @RunOnVirtualThread
    public Response searchTicketsConcurrently() {

        List<Ticket> tickets = service.retrieveTickets("concurrent");
        return Response
                .status(Response.Status.OK)
                .entity(tickets)
                .build();
    }

}
