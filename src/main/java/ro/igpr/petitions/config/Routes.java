package ro.igpr.petitions.config;

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


        // Petitions
        server.uri("/petitions/{" + Constants.Url.PETITION_ID + "}", config.getPetitionsController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_PETITION);

        server.uri("/petitions", config.getPetitionsController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.PETITION_COLLECTION);

        // Messages
        server.uri("/petitions/{" + Constants.Url.PETITION_ID + "}/messages/{" + Constants.Url.MESSAGE_ID + "}", config.getPetitionMessagesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_MESSAGE);

        server.uri("/petitions/{" + Constants.Url.PETITION_ID + "}/messages", config.getPetitionMessagesController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.MESSAGE_COLLECTION);

        // Attachments
        server.uri("/petitions/{" + Constants.Url.PETITION_ID + "}/attachments/{" + Constants.Url.ATTACHMENT_ID + "}", config.getPetitionMessagesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_ATTACHMENT);

        server.uri("/petitions/{" + Constants.Url.PETITION_ID + "}/attachments", config.getPetitionMessagesController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.ATTACHMENT_COLLECTION);


    }
}
