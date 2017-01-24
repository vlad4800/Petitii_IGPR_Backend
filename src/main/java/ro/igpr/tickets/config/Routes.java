package ro.igpr.tickets.config;

import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;

/**
 * Define controller routes
 */
public abstract class Routes {

    public final static void define(Configuration config, RestExpress server) {

        // Auth
        server.uri("/" + Constants.Routes.AUTH_USERS, config.getAuthController())
                .action(Constants.Routes.Actions.USERS, HttpMethod.GET)
                .name(Constants.Routes.AUTH_USERS_SINGLE_ROUTE);

        server.uri("/" + Constants.Routes.AUTH_DEVICES, config.getAuthController())
                .action(Constants.Routes.Actions.DEVICES, HttpMethod.GET)
                .name(Constants.Routes.AUTH_DEVICES_SINGLE_ROUTE);

        // Counties
        server.uri("/" + Constants.Routes.ROUTE_COUNTIES, config.getCountiesController())
                .action(Constants.Routes.Actions.READ_ALL, HttpMethod.GET)
                .name(Constants.Routes.COUNTY_COLLECTION);

        // Tickets
        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}", config.getTicketsController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_TICKET);

        server.uri("/" + Constants.Routes.ROUTE_TICKETS, config.getTicketsController())
                .action(Constants.Routes.Actions.READ_ALL, HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.TICKET_COLLECTION);

        // Tickets Messages
        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}/" + Constants.Routes.ROUTE_MESSAGES + "/{" + Constants.Url.MESSAGE_ID + "}", config.getTicketMessagesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_MESSAGE);

        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}/" + Constants.Routes.ROUTE_MESSAGES, config.getTicketMessagesController())
                .action(Constants.Routes.Actions.READ_ALL, HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.MESSAGE_COLLECTION);

        // Tickets Attachments
        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}/" + Constants.Routes.ROUTE_ATTACHMENTS + "/{" + Constants.Url.ATTACHMENT_ID + "}", config.getTicketAttachmentsController())
                .method(HttpMethod.GET, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_ATTACHMENT);

        server.uri("/" + Constants.Routes.ROUTE_TICKETS + "/{" + Constants.Url.TICKET_ID + "}/" + Constants.Routes.ROUTE_ATTACHMENTS, config.getTicketAttachmentsController())
                .action(Constants.Routes.Actions.READ_ALL, HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.ATTACHMENT_COLLECTION);

        // Users
        server.uri("/" + Constants.Routes.ROUTE_USERS + "/{" + Constants.Url.USER_ID + "}", config.getUsersController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_USER);

        server.uri("/" + Constants.Routes.ROUTE_USERS, config.getUsersController())
                .action(Constants.Routes.Actions.READ_ALL, HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.USER_COLLECTION);

        server.uri("/" + Constants.Routes.ROUTE_USERS + "/{" + Constants.Url.USER_ID + "}" + "/" + Constants.Routes.ROUTE_TICKETS, config.getTicketsController())
                .action(Constants.Routes.Actions.READ_ALL, HttpMethod.GET)
                .name(Constants.Routes.TICKET_COLLECTION);

    }
}
