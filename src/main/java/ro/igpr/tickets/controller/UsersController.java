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
import ro.igpr.tickets.domain.UsersEntity;

import java.util.Date;
import java.util.List;

public final class UsersController extends BaseController {

    public UsersController() {
        super();
    }

    /**
     * Creates a new user
     *
     * @param request
     * @param response
     * @return
     */
    @ApiResponses({
            @ApiResponse(code = 201, message = Constants.Messages.OBJECT_CREATED, response = UsersEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_OBJECT_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })

    @ApiOperation(value = "Create a new user.",
            notes = "Create a new user.",
            response = UsersEntity.class,
            position = 0)

    @ApiImplicitParams({

            @ApiImplicitParam(name = "ticketInfo", required = true, value = "The user details", paramType = "body",
                    dataType = "UsersEntity"
            ),
    })
    public final UsersEntity create(final Request request, final Response response) {

        super.create(request, response);

        final UsersEntity entity = request.getBodyAs(UsersEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);

        dao.save(entity);

        // Bind the resource with link URL tokens, etc. here...
        final TokenResolver resolver = HyperExpress.bind(Constants.Url.USER_ID, entity.getId().toString());

        // Include the Location header...
        final String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_USER);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

        response.setResponseCreated();

        return entity;
    }

    /**
     * Finds a user by id and outputs the user task
     *
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({

    })
    public final UsersEntity read(final Request request, final Response response) {

        super.read(request, response);

        final Long id = Long.valueOf(request.getHeader(Constants.Url.USER_ID, Constants.Messages.NO_USER_ID));

        final UsersEntity user = dao.get(UsersEntity.class, id);
        if (user == null) {
            throw new ItemNotFoundException(Constants.Messages.USERT_NOT_FOUND);
        }

        HyperExpress.bind(Constants.Url.USER_ID, user.getId().toString());

        return user;
    }

    /**
     * Lists all users
     *
     * @param request
     * @param response
     * @return
     */
    @ApiImplicitParams({
    })
    public final List<UsersEntity> readAll(final Request request, final Response response) {
        super.readAll(request, response);

        final List<UsersEntity> users = dao.getAll(UsersEntity.class, Order.asc(Constants.Fields.ID));

        HyperExpress.tokenBinder(new TokenBinder<UsersEntity>() {
            @Override
            public void bind(UsersEntity entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.USER_ID, entity.getId().toString());
            }
        });

        return users;
    }

    /**
     * Updates user details
     *
     * @param request
     * @param response
     */
    @ApiImplicitParams({

    })
    public final void update(final Request request, final Response response) {
        super.update(request, response);

        final Long id = Long.valueOf(request.getHeader(Constants.Url.USER_ID, Constants.Messages.NO_USER_ID));
        final UsersEntity user = request.getBodyAs(UsersEntity.class, Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED);
        if (user == null) {
            throw new ItemNotFoundException(Constants.Messages.USERT_NOT_FOUND);
        }

        if (user.getPassword() == null) {
            user.setPassword(Constants.Values.IGNORE_PASSWORD);
        }

        final Object result = dao.mergeFromEntities(user, id, Constants.Messages.USERT_NOT_FOUND);

        if (result == null) {
            throw new HibernateException(Constants.Messages.UPDATE_FAILED);
        }

        response.setResponseNoContent();
    }

    /**
     * Deletes a user
     *
     * @param request
     * @param response
     */
    @ApiImplicitParams({

    })
    public final void delete(final Request request, final Response response) {
        super.delete(request, response);

        final Long id = Long.valueOf(request.getHeader(Constants.Url.USER_ID, Constants.Messages.NO_USER_ID));
        final UsersEntity entity = dao.get(UsersEntity.class, id);

        if (entity == null) {
            throw new ItemNotFoundException(Constants.Messages.USERT_NOT_FOUND);
        }

        entity.setDeleteDate(new Date());
        dao.merge(entity);

        response.setResponseNoContent();
    }
}