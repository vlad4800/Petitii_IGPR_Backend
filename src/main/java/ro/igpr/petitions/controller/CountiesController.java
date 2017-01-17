package ro.igpr.petitions.controller;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.wordnik.swagger.annotations.*;
import io.netty.handler.codec.http.HttpMethod;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;
import ro.igpr.petitions.config.Constants;
import ro.igpr.petitions.domain.CountiesEntity;

import java.util.Date;
import java.util.List;

public final class CountiesController extends BaseController {

    public CountiesController() {
        super();
    }

    /**
     * Creates a new county
     *
     * @param request
     * @param response
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = CountiesEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })

    @ApiOperation(value = "Create a new county.",
            notes = "Create a new county.",
            response = CountiesEntity.class,
            position = 0)

    @ApiImplicitParams({

            @ApiImplicitParam(name = "petitionInfo", required = true, value = "The county details", paramType = "body",
                    dataType = "CountiesEntity"
            ),
    })
    public final CountiesEntity create(final Request request, final Response response) {

        super.create(request, response);

        final CountiesEntity entity = request.getBodyAs(CountiesEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        dao.save(entity);


        // Bind the resource with link URL tokens, etc. here...
        final TokenResolver resolver = HyperExpress.bind(Constants.Url.COUNTY_ID, entity.getId().toString());

        // Include the Location header...
        final String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_COUNTY);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

        response.setResponseCreated();
        return entity;
    }

    /**
     * Finds a county by id and outputs the county task
     *
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({

    })
    public final CountiesEntity read(final Request request, final Response response) {

        super.read(request, response);

        final Integer petitionId = Integer.valueOf(request.getHeader(Constants.Url.COUNTY_ID, Constants.Messages.NO_COUNTY_ID));

        final CountiesEntity county = dao.get(CountiesEntity.class, petitionId);
        if (county == null) {
            throw new ItemNotFoundException(Constants.Messages.COUNTY_NOT_FOUND);
        }

        HyperExpress.bind(Constants.Url.COUNTY_ID, county.getId().toString());

        return county;
    }

    /**
     * Lists all counties
     *
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({
    })
    public final List<CountiesEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);

        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 100);

        final List<CountiesEntity> counties = dao.getAll(CountiesEntity.class, Order.asc("id"));

        HyperExpress.tokenBinder(new TokenBinder<CountiesEntity>() {
            @Override
            public void bind(CountiesEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.COUNTY_ID, entity.getId().toString());
            }
        });

        response.setCollectionResponse(range, counties.size(), counties.size());
        return counties;
    }

    /**
     * Updates county details
     *
     * @param request
     * @param response
     */
    @ApiImplicitParams({

    })
    public final void update(final Request request, final Response response) {
        super.update(request, response);

        final Integer petitionId = Integer.valueOf(request.getHeader(Constants.Url.COUNTY_ID, Constants.Messages.NO_COUNTY_ID));
        final CountiesEntity county = request.getBodyAs(CountiesEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);
        if (county == null) {
            throw new ItemNotFoundException(Constants.Messages.COUNTY_NOT_FOUND);
        }

        final Object result = dao.mergeFromEntities(county, petitionId, Constants.Messages.COUNTY_NOT_FOUND);

        if (result == null) {
            throw new HibernateException("Update failed!");
        }

        response.setResponseNoContent();
    }

    /**
     * Deletes a county
     *
     * @param request
     * @param response
     */
    @ApiImplicitParams({

    })
    public final void delete(final Request request, final Response response) {
        super.delete(request, response);

        final Integer petitionId = Integer.valueOf(request.getHeader(Constants.Url.COUNTY_ID, Constants.Messages.NO_COUNTY_ID));
        final CountiesEntity entity = dao.get(CountiesEntity.class, petitionId);

        if (entity == null) {
            throw new ItemNotFoundException(Constants.Messages.COUNTY_NOT_FOUND);
        }

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }
}