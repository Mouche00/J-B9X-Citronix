package org.citronix.utils.validation.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.citronix.utils.validation.annotations.impl.ExistsImpl;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Exists {
    String message() default "The referenced entity does not exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    Class<?> entity();
    String field() default "id";
}
