package ro.igpr.tickets.config;

import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;

/**
 * Define controller routes
 */
public abstract class Routes {

    public final static void define(Configuration config, RestExpress server) {

        // Counties
        server.uri("/counties/{" + Constants.Url.COUNTY_ID + "}", config.getCountiesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_COUNTY);

        server.uri("/counties", config.getCountiesController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.COUNTY_COLLECTION);


        // Tickets
        server.uri("/tickets/{" + Constants.Url.TICKET_ID + "}", config.getTicketsController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_TICKET);

        server.uri("/tickets", config.getTicketsController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.TICKET_COLLECTION);

        // Messages
        server.uri("/tickets/{" + Constants.Url.TICKET_ID + "}/messages/{" + Constants.Url.MESSAGE_ID + "}", config.getTicketMessagesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_MESSAGE);

        server.uri("/tickets/{" + Constants.Url.TICKET_ID + "}/messages", config.getTicketMessagesController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.MESSAGE_COLLECTION);

        // Attachments
        server.uri("/tickets/{" + Constants.Url.TICKET_ID + "}/attachments/{" + Constants.Url.ATTACHMENT_ID + "}", config.getTicketMessagesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_ATTACHMENT);

        server.uri("/tickets/{" + Constants.Url.TICKET_ID + "}/attachments", config.getTicketMessagesController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.ATTACHMENT_COLLECTION);


    }
}
