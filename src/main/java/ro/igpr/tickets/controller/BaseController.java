package ro.igpr.tickets.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import org.restexpress.Request;
import org.restexpress.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ro.igpr.tickets.persistence.GenericDao;

import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * Created by Vlad on 13-Nov-14.
 */
public abstract class BaseController {

    protected final static GenericDao dao = GenericDao.getInstance();
    protected final static ObjectMapper mapper = new ObjectMapper();
    protected static final UrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();
    protected static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
    private final static String EMPTY_STRING = "";
    protected final static Pattern paramsSeparatorRegex = Pattern.compile("\\|");

    static {
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    public BaseController() {

    }

    public Object read(final Request request, final Response response) {
        return null;
    }

    public Object create(final Request request, final Response response) {
        return null;
    }

    public void update(final Request request, final Response response) {
    }

    public Object readAll(final Request request, final Response response) {
        return null;
    }

    public void delete(final Request request, final Response response) {
        return;
    }

    public final static ObjectMapper getMapper() {
        return mapper;
    }

}
