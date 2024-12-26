package me.tuhin47.auth.payload.constraints;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PasswordValidator implements ConstraintValidator<ValidPassword, byte[]> {

    public static final String VALID_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,}$";

    @Override
    public boolean isValid(byte[] password, ConstraintValidatorContext context) {
        if (password == null) {
            log.warn("Password in null. Ignored PasswordValidator");
            return true;
        }
//        return password.toString().matches(VALID_PASSWORD_PATTERN);
        return password.length > 5;
    }
}