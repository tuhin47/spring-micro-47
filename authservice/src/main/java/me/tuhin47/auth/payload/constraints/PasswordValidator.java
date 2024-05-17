package me.tuhin47.auth.payload.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, CharSequence> {

    public static final String VALID_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$";

    @Override
    public boolean isValid(CharSequence password, ConstraintValidatorContext context) {
        if (password == null) {
            return false;
        }
//        return password.toString().matches(VALID_PASSWORD_PATTERN);
        return password.length() > 5;
    }
}