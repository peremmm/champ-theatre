package com.ninjaTurtles.champtheatre.bean;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {NameValidator.class})
@Size(max = 75, message = "Name must be no more than 75 characters")
@Pattern(regexp = "^[\\p{L}\\s]+$", message = "Name must contain only letters and spaces")
@ReportAsSingleViolation
public @interface ValidName {

    String message() default "Invalid name";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
