package ro.igpr.tickets.config;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;
import org.restexpress.RestExpress;
import ro.igpr.tickets.domain.CountiesEntity;
import ro.igpr.tickets.domain.TicketsEntity;

import java.util.Map;

public abstract class Relationships {
    public final static void define(RestExpress server) {
        final Map<String, String> routes = server.getRouteUrlsByName();

        HyperExpress.relationships()
                .forCollectionOf(TicketsEntity.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.TICKET_COLLECTION))
                .withQuery("limit={limit}")
                .withQuery("offset={offset}")
                .rel(RelTypes.NEXT, routes.get(Constants.Routes.TICKET_COLLECTION) + "?offset={nextOffset}")
                .withQuery("limit={limit}")
                .optional()
                .rel(RelTypes.PREV, routes.get(Constants.Routes.TICKET_COLLECTION) + "?offset={prevOffset}")
                .withQuery("limit={limit}")
                .optional()

                .forClass(TicketsEntity.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.SINGLE_TICKET))
                .rel(RelTypes.UP, routes.get(Constants.Routes.SINGLE_TICKET))


                .forCollectionOf(CountiesEntity.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.COUNTY_COLLECTION))
                .withQuery("limit={limit}")
                .withQuery("offset={offset}")
                .rel(RelTypes.NEXT, routes.get(Constants.Routes.COUNTY_COLLECTION) + "?offset={nextOffset}")
                .withQuery("limit={limit}")
                .optional()
                .rel(RelTypes.PREV, routes.get(Constants.Routes.COUNTY_COLLECTION) + "?offset={prevOffset}")
                .withQuery("limit={limit}")
                .optional()

                .forClass(CountiesEntity.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.SINGLE_COUNTY))
                .rel(RelTypes.UP, routes.get(Constants.Routes.SINGLE_COUNTY));
    }
}
