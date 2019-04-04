package org.pau.web;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * pau Web绋嬪簭鍚姩绫�
 */
public class StartServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    	System.out.println("-------------------start initializer");
        return builder.sources(AppStart.class);
    }
}