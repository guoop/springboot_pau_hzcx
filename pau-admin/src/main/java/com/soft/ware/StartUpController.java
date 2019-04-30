package com.soft.ware;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class StartUpController {
	
	private final static Logger logger = LoggerFactory
			.getLogger(StartUpController.class);

	public static void main(String[] args) {
		SpringApplication.run(StartUpController.class, args);
		logger.info("StartUpController is success!");
	}
}
