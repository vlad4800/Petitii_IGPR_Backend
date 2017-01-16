package ro.igpr.petitions.config;

import org.restexpress.RestExpress;
import org.restexpress.util.Environment;
import ro.igpr.petitions.controller.CountiesController;
import ro.igpr.petitions.controller.PetitionMessagesController;
import ro.igpr.petitions.controller.PetitionsController;

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

    private static String ENVIRONMENT_NAME = "vlad";
    private String apiVersionNumber;
    private String apiVersionPath;
    private int port;
    private String baseUrl;
    private int executorThreadPoolSize;
    private int ioThreadPoolSize;
    private int connectTimeout;
    private boolean keepAlive;

    private PetitionsController petitionsController;
    private CountiesController countiesController;
    private PetitionMessagesController petitionMessagesController;


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

        initialize();
    }

    private final void initialize() {


        petitionsController = new PetitionsController();
        countiesController = new CountiesController();
        petitionMessagesController = new PetitionMessagesController();
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

    public PetitionsController getPetitionsController() {
        return petitionsController;
    }

    public static final String getEnvironmentName() {
        return ENVIRONMENT_NAME;
    }

    public static final void setEnvironmentName(String env) {
        ENVIRONMENT_NAME = env;
    }

    public CountiesController getCountiesController() {
        return countiesController;
    }

    public PetitionMessagesController getPetitionMessagesController() {
        return petitionMessagesController;
    }
}
