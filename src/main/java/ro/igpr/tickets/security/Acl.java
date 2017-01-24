/*
 * Copyright (c) 2015. Nephos6 Inc.
 */

package ro.igpr.tickets.security;

import io.netty.handler.codec.http.HttpMethod;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.persistence.types.Roles;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO Get privileges from database?
 */
public class Acl {

    private static Acl instance = null;
    private static final boolean DEFAULT_PERMISSION = false;
    private static Map<Roles, Map<String, Boolean>> permissions = new HashMap<>();

    private Acl() {

        permissions.put(Roles.admin,
                new HashMap<String, Boolean>() {{
                    put(Constants.Routes.SINGLE_USER + "_" + HttpMethod.GET, true);
                    put(Constants.Routes.SINGLE_USER + "_" + HttpMethod.PUT, true);
                    put(Constants.Routes.SINGLE_USER + "_" + HttpMethod.DELETE, true);
                    put(Constants.Routes.USER_COLLECTION + "_" + HttpMethod.GET, true);
                    put(Constants.Routes.USER_COLLECTION + "_" + HttpMethod.POST, true);
                    put(Constants.Routes.USER_COLLECTION + "_" + HttpMethod.PUT, true);
                    put(Constants.Routes.USER_COLLECTION + "_" + HttpMethod.DELETE, true);
                }}
        );
        permissions.put(Roles.user,
                new HashMap<String, Boolean>() {{
                    put(Constants.Routes.SINGLE_TICKET + "_" + HttpMethod.GET, true);
                    put(Constants.Routes.SINGLE_TICKET + "_" + HttpMethod.PUT, true);
                    put(Constants.Routes.SINGLE_TICKET + "_" + HttpMethod.DELETE, true);
                    put(Constants.Routes.TICKET_COLLECTION + "_" + HttpMethod.GET, true);
                    put(Constants.Routes.TICKET_COLLECTION + "_" + HttpMethod.POST, true);
                }}
        );
        permissions.put(Roles.device,
                new HashMap<String, Boolean>() {{
                    put(Constants.Routes.SINGLE_TICKET + "_" + HttpMethod.GET, true);
                    put(Constants.Routes.SINGLE_TICKET + "_" + HttpMethod.PUT, true);
                    put(Constants.Routes.SINGLE_TICKET + "_" + HttpMethod.DELETE, true);
                    put(Constants.Routes.TICKET_COLLECTION + "_" + HttpMethod.GET, true);
                    put(Constants.Routes.TICKET_COLLECTION + "_" + HttpMethod.POST, true);
                }}
        );
    }

    public static Acl getInstance() {
        if (instance == null) instance = new Acl();
        return instance;
    }

    public boolean hasPermission(Roles role, String resource, HttpMethod method) {

        boolean hasPermission = DEFAULT_PERMISSION;
        if (permissions.get(role) != null) {
            if (permissions.get(role).get(resource + "_" + method) != null) {
                hasPermission = permissions.get(role).get(resource + "_" + method);
            }
        }
        return hasPermission;
    }
}
