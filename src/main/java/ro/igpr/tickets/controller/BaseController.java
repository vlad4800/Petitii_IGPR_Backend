package ro.igpr.tickets.controller;

import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import org.restexpress.Request;
import org.restexpress.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.igpr.tickets.persistence.GenericDao;
import ro.igpr.tickets.persistence.TokenDao;
import ro.igpr.tickets.security.Security;

import java.util.regex.Pattern;

/**
 * Created by Vlad on 13-Nov-14.
 */
public abstract class BaseController {

    protected final static TokenDao tokenDao = TokenDao.getInstance();
    protected final static GenericDao dao = GenericDao.getInstance();
    protected static final UrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();
    protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
    protected final static Pattern paramsSeparatorRegex = Pattern.compile("\\|");

    public BaseController() {

    }

    public Object read(final Request request, final Response response) {
        Security.checkPrivileges(request);
        return null;
    }

    public Object create(final Request request, final Response response) {
        Security.checkPrivileges(request);
        return null;
    }

    public void update(final Request request, final Response response) {
        Security.checkPrivileges(request);
        return;
    }

    public Object readAll(final Request request, final Response response) {
        Security.checkPrivileges(request);
        return null;
    }

    public void delete(final Request request, final Response response) {
        Security.checkPrivileges(request);
        return;
    }
}
