package ro.igpr.tickets.exception;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.restexpress.exception.ServiceException;

public class GoneException extends ServiceException {

    private static final long serialVersionUID = -165901445338837176L;

    public GoneException() {
        super(HttpResponseStatus.GONE);
    }

    /**
     * @param message
     */
    public GoneException(String message) {
        super(HttpResponseStatus.GONE, message);
    }

    /**
     * @param cause
     */
    public GoneException(Throwable cause) {
        super(HttpResponseStatus.GONE, cause);
    }

    /**
     * @param message
     * @param cause
     */
    public GoneException(String message, Throwable cause) {
        super(HttpResponseStatus.GONE, message, cause);
    }
}