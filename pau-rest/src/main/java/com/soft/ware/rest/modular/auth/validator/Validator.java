package com.soft.ware.rest.modular.auth.validator;

import com.soft.ware.core.exception.ParameterException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class Validator {

    public static void valid(BindingResult result){
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            throw new ParameterException(error.getField() + error.getDefaultMessage());
        }
    }

}
