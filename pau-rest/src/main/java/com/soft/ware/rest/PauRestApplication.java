package com.soft.ware.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"com.soft.ware"})
public class PauRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(PauRestApplication.class, args);
    }
}
