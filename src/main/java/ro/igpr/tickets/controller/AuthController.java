package ro.igpr.tickets.controller;

import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.wordnik.swagger.annotations.*;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.exception.BadRequestException;
import org.restexpress.exception.UnauthorizedException;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.domain.TokenEntity;
import ro.igpr.tickets.domain.UsersEntity;
import ro.igpr.tickets.persistence.types.Status;
import ro.igpr.tickets.persistence.types.TokenType;
import ro.igpr.tickets.util.PasswordHash;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class AuthController extends BaseController {

    public AuthController() {
        super();
    }

    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.Messages.OK, response = TokenEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.NO_USERNAME),
            @ApiResponse(code = 400, message = Constants.Messages.NO_PASSWORD),
            @ApiResponse(code = 401, message = Constants.Messages.LOGIN_FAILED),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 404, message = Constants.Messages.USER_NOT_FOUND),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })
    @ApiOperation(value = "Get a user token",
            notes = "Get a user token",
            response = TokenEntity.class,
            position = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constants.Url.EMAIL, required = true, value = "Username", paramType = "query",
                    dataType = "string"
            ),
            @ApiImplicitParam(name = Constants.Url.PASSWORD, required = true, value = "Password", paramType = "query",
                    dataType = "string"
            )
    })
    public final TokenEntity users(final Request request, final Response response) {

        final String email = request.getHeader(Constants.Url.EMAIL, Constants.Messages.NO_USERNAME);
        final String password = request.getHeader(Constants.Url.PASSWORD, Constants.Messages.NO_PASSWORD);

        // find user
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.Fields.EMAIL, email);
        params.put(Constants.Fields.PASSWORD, password);

        final UsersEntity user = dao.get(UsersEntity.class, Constants.Fields.EMAIL, email);
        if (user == null) {
            throw new ItemNotFoundException(Constants.Messages.USER_NOT_FOUND);
        }

        if (user.getStatus().equals(Status.inactive)) {
            throw new ItemNotFoundException(Constants.Messages.USER_INACTIVE);
        }

        try {
            if (!PasswordHash.validatePassword(password, user.getPassword())) {
                throw new UnauthorizedException(Constants.Messages.LOGIN_FAILED);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new ItemNotFoundException(Constants.Messages.LOGIN_FAILED);
        } catch (InvalidKeySpecException ei) {
            ei.printStackTrace();
            throw new ItemNotFoundException(Constants.Messages.LOGIN_FAILED);
        }

        return tokenDao.getToken(user.getId().toString());
    }


    @ApiResponses({
            @ApiResponse(code = 200, message = Constants.Messages.OK, response = TokenEntity.class),
            @ApiResponse(code = 400, message = Constants.Messages.NO_DEVICE_ID),
            @ApiResponse(code = 400, message = Constants.Messages.INVALID_DEVICE_ID),
            @ApiResponse(code = 403, message = Constants.Messages.FORBIDDEN_RESOURCE),
            @ApiResponse(code = 405, message = Constants.Messages.RESOURCE_DETAILS_NOT_PROVIDED),
            @ApiResponse(code = 409, message = Constants.Messages.GENERIC_DATA_CONFLICT)
    })
    @ApiOperation(value = "Get a device token",
            notes = "Get a device token",
            response = TokenEntity.class,
            position = 0)
    @ApiImplicitParams({
            @ApiImplicitParam(name = Constants.Url.DEVICE_ID, required = true, value = "Device ID", paramType = "query",
                    dataType = "string"
            )
    })
    public final TokenEntity devices(final Request request, final Response response) {

        final String deviceId = request.getHeader(Constants.Url.DEVICE_ID, Constants.Messages.NO_DEVICE_ID);

        // Test if deviceId is indeed a UUID
        try {
            UUID deviceIdUUID = UUID.fromString(deviceId);
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException(Constants.Messages.INVALID_DEVICE_ID);
        }
        return tokenDao.getToken(deviceId, TokenType.device);
    }
}