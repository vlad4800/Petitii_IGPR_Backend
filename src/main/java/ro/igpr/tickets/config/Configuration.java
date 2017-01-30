package ro.igpr.tickets.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.restexpress.RestExpress;
import org.restexpress.util.Environment;
import ro.igpr.tickets.controller.*;

import java.text.SimpleDateFormat;
import java.util.Properties;

public final class Configuration
        extends Environment {
    private static final String API_VERSION_NUMBER_PROPERTY = "version.number";
    private static final String API_VERSION_PATH_PROPERTY = "version.path";
    private static final String DEFAULT_API_VERSION = "1.0";
    private static final String DEFAULT_API_VERSION_PATH = "/v1";
    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";
    private static final String DEFAULT_IO_THREAD_POOL_SIZE = "20";
    private static final String DEFAULT_CONNECT_TIMEOUT = "5000";
    private static final String DEFAULT_KEEP_ALIVE = "true";
    private static final String PORT_PROPERTY = "port";
    private static final String BASE_URL_PROPERTY = "base.url";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";
    private static final String IO_THREAD_POOL_SIZE = "io.threadPool.size";
    private static final String CONNECT_TIMEOUT = "connectTimeout";
    private static final String KEEP_ALIVE = "keepAlive";
    private static final String ATTACHMENTS_PATH = "attachments.path";
    private static final String ATTACHMENTS_URL = "attachments.url";
    private static final String DEFAULT_ATTACHMENTS_PATH = ".";
    private static final String DEFAULT_ATTACHMENTS_URL = "/";

    private static String ENVIRONMENT_NAME = "vlad";

    public static final int TOKEN_BEARER_EXPIRY = 3600 * 24;
    public static final int TOKEN_DEVICE_EXPIRY = 3600 * 24 * 365;
    public static final int TOKEN_RESET_EXPIRY = 3600 * 24;

    private String apiVersionNumber;
    private String apiVersionPath;
    private int port;
    private String baseUrl;
    private int executorThreadPoolSize;
    private int ioThreadPoolSize;
    private int connectTimeout;
    private boolean keepAlive;
    private static String attachmentsPath;
    private static String attachmentsUrl;

    private TicketsController ticketsController;
    private CountiesController countiesController;
    private TicketMessagesController ticketMessagesController;
    private TicketAttachmentsController ticketAttachmentsController;
    private UsersController usersController;
    private AuthController authController;

    private final static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void fillValues(Properties p) {

        this.apiVersionNumber = p.getProperty(API_VERSION_NUMBER_PROPERTY, DEFAULT_API_VERSION);
        this.apiVersionPath = p.getProperty(API_VERSION_PATH_PROPERTY, DEFAULT_API_VERSION_PATH);


        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.ioThreadPoolSize = Integer.parseInt(p.getProperty(IO_THREAD_POOL_SIZE, DEFAULT_IO_THREAD_POOL_SIZE));
        this.connectTimeout = Integer.parseInt(p.getProperty(CONNECT_TIMEOUT, DEFAULT_CONNECT_TIMEOUT));
        this.keepAlive = Boolean.parseBoolean(p.getProperty(KEEP_ALIVE, DEFAULT_KEEP_ALIVE));
        attachmentsPath = p.getProperty(ATTACHMENTS_PATH, DEFAULT_ATTACHMENTS_PATH);
        attachmentsUrl = p.getProperty(ATTACHMENTS_URL, DEFAULT_ATTACHMENTS_URL);

        initialize();
    }

    private final void initialize() {

        initObjectMapper();

        ticketsController = new TicketsController();
        countiesController = new CountiesController();
        ticketMessagesController = new TicketMessagesController();
        ticketAttachmentsController = new TicketAttachmentsController();
        usersController = new UsersController();
        authController = new AuthController();
    }

    private void initObjectMapper() {
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    public final int getPort() {
        return port;
    }

    public final String getBaseUrl() {
        return baseUrl;
    }

    public final int getExecutorThreadPoolSize() {
        return executorThreadPoolSize;
    }

    public int getIoThreadPoolSize() {
        return ioThreadPoolSize;
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public final String getApiVersion() {
        return apiVersionNumber;
    }

    public final String getApiVersionPath() {
        return apiVersionPath;
    }


    public static final String getEnvironmentName() {
        return ENVIRONMENT_NAME;
    }

    public static final void setEnvironmentName(String env) {
        ENVIRONMENT_NAME = env;
    }

    public TicketsController getTicketsController() {
        return ticketsController;
    }


    public CountiesController getCountiesController() {
        return countiesController;
    }

    public TicketMessagesController getTicketMessagesController() {
        return ticketMessagesController;
    }

    public TicketAttachmentsController getTicketAttachmentsController() {
        return ticketAttachmentsController;
    }

    public final static String getAttachmentsPath() {
        return attachmentsPath;
    }

    public static String getAttachmentsUrl() {
        return attachmentsUrl;
    }

    public UsersController getUsersController() {
        return usersController;
    }

    public AuthController getAuthController() {
        return authController;
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static int getTokenBearerExpiry() {
        return TOKEN_BEARER_EXPIRY;
    }

    public static int getTokenResetExpiry() {
        return TOKEN_RESET_EXPIRY;
    }

    public static int getTokenDeviceExpiry() {
        return TOKEN_DEVICE_EXPIRY;
    }
}
