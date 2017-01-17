package ro.igpr.tickets.controller;

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
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.domain.TicketAttachmentsEntity;

import java.util.Date;
import java.util.List;

public final class TicketAttachmentsController extends BaseController {

    public TicketAttachmentsController() {
        super();
    }

    /**
     * Creates a new ticket
     *
     * @param request
     * @param response
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = TicketAttachmentsEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })

    @ApiOperation(value = "Create a new attachment.",
            notes = "Create a new attachment",
            response = TicketAttachmentsEntity.class,
            position = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ticketInfo", required = true, value = "The attachment details", paramType = "body",
                    dataType = "TicketAttachmentsEntity"
            ),
    })
    public final TicketAttachmentsEntity create(final Request request, final Response response) {

        super.create(request, response);

        final Integer ticketId = Integer.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));

        final TicketAttachmentsEntity entity = request.getBodyAs(TicketAttachmentsEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        entity.setTicketId(ticketId);

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
     * Finds a ticket by id and outputs the ticket task
     *
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({

    })
    public final TicketAttachmentsEntity read(final Request request, final Response response) {

        super.read(request, response);

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.ATTACHMENT_ID, Constants.Messages.NO_ATTACHMENT_ID));

        final TicketAttachmentsEntity ticket = dao.get(TicketAttachmentsEntity.class, id);
        if (ticket == null) {
            throw new ItemNotFoundException(Constants.Messages.ATTACHMENT_NOT_FOUND);
        }

        HyperExpress.bind(Constants.Url.ATTACHMENT_ID, ticket.getId().toString());

        return ticket;
    }

    /**
     * Lists all tickets
     *
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({
    })
    public final List<TicketAttachmentsEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);

        final Integer ticketId = Integer.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));
        final List<TicketAttachmentsEntity> tickets = dao.getAll(TicketAttachmentsEntity.class, Order.asc("id"));

        HyperExpress.tokenBinder(new TokenBinder<TicketAttachmentsEntity>() {
            @Override
            public void bind(TicketAttachmentsEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.ATTACHMENT_ID, entity.getId().toString());
            }
        });

        return tickets;
    }

    /**
     * Updates ticket details
     *
     * @param request
     * @param response
     */
    @ApiImplicitParams({

    })
    public final void update(final Request request, final Response response) {
        super.update(request, response);

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.ATTACHMENT_ID, Constants.Messages.NO_ATTACHMENT_ID));
        final TicketAttachmentsEntity ticket = request.getBodyAs(TicketAttachmentsEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);
        if (ticket == null) {
            throw new ItemNotFoundException(Constants.Messages.ATTACHMENT_NOT_FOUND);
        }

        final Object result = dao.mergeFromEntities(ticket, id, Constants.Messages.ATTACHMENT_NOT_FOUND);

        if (result == null) {
            throw new HibernateException("Update failed!");
        }

        response.setResponseNoContent();
    }

    /**
     * Deletes a ticket
     *
     * @param request
     * @param response
     */
    @ApiImplicitParams({

    })
    public final void delete(final Request request, final Response response) {
        super.delete(request, response);

        final Integer id = Integer.valueOf(request.getHeader(Constants.Url.ATTACHMENT_ID, Constants.Messages.NO_ATTACHMENT_ID));
        final TicketAttachmentsEntity entity = dao.get(TicketAttachmentsEntity.class, id);

        if (entity == null) {
            throw new ItemNotFoundException(Constants.Messages.ATTACHMENT_NOT_FOUND);
        }

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }
}