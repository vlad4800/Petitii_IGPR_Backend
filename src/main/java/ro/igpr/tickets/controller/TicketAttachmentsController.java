package ro.igpr.tickets.controller;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.wordnik.swagger.annotations.*;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import org.hibernate.criterion.Order;
import org.restexpress.Request;
import org.restexpress.Response;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.domain.TicketAttachmentsEntity;
import ro.igpr.tickets.util.StreamUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @TODO Check if ticket actually exists
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = TicketAttachmentsEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })
    @ApiOperation(value = "Create a new ticket attachment.",
            notes = "Create a new ticket attachment",
            response = TicketAttachmentsEntity.class,
            position = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fileName", required = true, value = "The attached file name", paramType = "query",
                    dataType = "string"
            ),
            @ApiImplicitParam(name = "Content-Type", required = true, value = "The contet-type of the file", paramType = "header",
                    dataType = "string"
            ),
            @ApiImplicitParam(name = "ticketInfo", required = true, value = "The attachment binary content", paramType = "body",
                    dataType = "TicketAttachmentsEntity"
            ),
    })
    public final TicketAttachmentsEntity create(final Request request, final Response response) {

        super.create(request, response);

        final Long ticketId = Long.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));
        final String originalFileName = request.getHeader(Constants.Url.FILE_NAME, Constants.Messages.NO_FILENAME);
        final String contentType = request.getHeader(HttpHeaders.Names.CONTENT_TYPE, Constants.Messages.NO_CONTENT_TYPE);

        TicketAttachmentsEntity entity = null;

        // write image to disk
        String newFileName = StreamUtil.writeFileFromStreamToDisk(ticketId, originalFileName, request.getBodyAsStream());

        // create only if file was saved to disk
        if (newFileName != null) {
            entity = new TicketAttachmentsEntity(ticketId, newFileName, originalFileName, contentType);
            dao.save(entity);

            // Bind the resource with link URL tokens, etc. here...
            final TokenResolver resolver = HyperExpress.bind(Constants.Url.ATTACHMENT_ID, entity.getId().toString());

            // Include the Location header...
            final String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_ATTACHMENT);
            response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

            // Return the newly-created resource...

            response.setResponseCreated();
        } else {
            response.setResponseNoContent();
        }
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

        final Long id = Long.valueOf(request.getHeader(Constants.Url.ATTACHMENT_ID, Constants.Messages.NO_ATTACHMENT_ID));

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
    public final List<TicketAttachmentsEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);

        final Long ticketId = Long.valueOf(request.getHeader(Constants.Url.TICKET_ID, Constants.Messages.NO_TICKET_ID));
        Map<String, Object> fields = new HashMap<>();
        fields.put(Constants.Fields.TICKET_ID, ticketId);
        final List<TicketAttachmentsEntity> tickets = dao.getAll(TicketAttachmentsEntity.class, fields, Order.asc(Constants.Fields.ID));

        HyperExpress.tokenBinder(new TokenBinder<TicketAttachmentsEntity>() {
            @Override
            public void bind(TicketAttachmentsEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.ATTACHMENT_ID, entity.getId().toString());
            }
        });

        return tickets;
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

        final Long id = Long.valueOf(request.getHeader(Constants.Url.ATTACHMENT_ID, Constants.Messages.NO_ATTACHMENT_ID));
        final TicketAttachmentsEntity entity = dao.get(TicketAttachmentsEntity.class, id);

        if (entity == null) {
            throw new ItemNotFoundException(Constants.Messages.ATTACHMENT_NOT_FOUND);
        }

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }
}