/*
 * Copyright (c) 2015. Nephos6 Inc.
 */

package ro.igpr.tickets.security;

import io.netty.handler.codec.http.HttpMethod;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.persistence.types.Roles;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO Get privileges from database
 */
public class Acl {

    private static Acl instance = null;
    private static final boolean DEFAULT_PERMISSION = false;
    private static Map<Roles, List<Permission>> permissions = new HashMap<>();

    private Acl() {

        permissions.put(Roles.admin, Arrays.asList(
                new Permission(Roles.admin, Constants.Routes.SINGLE_USER, HttpMethod.GET, true),
                new Permission(Roles.admin, Constants.Routes.SINGLE_USER, HttpMethod.PUT, true),
                new Permission(Roles.admin, Constants.Routes.SINGLE_USER, HttpMethod.DELETE, true),
                new Permission(Roles.admin, Constants.Routes.USER_COLLECTION, HttpMethod.GET, true),
                new Permission(Roles.admin, Constants.Routes.USER_COLLECTION, HttpMethod.POST, true),
                new Permission(Roles.admin, Constants.Routes.USER_COLLECTION, HttpMethod.PUT, true),
                new Permission(Roles.admin, Constants.Routes.USER_COLLECTION, HttpMethod.DELETE, true)
        ));

        permissions.put(Roles.user, Arrays.asList(
                new Permission(Roles.user, Constants.Routes.SINGLE_TICKET, HttpMethod.GET, true),
                new Permission(Roles.user, Constants.Routes.SINGLE_TICKET, HttpMethod.DELETE, true),
                new Permission(Roles.user, Constants.Routes.SINGLE_TICKET, HttpMethod.PUT, true),
                new Permission(Roles.user, Constants.Routes.TICKET_COLLECTION, HttpMethod.GET, true),
                new Permission(Roles.user, Constants.Routes.TICKET_COLLECTION, HttpMethod.POST, true)
        ));
        permissions.put(Roles.device, Arrays.asList(
                new Permission(Roles.device, Constants.Routes.SINGLE_TICKET, HttpMethod.GET, true),
                new Permission(Roles.device, Constants.Routes.SINGLE_TICKET, HttpMethod.DELETE, true),
                new Permission(Roles.device, Constants.Routes.SINGLE_TICKET, HttpMethod.PUT, true),
                new Permission(Roles.device, Constants.Routes.TICKET_COLLECTION, HttpMethod.GET, true),
                new Permission(Roles.device, Constants.Routes.TICKET_COLLECTION, HttpMethod.POST, true)
        ));
    }

    public static Acl getInstance() {
        if (instance == null) instance = new Acl();
        return instance;
    }

    public boolean hasPermission(Roles role, String resource, HttpMethod method) {

        if (permissions.get(role) != null) {
            for (Permission perm : permissions.get(role)) {
                if (perm.role.equals(role) && perm.resource.equals(resource) && perm.method.equals(method) && perm.allow)
                    return true;
            }
        }
        return DEFAULT_PERMISSION;
    }

    private class Permission {
        public Roles role;
        public String resource;
        public HttpMethod method;
        public boolean allow;

        private Permission(Roles role, String resource, HttpMethod method, boolean allow) {
            this.role = role;
            this.resource = resource;
            this.method = method;
            this.allow = allow;

        }

    }
}
