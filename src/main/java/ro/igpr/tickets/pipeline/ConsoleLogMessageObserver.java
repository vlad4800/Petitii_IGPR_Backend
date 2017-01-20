package ro.igpr.tickets.pipeline;

import io.netty.handler.codec.http.HttpHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import ro.igpr.tickets.config.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * Created by Vlad on 24-Dec-14.
 */
public final class ConsoleLogMessageObserver extends SimpleConsoleLogMessageObserver {

    private static final Logger LOG = LogManager.getLogger("");
    private final Map<String, Timer> timers = new ConcurrentHashMap<String, Timer>();
    protected final static Pattern commaRegex = Pattern.compile("\\,");

    @Override
    protected final void onReceived(final Request request, final Response response) {
        timers.put(request.getCorrelationId(), new Timer());
    }

    @Override
    protected final void onException(Throwable exception, final Request request, final Response response) {

        final StringBuilder sb = new StringBuilder();
        sb.append(Constants.IP).append(getIp(request)).append(Constants.SPACE);
        sb.append(Constants.TOKEN).append(request.getHeader(HttpHeaders.Names.AUTHORIZATION)).append(Constants.SPACE);
        sb.append(Constants.METHOD).append(request.getEffectiveHttpMethod().toString()).append(Constants.SPACE);
        sb.append(Constants.URL).append(request.getUrl()).append(Constants.SPACE);
        sb.append(Constants.THREW_EXCEPTION).append(exception.getClass().getSimpleName());
        sb.append(Constants.WITH_MESSAGE).append(exception.getMessage());
        sb.append(Constants.GZIP_SUPPORT).append(request.getHeader(HttpHeaders.Names.ACCEPT_ENCODING));

        LOG.error(sb.toString());
        if (!Configuration.getEnvironmentName().equals(Constants.PROD)) {
            exception.printStackTrace();
        }
    }

    @Override
    protected final void onComplete(final Request request, final Response response) {
        final Timer timer = timers.remove(request.getCorrelationId());
        if (timer != null) {
            timer.stop();
        }

        final StringBuilder sb = new StringBuilder();
        sb.append(Constants.IP).append(getIp(request)).append(Constants.SPACE);
        sb.append(Constants.TOKEN).append(request.getHeader(HttpHeaders.Names.AUTHORIZATION));
        sb.append(Constants.SPACE);
        sb.append(request.getEffectiveHttpMethod().toString());
        sb.append(Constants.SPACE);
        sb.append(request.getUrl());

        if (timer != null) {
            sb.append(Constants.RESPONDED_WITH);
            sb.append(response.getResponseStatus().toString());
            sb.append(Constants.IN);
            sb.append(timer.toString());
        } else {
            sb.append(Constants.RESPONDED_WITH);
            sb.append(response.getResponseStatus().toString());
            sb.append(Constants.NO_TIMER_FOUND);
        }
        sb.append(Constants.SPACE).append(request.getHeader(HttpHeaders.Names.USER_AGENT));
        sb.append(Constants.GZIP_SUPPORT).append(request.getHeader(HttpHeaders.Names.ACCEPT_ENCODING));

        LOG.info(sb.toString());
    }

    private final class Timer {
        private long startMillis = 0;
        private long stopMillis = 0;

        public Timer() {
            super();
            this.startMillis = System.currentTimeMillis();
        }

        public final void stop() {
            this.stopMillis = System.currentTimeMillis();
        }

        public final String toString() {
            final long stopTime = (stopMillis == 0 ? System.currentTimeMillis() : stopMillis);

            return String.valueOf(stopTime - startMillis) + Constants.MS;
        }
    }

    private final static String getIp(Request request) {
        String ip = "";
        if (request.getHeader(Constants.X_FORWARDED_FOR) != null) {
            ip = request.getHeader(Constants.X_FORWARDED_FOR);
        } else {
            ip = request.getRemoteAddress().toString();
        }

        if (ip.indexOf(Constants.COMMA) >= 0)
            ip = commaRegex.split(ip)[0];

        return ip;
    }

    private class Constants {
        public final static String IP = "IP:";
        public final static String TOKEN = "TOKEN:";
        public final static String METHOD = "METHOD:";
        public final static String URL = "URL:";
        public final static String THREW_EXCEPTION = " threw exception: ";
        public final static String WITH_MESSAGE = " with message: ";
        public final static String GZIP_SUPPORT = " | Gzip support: ";
        public final static String SPACE = " ";
        public final static String RESPONDED_WITH = " responded with ";
        public final static String IN = " in ";
        public final static String NO_TIMER_FOUND = " (no timer found)";
        public final static String X_FORWARDED_FOR = "x-forwarded-for";
        public final static String COMMA = ",";
        public final static String PROD = "prod";
        public final static String MS = "ms";
    }
}
