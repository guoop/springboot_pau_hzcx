package com.soft.ware.rest.modular.auth.filter;

import com.soft.ware.core.support.HttpKit;
import com.soft.ware.rest.modular.auth.controller.dto.Customer;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class ConstomerUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {



    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType() == Customer.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = HttpKit.getRequest();
        String referer = request.getHeader("Referer");
        if (request.getServletPath().startsWith("/customer")) {
            String[] split = referer.split("/");
            String domain = split[2];
            String appId = split[3];
            Customer c = new Customer();
            c.setOpenId("");
            c.setOwner("16d0a4b0dcd411e8b2e187bf6b98e5cd");
            return c;
        }
        return null;
    }


}
