package me.tuhin47.auth.payload.constraints;

import me.tuhin47.auth.payload.request.SignUpRequest;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, SignUpRequest> {

    @Override
    public boolean isValid(final SignUpRequest user, final ConstraintValidatorContext context) {
        return Arrays.equals(user.getPassword(), user.getMatchingPassword());
    }

}
