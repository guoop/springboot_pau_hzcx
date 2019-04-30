package com.soft.ware.exceptionHandler;

import com.soft.ware.core.exception.PauException;
import com.soft.ware.rest.MyRestResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(PauException.class)
    public @ResponseBody
    MyRestResponse restExceptionHandler(PauException e) {
        /*e.printStackTrace();*/
        /*if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }*/
        return new MyRestResponse(e.getCode(), e.getMessage());
    }
}
