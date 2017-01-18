package ro.igpr.tickets;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.domain.hal.HalResourceFactory;
import com.strategicgains.repoexpress.exception.DuplicateItemException;
import com.strategicgains.repoexpress.exception.InvalidObjectIdException;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.strategicgains.restexpress.plugin.cache.CacheControlPlugin;
import com.strategicgains.restexpress.plugin.cors.CorsHeaderPlugin;
import com.strategicgains.restexpress.plugin.swagger.SwaggerPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restexpress.Flags;
import org.restexpress.RestExpress;
import org.restexpress.exception.BadRequestException;
import org.restexpress.exception.ConflictException;
import org.restexpress.exception.NotFoundException;
import org.restexpress.plugin.hyperexpress.HyperExpressPlugin;
import org.restexpress.plugin.hyperexpress.Linkable;
import org.restexpress.plugin.version.VersionPlugin;
import ro.igpr.tickets.config.Configuration;
import ro.igpr.tickets.config.Relationships;
import ro.igpr.tickets.config.Routes;
import ro.igpr.tickets.pipeline.ConsoleLogMessageObserver;
import ro.igpr.tickets.postprocessor.LastModifiedHeaderPostprocessor;
import ro.igpr.tickets.serialization.SerializationProvider;

import java.security.InvalidParameterException;
import java.text.ParseException;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;

/**
 * Created by vlad4800@gmail.com on 16-Jan-17.
 */
public class Server {

    public static final String CATALOG_NAME = "tickets";
    private static final String SERVICE_NAME = "Tickets";
    private static final Logger LOG = LogManager.getLogger(SERVICE_NAME);
    private Configuration config;
    private boolean isStarted = false;
    private RestExpress server;

    public Server(Configuration config) {
        this.config = config;

        RestExpress.setDefaultSerializationProvider(new SerializationProvider());
        this.server = new RestExpress()
                .setName(SERVICE_NAME)
                .setUseTcpNoDelay(true)
                .setBaseUrl(config.getBaseUrl())
                .setUseSystemOut(false)
                .setKeepAlive(config.isKeepAlive())
                .setMaxContentSize(10000000)
                .setIoThreadCount(config.getIoThreadPoolSize())
                .addMessageObserver(new ConsoleLogMessageObserver())
                .addPostprocessor(new LastModifiedHeaderPostprocessor())
                .setExecutorThreadCount(config.getExecutorThreadPoolSize());

        Routes.define(config, server);
        Relationships.define(server);
        configurePlugins(config, server);
        mapExceptions(server);
    }

    public Server start() {
        if (!isStarted) {
            server.bind(config.getPort());
            isStarted = true;
        }
        return this;
    }

    public void awaitShutdown() {
        if (isStarted) server.awaitShutdown();
    }

    public void shutdown() {
        if (isStarted) server.shutdown();
    }

    private final static void configurePlugins(Configuration config,
                                               RestExpress server) {
        new VersionPlugin(config.getApiVersion())         // Supply the version string here.
//                .path(config.getApiVersionPath())            // optional. Supply the version route URL here. Defaults to "/version"
//                .flag("version 1")          // optional. Set a flag on the request for this route.
//                .parameter("name", object)   // optional. Set a parameter on the request for this route.
                .register(server);

        new SwaggerPlugin("/api-docs")
                .swaggerVersion("1.11")
                .apiVersion(config.getApiVersion())
                .flag(Flags.Auth.PUBLIC_ROUTE)
                .register(server);

        new HyperExpressPlugin(Linkable.class)
                .register(server);

        HyperExpress.registerResourceFactoryStrategy(new HalResourceFactory(), "application/hal+json");
        HalResourceFactory halFactory = new HalResourceFactory();
//        HyperExpress.registerResourceFactoryStrategy(halFactory, "application/hal+json");
        HyperExpress.registerResourceFactoryStrategy(halFactory, "application/json");


        new CacheControlPlugin()
                .register(server);
        server.addPostprocessor(new LastModifiedHeaderPostprocessor());


        new CorsHeaderPlugin("*")
                .flag(Flags.Auth.PUBLIC_ROUTE)
                .allowHeaders(CONTENT_TYPE, ACCEPT, AUTHORIZATION, REFERER, LOCATION)
                .exposeHeaders(LOCATION)
                .register(server);
    }

    /**
     * Map exceptions
     *
     * @param server
     */
    private final static void mapExceptions(RestExpress server) {
        server
                .mapException(ItemNotFoundException.class, NotFoundException.class)
                .mapException(DuplicateItemException.class, ConflictException.class)
                .mapException(javax.validation.ValidationException.class, BadRequestException.class)
                .mapException(org.hibernate.QueryException.class, ConflictException.class)
                .mapException(InvalidObjectIdException.class, BadRequestException.class)
                .mapException(InvalidParameterException.class, BadRequestException.class)
                .mapException(ParseException.class, BadRequestException.class);
    }


    public final Configuration getConfig() {
        return this.config;
    }
}
