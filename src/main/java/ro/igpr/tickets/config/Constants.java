package ro.igpr.tickets.config;

public final class Constants {

    public final class Messages {
        public static final String OK = "Ok";
        public static final String INVALID_OBJECT_ID = "Invalid object ID supplied!";

        public static final String NO_DEVICE_ID = "No device ID supplied!";
        public static final String INVALID_DEVICE_ID = "Invalid deviceId!";

        public static final String NO_TICKET_ID = "No ticket ID supplied!";
        public static final String TICKET_NOT_FOUND = "Ticket not found!";

        public static final String NO_USER_ID = "No user ID supplied!";
        public static final String USERT_NOT_FOUND = "User not found!";
        public static final String NO_USERNAME = "No username supplied!";
        public static final String NO_EMAIL = "No email supplied!";
        public static final String NO_PASSWORD = "No password supplied!";
        public static final String USER_NOT_FOUND = "User not found!";
        public static final String USER_INACTIVE = "User is inactive!";
        public static final String LOGIN_FAILED = "Login failed!";


        public static final String NO_MESSAGE_ID = "No message ID supplied!";
        public static final String MESSAGE_NOT_FOUND = "Message not found!";

        public static final String NO_ATTACHMENT_ID = "No attachment ID supplied!";
        public static final String ATTACHMENT_NOT_FOUND = "Attachment not found!";
        public static final String NO_FILENAME = "Please provide the fileName param!";
        public static final String NO_CONTENT_TYPE = "Please provide the file content-type header!";

        public static final String NO_COUNTY_ID = "No county ID supplied!";
        public static final String COUNTY_NOT_FOUND = "County not found!";

        public static final String RESOURCE_DETAILS_NOT_PROVIDED = "Resource details not provided";
        public static final String GENERIC_DATA_CONFLICT = "Generic data conflict";
        public static final String OBJECT_CREATED = "Object created";
        public static final String OBJECT_FOUND = "Object found";
        public static final String OBJECT_NOT_FOUND = "Object not found";

        public static final String NO_ACTION = "No method supplied!";
        public static final String FORBIDDEN_RESOURCE = "You do not have access to this resource!";

        public static final String CANNOT_WRITE_TO_DISK = "Cannot write file to disk!";
        public static final String UPDATE_FAILED = "Update failed!";

        public static final String AUTHORIZATION_TOKEN_INVALID = "Authorization token is missing!";

        public static final String TOKEN_VALIDATION_FAILED = "Token generation failed!";
    }

    public final class Tokens {
        public static final String TOKEN_DEVICE_NAME = "device";
        public static final String TOKEN_BEARER_NAME = "bearer";
        public static final String ALGORITHM_SHA1 = "SHA-1";
        public static final String UTF8 = "UTF-8";
        public static final String P02X = "%02x";
        public static final String SEPARATOR = "#";
        public static final String AUTHORIZED_TOKEN_ENTITY_NAME = "authorizedTokenEntity";
    }

    /**
     * These define the URL parameters used in the route definition strings (e.g. '{ticketId}').
     */
    public final class Url {
        public static final String ID = "id";

        public static final String DEVICE_ID = "deviceId";
        public static final String TICKET_ID = "ticketId";
        public static final String USER_ID = "userId";
        public static final String COUNTY_ID = "countyId";
        public static final String MESSAGE_ID = "messageId";
        public static final String ATTACHMENT_ID = "attachmentId";
        public static final String FILE_NAME = "fileName";

        public static final String USERNAME = "username";
        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }

    /**
     * These define the route names used in naming each route definitions.  These names are used
     * to retrieve URL patterns within the controllers by name to create links in responses.
     */
    public final class Routes {

        public final class Actions {
            public static final String READ_ALL = "readAll";
            public static final String USERS = "users";
            public static final String DEVICES = "devices";
        }

        public static final String ROUTE_USERS = "users";
        public static final String SINGLE_USER = "user.single.route";
        public static final String USER_COLLECTION = "user.collection.route";

        public static final String ROUTE_TICKETS = "tickets";
        public static final String SINGLE_TICKET = "ticket.single.route";
        public static final String TICKET_COLLECTION = "ticket.collection.route";

        public static final String ROUTE_MESSAGES = "messages";
        public static final String SINGLE_MESSAGE = "message.single.route";
        public static final String MESSAGE_COLLECTION = "message.collection.route";

        public static final String ROUTE_ATTACHMENTS = "attachments";
        public static final String SINGLE_ATTACHMENT = "attachment.single.route";
        public static final String ATTACHMENT_COLLECTION = "attachment.collection.route";

        public static final String ROUTE_COUNTIES = "counties";
        public static final String SINGLE_COUNTY = "county.single.route";
        public static final String COUNTY_COLLECTION = "county.collection.route";


        public static final String AUTH_DEVICES = "auth/devices";
        public static final String AUTH_DEVICES_SINGLE_ROUTE = "auth.devices.single.route";
        public static final String AUTH_USERS = "auth/users";
        public static final String AUTH_USERS_SINGLE_ROUTE = "auth.users.single.route";

    }

    public final class Values {
        public static final String IGNORE_PASSWORD = "##ignorePass##";
    }

    public final class Fields {
        public static final String ID = "id";
        public static final String DEVICE_ID = "deviceId";
        public static final String USER_ID = "userId";
        public static final String TICKET_ID = "ticketId";
        public static final String VALUE = "value";
        public static final String TYPE = "type";
        public static final String ENTITY_ID = "entityId";


        public static final String EMAIL = "email";
        public static final String PASSWORD = "password";
    }
}