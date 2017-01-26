package ro.igpr.tickets.pipeline;

import io.netty.handler.codec.http.HttpHeaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import ro.igpr.tickets.config.Configuration;
import ro.igpr.tickets.config.Constants;
import ro.igpr.tickets.util.CommonUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class ConsoleLogMessageObserver extends SimpleConsoleLogMessageObserver {

    private static final Logger LOG = LogManager.getLogger("");
    private final Map<String, Timer> timers = new ConcurrentHashMap<String, Timer>();


    @Override
    protected final void onReceived(final Request request, final Response response) {
        timers.put(request.getCorrelationId(), new Timer());
    }

    @Override
    protected final void onException(Throwable exception, final Request request, final Response response) {

        final StringBuilder sb = new StringBuilder();
        sb.append(Constants.ConsoleLog.IP).append(CommonUtil.getIp(request)).append(Constants.ConsoleLog.SPACE);
        sb.append(Constants.ConsoleLog.TOKEN).append(request.getHeader(HttpHeaders.Names.AUTHORIZATION)).append(Constants.ConsoleLog.SPACE);
        sb.append(Constants.ConsoleLog.METHOD).append(request.getEffectiveHttpMethod().toString()).append(Constants.ConsoleLog.SPACE);
        sb.append(Constants.ConsoleLog.URL).append(request.getUrl()).append(Constants.ConsoleLog.SPACE);
        sb.append(Constants.ConsoleLog.THREW_EXCEPTION).append(exception.getClass().getSimpleName());
        sb.append(Constants.ConsoleLog.WITH_MESSAGE).append(exception.getMessage());
        sb.append(Constants.ConsoleLog.GZIP_SUPPORT).append(request.getHeader(HttpHeaders.Names.ACCEPT_ENCODING));

        LOG.error(sb.toString());
        if (!Configuration.getEnvironmentName().equals(Constants.ConsoleLog.PROD)) {
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
        sb.append(Constants.ConsoleLog.IP).append(CommonUtil.getIp(request)).append(Constants.ConsoleLog.SPACE);
        sb.append(Constants.ConsoleLog.TOKEN).append(request.getHeader(HttpHeaders.Names.AUTHORIZATION));
        sb.append(Constants.ConsoleLog.SPACE);
        sb.append(request.getEffectiveHttpMethod().toString());
        sb.append(Constants.ConsoleLog.SPACE);
        sb.append(request.getUrl());

        if (timer != null) {
            sb.append(Constants.ConsoleLog.RESPONDED_WITH);
            sb.append(response.getResponseStatus().toString());
            sb.append(Constants.ConsoleLog.IN);
            sb.append(timer.toString());
        } else {
            sb.append(Constants.ConsoleLog.RESPONDED_WITH);
            sb.append(response.getResponseStatus().toString());
            sb.append(Constants.ConsoleLog.NO_TIMER_FOUND);
        }
        sb.append(Constants.ConsoleLog.SPACE).append(request.getHeader(HttpHeaders.Names.USER_AGENT));
        sb.append(Constants.ConsoleLog.GZIP_SUPPORT).append(request.getHeader(HttpHeaders.Names.ACCEPT_ENCODING));

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

            return String.valueOf(stopTime - startMillis) + Constants.ConsoleLog.MS;
        }
    }
}
