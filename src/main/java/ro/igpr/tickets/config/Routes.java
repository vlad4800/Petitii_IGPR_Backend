package ro.igpr.tickets.config;

import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;

/**
 * Define controller routes
 */
public abstract class Routes {

    public final static void define(Configuration config, RestExpress server) {

        // Counties
        server.uri("/" + Constants.Routes.ROUTE_COUNTIES, config.getCountiesController())
                .action("readAll", HttpMethod.GET)
                .name(Constants.Routes.COUNTY_COLLECTION);

        // Tickets
        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}", config.getTicketsController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_TICKET);

        server.uri("/" + Constants.Routes.ROUTE_TICKETS, config.getTicketsController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.TICKET_COLLECTION);

        // Messages
        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}/" + Constants.Routes.ROUTE_MESSAGES + "/{" + Constants.Url.MESSAGE_ID + "}", config.getTicketMessagesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_MESSAGE);

        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}/" + Constants.Routes.ROUTE_MESSAGES, config.getTicketMessagesController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.MESSAGE_COLLECTION);

        // Attachments
        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}/" + Constants.Routes.ROUTE_ATTACHMENTS + "/{" + Constants.Url.ATTACHMENT_ID + "}", config.getTicketAttachmentsController())
                .method(HttpMethod.GET, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_ATTACHMENT);

        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}/" + Constants.Routes.ROUTE_ATTACHMENTS, config.getTicketAttachmentsController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.ATTACHMENT_COLLECTION);

        // Users
        server.uri("/" + Constants.Routes.ROUTE_USERS + "/{" + Constants.Url.USER_ID + "}", config.getUsersController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_USER);

        server.uri("/" + Constants.Routes.ROUTE_USERS, config.getUsersController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.USER_COLLECTION);

        server.uri("/" + Constants.Routes.ROUTE_USERS + "/{" + Constants.Url.USER_ID + "}" + "/" + Constants.Routes.ROUTE_TICKETS, config.getTicketsController())
                .action("readAll", HttpMethod.GET)
                .name(Constants.Routes.TICKET_COLLECTION);
    }
}
