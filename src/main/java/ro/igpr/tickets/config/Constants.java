package ro.igpr.tickets.config;

public final class Constants {

    public final class Messages {
        public static final String INVALID_OBJECT_ID = "Invalid object ID supplied!";

        public static final String NO_TICKET_ID = "No ticket ID supplied!";
        public static final String TICKET_NOT_FOUND = "Ticket not found!";

        public static final String NO_MESSAGE_ID = "No message ID supplied!";
        public static final String MESSAGE_NOT_FOUND = "Message not found!";

        public static final String NO_ATTACHMENT_ID = "No attachment ID supplied!";
        public static final String ATTACHMENT_NOT_FOUND = "Attachment not found!";

        public static final String NO_COUNTY_ID = "No county ID supplied!";
        public static final String COUNTY_NOT_FOUND = "County not found!";

        public static final String RESOURCE_DETAILS_NOT_PROVIDED = "Resource details not provided";
        public static final String GENERIC_DATA_CONFLICT = "Generic data conflict";
        public static final String OBJECT_CREATED = "Object created";
        public static final String OBJECT_FOUND = "Object found";
        public static final String OBJECT_NOT_FOUND = "Object not found";

        public static final String NO_ACTION = "No method supplied!";
        public static final String FORBIDDEN_RESOURCE = "You do not have access to this resource!";
    }

    /**
     * These define the URL parameters used in the route definition strings (e.g. '{ticketId}').
     */
    public final class Url {
        public static final String ID = "id";

        public static final String TICKET_ID = "ticketId";
        public static final String COUNTY_ID = "countyId";
        public static final String MESSAGE_ID = "messageId";
        public static final String ATTACHMENT_ID = "attachmentId";
    }

    /**
     * These define the route names used in naming each route definitions.  These names are used
     * to retrieve URL patterns within the controllers by name to create links in responses.
     */
    public final class Routes {

        public static final String SINGLE_TICKET = "ticket.single.route";
        public static final String TICKET_COLLECTION = "ticket.collection.route";

        public static final String SINGLE_MESSAGE = "message.single.route";
        public static final String MESSAGE_COLLECTION = "message.collection.route";

        public static final String SINGLE_ATTACHMENT = "attachment.single.route";
        public static final String ATTACHMENT_COLLECTION = "attachment.collection.route";

        public static final String SINGLE_COUNTY = "county.single.route";
        public static final String COUNTY_COLLECTION = "county.collection.route";

    }
}
