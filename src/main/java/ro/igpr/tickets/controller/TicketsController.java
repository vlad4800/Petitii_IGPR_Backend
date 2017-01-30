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
import org.restexpress.exception.ForbiddenException;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.domain.TicketsEntity;
import ro.igpr.tickets.domain.TokenEntity;
import ro.igpr.tickets.persistence.types.TokenType;
import ro.igpr.tickets.util.CommonUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TicketsController extends BaseController {

    public TicketsController() {
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
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = TicketsEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })

    @ApiOperation(value = "Create a new ticket.",
            notes = "Create a new ticket.",
            response = TicketsEntity.class,
            position = 0)

    @ApiImplicitParams({

            @ApiImplicitParam(name = "ticketInfo", required = true, value = "The ticket details", paramType = "body",
                    dataType = "TicketsEntity"
            ),
    })
    public final TicketsEntity create(final Request request, final Response response) {

        super.create(request, response);

        final TicketsEntity entity = request.getBodyAs(TicketsEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        // set the ticket IP to the IP of the request
        entity.setIp(CommonUtil.getIp(request));
        dao.save(entity);

        // Bind the resource with link URL tokens, etc. here...
        final TokenResolver resolver = HyperExpress.bind(Constants.Url.TICKET_ID, entity.getId().toString());

        // Include the Location header...
        final String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_TICKET);
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
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.Messages.OK, response = TicketsEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.NO_TICKET_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 404, message = Constants.Messages.TICKET_NOT_FOUND),
    })
    @ApiImplicitParams({

    })
    public final TicketsEntity read(final Request request, final Response response) {

        super.read(request, response);

        final Long id = Long.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));

        TicketsEntity ticket = this.validate(request, id);
        HyperExpress.bind(Constants.Url.TICKET_ID, ticket.getId().toString());

        return ticket;
    }

    /**
     * Lists all tickets
     *
     * @param request
     * @param response
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.Messages.OK, response = List.class),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 404, message = Constants.Messages.TICKET_NOT_FOUND),
    })
    public final List<TicketsEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);

        Map<String, Object> params = new HashMap<>();
        TokenEntity te = (TokenEntity) request.getAttachment(Constants.Tokens.AUTHORIZED_TOKEN_ENTITY_NAME);
        if (te.getType().equals(TokenType.bearer)) {
            params.put(Constants.Fields.USER_ID, te.getEntityId());
        } else if (te.getType().equals(TokenType.device)) {
            params.put(Constants.Fields.DEVICE_ID, te.getEntityId());
        }

        final List<TicketsEntity> tickets = dao.getAll(TicketsEntity.class, params, Order.asc(Constants.Fields.ID));

        HyperExpress.tokenBinder(new TokenBinder<TicketsEntity>() {
            @Override
            public void bind(TicketsEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.TICKET_ID, entity.getId().toString());
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
    @ApiResponses({
            @ApiResponse(code = 204, message = ""),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 404, message = Constants.Messages.TICKET_NOT_FOUND),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT),
    })
    @ApiOperation(value = "Update a ticket.",
            notes = "Update a ticket.",
            response = TicketsEntity.class,
            position = 0)

    @ApiImplicitParams({

            @ApiImplicitParam(name = "ticketDetails", required = true, value = "The ticket details", paramType = "body",
                    dataType = "TicketsEntity"
            ),
    })
    public final void update(final Request request, final Response response) {
        super.update(request, response);

        final Long id = Long.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));
        final TicketsEntity ticket = request.getBodyAs(TicketsEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        this.validate(request, id);

        final Object result = dao.mergeFromEntities(ticket, id, Constants.Messages.TICKET_NOT_FOUND);

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
    @ApiResponses({
            @ApiResponse(code = 204, message = ""),
            @ApiResponse(code = 400, message = Constants.Messages.NO_TICKET_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 404, message = Constants.Messages.TICKET_NOT_FOUND),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT),
    })
    @ApiImplicitParams({

    })
    public final void delete(final Request request, final Response response) {
        super.delete(request, response);

        final Long id = Long.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));
        final TicketsEntity entity = this.validate(request, id);

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }

    /**
     * Checks if given credentials are valid for requested entityId
     *
     * @param request
     * @param id
     * @return
     */
    private final TicketsEntity validate(Request request, Long id) {

        final TicketsEntity ticket = dao.get(TicketsEntity.class, id);
        if (ticket == null) {
            throw new ItemNotFoundException(Constants.Messages.TICKET_NOT_FOUND);
        }

        TokenEntity te = (TokenEntity) request.getAttachment(Constants.Tokens.AUTHORIZED_TOKEN_ENTITY_NAME);
        if (te.getType().equals(TokenType.bearer)) {
            if (ticket.getUserId() == null || !ticket.getUserId().equals(te.getEntityId())) {
                throw new ForbiddenException(Constants.Messages.FORBIDDEN_RESOURCE);
            }
        } else if (te.getType().equals(TokenType.device)) {
            if (ticket.getDeviceId() == null || !ticket.getDeviceId().equals(te.getEntityId())) {
                throw new ForbiddenException(Constants.Messages.FORBIDDEN_RESOURCE);
            }
        }

        return ticket;
    }
}