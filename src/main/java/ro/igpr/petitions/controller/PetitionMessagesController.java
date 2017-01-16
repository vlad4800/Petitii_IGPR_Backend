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
import ro.igpr.petitions.domain.PetitionMessagesEntity;

import java.util.Date;
import java.util.List;

public final class PetitionMessagesController extends BaseController {

    public PetitionMessagesController() {
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
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = PetitionMessagesEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })

    @ApiOperation(value = "Create a new petition.",
            notes = "Create a new petition.",
            response = PetitionMessagesEntity.class,
            position = 0)

    @ApiImplicitParams({

            @ApiImplicitParam(name = "petitionInfo", required = true, value = "The petition details", paramType = "body",
                    dataType = "PetitionMessagesEntity"
            ),
    })
    public final PetitionMessagesEntity create(final Request request, final Response response) {

        super.create(request, response);

        final Integer petitionId = Integer.valueOf(request.getHeader(Constants.Url.PETITION_ID, Constants.Messages.NO_PETITION_ID));

        final PetitionMessagesEntity entity = request.getBodyAs(PetitionMessagesEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        entity.setPetitionId(petitionId);

        response.setResponseCreated();

        // Bind the resource with link URL tokens, etc. here...
        final TokenResolver resolver = HyperExpress.bind(Constants.Url.MESSAGE_ID, entity.getId().toString());

        // Include the Location header...
        final String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_PETITION);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

        // Return the newly-created resource...

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
    public final PetitionMessagesEntity read(final Request request, final Response response) {

        super.read(request, response);

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.MESSAGE_ID, Constants.Messages.NO_MESSAGE_ID));

        final PetitionMessagesEntity petition = dao.get(PetitionMessagesEntity.class, id);
        if (petition == null) {
            throw new ItemNotFoundException(Constants.Messages.MESSAGE_NOT_FOUND);
        }

        HyperExpress.bind(Constants.Url.MESSAGE_ID, petition.getId().toString());

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
    public final List<PetitionMessagesEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);

        final Integer petitionId = Integer.valueOf(request.getHeader(Constants.Url.PETITION_ID, Constants.Messages.NO_PETITION_ID));
        final List<PetitionMessagesEntity> petitions = dao.getAll(PetitionMessagesEntity.class, Order.asc("id"));

        HyperExpress.tokenBinder(new TokenBinder<PetitionMessagesEntity>() {
            @Override
            public void bind(PetitionMessagesEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.MESSAGE_ID, entity.getId().toString());
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

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.MESSAGE_ID, Constants.Messages.NO_MESSAGE_ID));
        final PetitionMessagesEntity petition = request.getBodyAs(PetitionMessagesEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);
        if (petition == null) {
            throw new ItemNotFoundException(Constants.Messages.MESSAGE_NOT_FOUND);
        }

        final Object result = dao.mergeFromEntities(petition, id, Constants.Messages.MESSAGE_NOT_FOUND);

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

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.MESSAGE_ID, Constants.Messages.NO_MESSAGE_ID));
        final PetitionMessagesEntity entity = dao.get(PetitionMessagesEntity.class, id);

        if (entity == null) {
            throw new ItemNotFoundException(Constants.Messages.MESSAGE_NOT_FOUND);
        }

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }
}