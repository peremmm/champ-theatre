package com.ninjaTurtles.champtheatre.bean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    public void initialize(ValidName constraint) {
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        String[] names = value.split("\\s+");
        if (names.length != 1 && names.length != 2) {
            return false;
        }
        for (String name : names) {
            if (!name.matches("^[\\p{L}]+$")) {
                return false;
            }
        }
        return true;
    }
}
