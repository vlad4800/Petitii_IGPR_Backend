package ro.igpr.tickets.security;

import io.netty.handler.codec.http.HttpHeaders;
import org.restexpress.Request;
import org.restexpress.exception.ForbiddenException;
import org.restexpress.exception.UnauthorizedException;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.domain.TokenEntity;
import ro.igpr.tickets.domain.UsersEntity;
import ro.igpr.tickets.persistence.GenericDao;
import ro.igpr.tickets.persistence.types.Roles;
import ro.igpr.tickets.persistence.types.TokenType;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by vlad4800@gmail.com on 20-Jan-17.
 */
public class Security {

    private final static String EMPTY_STRING = "";
    private final static int FIVE_MINUTES = 300000;
    private final static int THIRTY_MINUTES = 30;
    private final static GenericDao dao = GenericDao.getInstance();

    /**
     * Checks if requested method can be executed for the provided access token
     *
     * @param request
     * @return
     */
    public static void checkPrivileges(Request request) {

        String authToken = request.getHeader(HttpHeaders.Names.AUTHORIZATION, Constants.Messages.AUTHORIZATION_TOKEN_INVALID);
        try {
            authToken = URLDecoder.decode(authToken, "UTF-8").trim();
        } catch (UnsupportedEncodingException e) {
        }

        boolean hasPrivileges = false;
        if (authToken.startsWith(Constants.Tokens.TOKEN_DEVICE_NAME)) {
            hasPrivileges = isAuthTokenValid(request, authToken.toLowerCase().replace(Constants.Tokens.TOKEN_DEVICE_NAME, EMPTY_STRING).trim(), TokenType.device);
        } else if (authToken.toLowerCase().startsWith(Constants.Tokens.TOKEN_BEARER_NAME)) {
            hasPrivileges = isAuthTokenValid(request, authToken.toLowerCase().replace(Constants.Tokens.TOKEN_BEARER_NAME, EMPTY_STRING).trim(), TokenType.bearer);
        }
        if (!hasPrivileges) throw new UnauthorizedException(Constants.Messages.AUTHORIZATION_TOKEN_INVALID);
    }

    /**
     * Check if deviceId is a valid UUID
     *
     * @param deviceId
     * @return
     */
    public static final boolean isDeviceIdValid(String deviceId) {
        try {
            UUID u = UUID.fromString(deviceId);
        } catch (IllegalFormatCodePointException | NumberFormatException ex) {
            return false;
        }
        return true;
    }

    /**
     * Checks if provided token is valid
     *
     * @param request
     * @param authToken
     * @param type
     * @return
     */
    private static final boolean isAuthTokenValid(Request request, String authToken, TokenType type) {

        return (getAuthorizedTokenEntity(request, authToken, type) != null);
    }

    /**
     * Retrieve auth token from DB
     *
     * @param authToken
     * @param type
     * @return
     */
    public static TokenEntity getAuthorizedTokenEntity(Request request, String authToken, TokenType type) {

        final Map<String, Object> params = new HashMap<>();
        params.put(Constants.Fields.VALUE, authToken);
        params.put(Constants.Fields.TYPE, type);
        final TokenEntity token = dao.get(TokenEntity.class, params);
        if (token == null || token.getDeleteDate() != null) return null;

        Date now = new Date();
        // if expiry time is before current time then token is expired
        if (token.getExpiryDate().before(now)) return null;

        Roles role = Roles.user;
        if (type.equals(TokenType.bearer)) {
            UsersEntity user = dao.get(UsersEntity.class, Constants.Fields.ID, Long.valueOf(token.getEntityId()));

            if (user == null) return null;

            role = user.getRole();
        } else if (type.equals(TokenType.device)) {
            role = Roles.device;
        }

        if (!Acl.getInstance().hasPermission(role, request.getResolvedRoute().getName(), request.getResolvedRoute().getMethod()))
            throw new ForbiddenException(Constants.Messages.FORBIDDEN_RESOURCE);

        /**
         * Extend user token expiry date with 30 minutes if it expires within 5 minutes or less from now
         */
        if (token.getExpiryDate().getTime() - now.getTime() <= FIVE_MINUTES) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(token.getExpiryDate());
            calendar.add(Calendar.MINUTE, THIRTY_MINUTES);
            token.setExpiryDate(calendar.getTime());
            dao.saveOrUpdate(token);
        }
        request.putAttachment(Constants.Tokens.AUTHORIZED_TOKEN_ENTITY_NAME, token);

        return token;
    }
}
