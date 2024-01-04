package br.dev.a2.docs;

import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        tags = {
                @Tag(name = "", description = "Simple api to test Java Virtual Threads."),
        },
        info = @Info(
                title = "Ticketguru",
                version = "latest",
                contact = @Contact(
                        name = "Check it out others project from anderanjos",
                        url = "https://github.com/anderanjos")),
        servers = {
                @Server(url = "http://localhost:8080")
        })
public class Documentation extends Application {
}
