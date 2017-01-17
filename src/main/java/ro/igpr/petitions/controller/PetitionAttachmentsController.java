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
import ro.igpr.petitions.domain.PetitionAttachmentsEntity;

import java.util.Date;
import java.util.List;

public final class PetitionAttachmentsController extends BaseController {

    public PetitionAttachmentsController() {
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
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = PetitionAttachmentsEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })

    @ApiOperation(value = "Create a new attachment.",
            notes = "Create a new attachment",
            response = PetitionAttachmentsEntity.class,
            position = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "petitionInfo", required = true, value = "The attachment details", paramType = "body",
                    dataType = "PetitionAttachmentsEntity"
            ),
    })
    public final PetitionAttachmentsEntity create(final Request request, final Response response) {

        super.create(request, response);

        final Integer petitionId = Integer.valueOf(request.getHeader(Constants.Url.PETITION_ID, Constants.Messages.NO_PETITION_ID));

        final PetitionAttachmentsEntity entity = request.getBodyAs(PetitionAttachmentsEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        entity.setPetitionId(petitionId);

        dao.save(entity);

        // Bind the resource with link URL tokens, etc. here...
        final TokenResolver resolver = HyperExpress.bind(Constants.Url.ATTACHMENT_ID, entity.getId().toString());

        // Include the Location header...
        final String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_ATTACHMENT);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

        // Return the newly-created resource...

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
    public final PetitionAttachmentsEntity read(final Request request, final Response response) {

        super.read(request, response);

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.ATTACHMENT_ID, Constants.Messages.NO_ATTACHMENT_ID));

        final PetitionAttachmentsEntity petition = dao.get(PetitionAttachmentsEntity.class, id);
        if (petition == null) {
            throw new ItemNotFoundException(Constants.Messages.ATTACHMENT_NOT_FOUND);
        }

        HyperExpress.bind(Constants.Url.ATTACHMENT_ID, petition.getId().toString());

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
    public final List<PetitionAttachmentsEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);

        final Integer petitionId = Integer.valueOf(request.getHeader(Constants.Url.PETITION_ID, Constants.Messages.NO_PETITION_ID));
        final List<PetitionAttachmentsEntity> petitions = dao.getAll(PetitionAttachmentsEntity.class, Order.asc("id"));

        HyperExpress.tokenBinder(new TokenBinder<PetitionAttachmentsEntity>() {
            @Override
            public void bind(PetitionAttachmentsEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.ATTACHMENT_ID, entity.getId().toString());
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

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.ATTACHMENT_ID, Constants.Messages.NO_ATTACHMENT_ID));
        final PetitionAttachmentsEntity petition = request.getBodyAs(PetitionAttachmentsEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);
        if (petition == null) {
            throw new ItemNotFoundException(Constants.Messages.ATTACHMENT_NOT_FOUND);
        }

        final Object result = dao.mergeFromEntities(petition, id, Constants.Messages.ATTACHMENT_NOT_FOUND);

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

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.ATTACHMENT_ID, Constants.Messages.NO_ATTACHMENT_ID));
        final PetitionAttachmentsEntity entity = dao.get(PetitionAttachmentsEntity.class, id);

        if (entity == null) {
            throw new ItemNotFoundException(Constants.Messages.ATTACHMENT_NOT_FOUND);
        }

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }
}