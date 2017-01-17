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
import ro.igpr.petitions.config.Constants;
import ro.igpr.petitions.domain.PetitionsEntity;

import java.util.Date;
import java.util.List;

public final class PetitionsController extends BaseController {

    public PetitionsController() {
        super();
    }

    /**
     * Creates a new petition
     *
     * @param request
     * @param response
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = PetitionsEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })

    @ApiOperation(value = "Create a new petition.",
            notes = "Create a new petition.",
            response = PetitionsEntity.class,
            position = 0)

    @ApiImplicitParams({

            @ApiImplicitParam(name = "petitionInfo", required = true, value = "The petition details", paramType = "body",
                    dataType = "PetitionsEntity"
            ),
    })
    public final PetitionsEntity create(final Request request, final Response response) {

        super.create(request, response);

        final PetitionsEntity entity = request.getBodyAs(PetitionsEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        dao.save(entity);

        // Bind the resource with link URL tokens, etc. here...
        final TokenResolver resolver = HyperExpress.bind(Constants.Url.PETITION_ID, entity.getId().toString());

        // Include the Location header...
        final String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_PETITION);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

        response.setResponseCreated();

        return entity;
    }

    /**
     * Finds a petition by id and outputs the petition task
     *
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({

    })
    public final PetitionsEntity read(final Request request, final Response response) {

        super.read(request, response);

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.PETITION_ID, Constants.Messages.NO_PETITION_ID));

        final PetitionsEntity petition = dao.get(PetitionsEntity.class, id);
        if (petition == null) {
            throw new ItemNotFoundException(Constants.Messages.PETITION_NOT_FOUND);
        }

        HyperExpress.bind(Constants.Url.PETITION_ID, petition.getId().toString());

        return petition;
    }

    /**
     * Lists all petitions
     *
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({
    })
    public final List<PetitionsEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);


        final List<PetitionsEntity> petitions = dao.getAll(PetitionsEntity.class, Order.asc("id"));

        HyperExpress.tokenBinder(new TokenBinder<PetitionsEntity>() {
            @Override
            public void bind(PetitionsEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.PETITION_ID, entity.getId().toString());
            }
        });

        return petitions;
    }

    /**
     * Updates petition details
     *
     * @param request
     * @param response
     */
    @ApiImplicitParams({

    })
    public final void update(final Request request, final Response response) {
        super.update(request, response);

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.PETITION_ID, Constants.Messages.NO_PETITION_ID));
        final PetitionsEntity petition = request.getBodyAs(PetitionsEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);
        if (petition == null) {
            throw new ItemNotFoundException(Constants.Messages.PETITION_NOT_FOUND);
        }

        final Object result = dao.mergeFromEntities(petition, id, Constants.Messages.PETITION_NOT_FOUND);

        if (result == null) {
            throw new HibernateException("Update failed!");
        }

        response.setResponseNoContent();
    }

    /**
     * Deletes a petition
     *
     * @param request
     * @param response
     */
    @ApiImplicitParams({

    })
    public final void delete(final Request request, final Response response) {
        super.delete(request, response);

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.PETITION_ID, Constants.Messages.NO_PETITION_ID));
        final PetitionsEntity entity = dao.get(PetitionsEntity.class, id);

        if (entity == null) {
            throw new ItemNotFoundException(Constants.Messages.PETITION_NOT_FOUND);
        }

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }
}