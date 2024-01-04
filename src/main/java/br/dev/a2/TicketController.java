package br.dev.a2;

import io.smallrye.common.annotation.RunOnVirtualThread;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

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


    @Operation(summary = "Fetch tickets using SEQUENTIAL platform threads strategy")
    @APIResponse(responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(
                            implementation = Ticket.class,
                            type = SchemaType.ARRAY)))
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
    @Operation(summary = "Fetch tickets using ASYNCHRONOUSLY using CompletableFutures and Thread Pool strategy")
    @APIResponse(responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(
                            implementation = Ticket.class,
                            type = SchemaType.ARRAY)))
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

    @Operation(summary = "Fetch tickets using SEQUENTIAL approach over Virtual Threads")
    @APIResponse(responseCode = "200",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(
                            implementation = Ticket.class,
                            type = SchemaType.ARRAY)))
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
