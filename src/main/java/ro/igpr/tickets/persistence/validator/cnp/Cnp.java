package ro.igpr.tickets.persistence.validator.cnp;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

/**
 * @author Adrian Ber
 * @see https://github.com/beradrian/jcommon/blob/master/src/main/java/net/sf/jcommon/geo/Cnp.java
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CnpValidator.class)
@Documented
public @interface Cnp {
    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    String message() default "cnp.invalid";
}