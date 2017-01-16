package ro.igpr.petitions.config;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;
import org.restexpress.RestExpress;
import ro.igpr.petitions.domain.PetitionsEntity;

import java.util.Map;

public abstract class Relationships {
    public final static void define(RestExpress server) {
        final Map<String, String> routes = server.getRouteUrlsByName();

        HyperExpress.relationships()
                .forCollectionOf(PetitionsEntity.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.PETITION_COLLECTION))
                .withQuery("limit={limit}")
                .withQuery("offset={offset}")
                .rel(RelTypes.NEXT, routes.get(Constants.Routes.PETITION_COLLECTION) + "?offset={nextOffset}")
                .withQuery("limit={limit}")
                .optional()
                .rel(RelTypes.PREV, routes.get(Constants.Routes.PETITION_COLLECTION) + "?offset={prevOffset}")
                .withQuery("limit={limit}")
                .optional()

                .forClass(PetitionsEntity.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.SINGLE_PETITION))
                .rel(RelTypes.UP, routes.get(Constants.Routes.SINGLE_PETITION));
    }
}
