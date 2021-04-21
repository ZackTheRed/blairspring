package com.blair.blairspring.model.validation;

import com.blair.blairspring.model.userschema.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BeforeDeleteUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getUsername().equals("to_be_deleted")) {
            errors.reject("myerrorcode", "some custom error message");
        }
    }

}
