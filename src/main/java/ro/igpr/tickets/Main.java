package ro.igpr.tickets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restexpress.util.Environment;
import ro.igpr.tickets.config.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.util.TimeZone;

/**
 * Created by vlad4800@gmail.com on 16-Jan-17.
 */
public class Main {

    private static final Logger LOG = LogManager.getLogger("");

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {

        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));

        String processId = ManagementFactory.getRuntimeMXBean().getName();
        String mavenProfile = args.length > 0 ? args[0] : Configuration.getEnvironmentName();
        LOG.debug("Starting API process with PID [" + processId + "] and profile [" + mavenProfile + "]");

        Configuration config = loadEnvironment(args);
        Server server = new Server(config);

        LOG.debug("STARTED THE API SERVER");

        server.start().awaitShutdown();
    }

    private final static Configuration loadEnvironment(String[] args)
            throws FileNotFoundException, IOException {

        Configuration config = new Configuration();
        if (args.length > 0) {
            config.setEnvironmentName(args[0]);
            config = Environment.from(args[0], Configuration.class);

        } else {
            config = Environment.fromDefault(Configuration.class);
        }
        return config;
    }
}
