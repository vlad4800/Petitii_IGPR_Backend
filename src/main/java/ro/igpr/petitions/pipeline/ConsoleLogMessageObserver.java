package ro.igpr.petitions.pipeline;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Vlad on 24-Dec-14.
 */
public final class ConsoleLogMessageObserver extends SimpleConsoleLogMessageObserver {

    private static final Logger LOG = LogManager.getLogger("");
    private final Map<String, Timer> timers = new ConcurrentHashMap<String, Timer>();

    @Override
    protected final void onReceived(final Request request, final Response response) {
        timers.put(request.getCorrelationId(), new Timer());
    }

    @Override
    protected final void onException(Throwable exception, final Request request, final Response response) {
        final StringBuffer sb = new StringBuffer();
        sb.append("IP: ").append(request.getRemoteAddress()).append(" ");
        sb.append("TOKEN:").append(request.getHeader("Authorization")).append(" ");
        sb.append("METHOD:").append(request.getEffectiveHttpMethod().toString()).append(" ");
        sb.append("URL:").append(request.getUrl()).append(" ");
//        sb.append("\n\rBODY: ").append(new String(request.getBodyAsBytes())).append(" ");
        sb.append(" threw exception: ").append(exception.getClass().getSimpleName());
        sb.append(" with message: ").append(exception.getMessage());

        sb.append(" | Gzip support: ").append(request.getHeader("Accept-Encoding"));


        if (response.getResponseStatus().code() == 500) {
            StackTraceElement[] trace = exception.getStackTrace();

            sb.append("\nexception: ");
            for (StackTraceElement elem : trace) {
                sb.append("\nat " + elem);
            }
            exception.printStackTrace();
        }
        LOG.error(sb.toString());
        exception.printStackTrace();
//        if (!Server.getInstance().getConfig().getEnvironmentName().equals("prod")) {
//            exception.printStackTrace();
//        }
    }

    public static String slurp(final InputStream is, final int bufferSize) {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            for (; ; ) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0)
                    break;
                out.append(buffer, 0, rsz);
            }
        } catch (UnsupportedEncodingException ex) {
        /* ... */
        } catch (IOException ex) {
        /* ... */
        }
        return out.toString();
    }

    @Override
    protected final void onComplete(final Request request, final Response response) {
        final Timer timer = timers.remove(request.getCorrelationId());
        if (timer != null) timer.stop();


        final StringBuffer sb = new StringBuffer();
        sb.append("IP: ").append(request.getRemoteAddress()).append(" ");
        sb.append("TOKEN:").append(request.getHeader("Authorization"));
        sb.append(" ");
        sb.append(request.getEffectiveHttpMethod().toString());
        sb.append(" ");
        sb.append(request.getUrl());

        if (timer != null) {
            sb.append(" responded with ");
            sb.append(response.getResponseStatus().toString());
            sb.append(" in ");
            sb.append(timer.toString());
        } else {
            sb.append(" responded with ");
            sb.append(response.getResponseStatus().toString());
            sb.append(" (no timer found)");
        }
        sb.append(" ").append(request.getHeader("User-Agent"));

        sb.append(" | Gzip support: ").append(request.getHeader("Accept-Encoding"));

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

            return String.valueOf(stopTime - startMillis) + "ms";
        }
    }
}
