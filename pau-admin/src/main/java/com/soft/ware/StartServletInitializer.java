package com.soft.ware;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * pau Web程序启动类
 */
public class StartServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	System.out.println("-------------------start initializer");
        return builder.sources(StartUpController.class);
    }
}