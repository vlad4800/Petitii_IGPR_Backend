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
import ro.igpr.tickets.domain.TicketMessagesEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TicketMessagesController extends BaseController {

    public TicketMessagesController() {
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
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = TicketMessagesEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })

    @ApiOperation(value = "Create a new ticket message.",
            notes = "Create a new ticket message.",
            response = TicketMessagesEntity.class,
            position = 0)

    @ApiImplicitParams({
            @ApiImplicitParam(name = "ticketInfo", required = true, value = "The ticket message details", paramType = "body",
                    dataType = "TicketMessagesEntity"
            ),
    })
    public final TicketMessagesEntity create(final Request request, final Response response) {

        super.create(request, response);

        final Long ticketId = Long.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));

        final TicketMessagesEntity entity = request.getBodyAs(TicketMessagesEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        entity.setTicketId(ticketId);
        dao.save(entity);


        // Bind the resource with link URL tokens, etc. here...
        final TokenResolver resolver = HyperExpress.bind(Constants.Url.MESSAGE_ID, entity.getId().toString());

        // Include the Location header...
        final String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_MESSAGE);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

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
    public final TicketMessagesEntity read(final Request request, final Response response) {

        super.read(request, response);

        final Long id = Long.valueOf(request.getHeader(Constants.Url.MESSAGE_ID, Constants.Messages.NO_MESSAGE_ID));

        final TicketMessagesEntity ticket = dao.get(TicketMessagesEntity.class, id);
        if (ticket == null) {
            throw new ItemNotFoundException(Constants.Messages.MESSAGE_NOT_FOUND);
        }

        HyperExpress.bind(Constants.Url.MESSAGE_ID, ticket.getId().toString());

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
    public final List<TicketMessagesEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);

        final Long ticketId = Long.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));
        Map<String, Object> fields = new HashMap<>();
        fields.put(Constants.Fields.TICKET_ID, ticketId);
        final List<TicketMessagesEntity> tickets = dao.getAll(TicketMessagesEntity.class, fields, Order.asc(Constants.Fields.ID));

        HyperExpress.tokenBinder(new TokenBinder<TicketMessagesEntity>() {
            @Override
            public void bind(TicketMessagesEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.MESSAGE_ID, entity.getId().toString());
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

        final Long id = Long.valueOf(request.getHeader(Constants.Url.MESSAGE_ID, Constants.Messages.NO_MESSAGE_ID));
        final TicketMessagesEntity ticket = request.getBodyAs(TicketMessagesEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);
        if (ticket == null) {
            throw new ItemNotFoundException(Constants.Messages.MESSAGE_NOT_FOUND);
        }

        final Object result = dao.mergeFromEntities(ticket, id, Constants.Messages.MESSAGE_NOT_FOUND);

        if (result == null) {
            throw new HibernateException(Constants.Messages.UPDATE_FAILED);
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

        final Long id = Long.valueOf(request.getHeader(Constants.Url.MESSAGE_ID, Constants.Messages.NO_MESSAGE_ID));
        final TicketMessagesEntity entity = dao.get(TicketMessagesEntity.class, id);

        if (entity == null) {
            throw new ItemNotFoundException(Constants.Messages.MESSAGE_NOT_FOUND);
        }

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }
}