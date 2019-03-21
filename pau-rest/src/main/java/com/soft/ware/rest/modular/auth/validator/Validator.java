package com.soft.ware.rest.modular.auth.validator;

import com.soft.ware.core.exception.PauException;
import com.soft.ware.rest.common.exception.BizExceptionEnum;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

public class Validator {

    public static void valid(BindingResult result){
        if (result.hasErrors()) {
            FieldError error = result.getFieldError();
            throw new PauException(BizExceptionEnum.PARAME_ERROR);
        }
    }

}
