package ro.igpr.tickets.util;

import org.restexpress.Request;
import ro.igpr.tickets.config.Constants;

import java.util.regex.Pattern;

public class CommonUtil {
    protected final static Pattern commaRegex = Pattern.compile("\\,");

    public final static String getIp(Request request) {
        String ip = "";
        if (request.getHeader(Constants.ConsoleLog.X_FORWARDED_FOR) != null) {
            ip = request.getHeader(Constants.ConsoleLog.X_FORWARDED_FOR);
        } else {
            ip = request.getRemoteAddress().toString();
        }

        if (ip.indexOf(Constants.ConsoleLog.COMMA) >= 0)
            ip = commaRegex.split(ip)[0];

        return ip;
    }
}
