package com.soft.ware.rest;

import com.soft.ware.rest.modular.auth.filter.AuthHandlerMethodArgumentResolver;
import com.soft.ware.rest.modular.auth.filter.PageHandlerMethodArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;


@SpringBootApplication(scanBasePackages = {"com.soft.ware"})
public class PauRestApplication extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        SpringApplication.run(PauRestApplication.class, args);
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new AuthHandlerMethodArgumentResolver());
        argumentResolvers.add(new PageHandlerMethodArgumentResolver());
    }
}
