package ro.igpr.tickets.persistence.validator.cnp;

import javax.validation.Payload;

/**
 * Created by vlad4800@gmail.com on 18-Jan-17.
 */
public class Severity {
    public interface Info extends Payload {
    }

    public interface Error extends Payload {
    }
}