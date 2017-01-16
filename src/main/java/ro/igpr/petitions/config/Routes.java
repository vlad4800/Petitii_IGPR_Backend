package ro.igpr.petitions.config;

import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;

/**
 * Define controller routes
 */
public abstract class Routes {

    public final static void define(Configuration config, RestExpress server) {

        // Counties
        server.uri(config.getApiVersionPath() + "/counties/{" + Constants.Url.PETITION_ID + "}", config.getCountiesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_COUNTY);

        server.uri(config.getApiVersionPath() + "/counties", config.getCountiesController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.COUNTY_COLLECTION);

        // Petitions
        server.uri(config.getApiVersionPath() + "/petitions/{" + Constants.Url.PETITION_ID + "}", config.getPetitionsController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_PETITION);

        server.uri(config.getApiVersionPath() + "/petitions", config.getPetitionsController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.PETITION_COLLECTION);


        server.uri(config.getApiVersionPath() + "/petitions/{" + Constants.Url.PETITION_ID + "}/messages/{" + Constants.Url.MESSAGE_ID + "}", config.getPetitionMessagesController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_MESSAGE);

        server.uri(config.getApiVersionPath() + "/petitions/{" + Constants.Url.PETITION_ID + "}/messages", config.getPetitionMessagesController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.MESSAGE_COLLECTION);
    }
}
