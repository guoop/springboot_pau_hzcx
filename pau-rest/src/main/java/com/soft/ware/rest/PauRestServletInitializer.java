package com.soft.ware.rest;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * pau REST Web程序启动类
 *
 * @author paulo
 * @date 2017年9月29日09:00:42
 */
public class PauRestServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PauRestApplication.class);
    }

}
