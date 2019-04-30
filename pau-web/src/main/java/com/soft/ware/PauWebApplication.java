package com.soft.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.soft.ware.mapper"})
public class PauWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(PauWebApplication.class, args);
    }
}
