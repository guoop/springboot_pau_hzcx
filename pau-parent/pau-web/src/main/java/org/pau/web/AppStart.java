package org.pau.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class AppStart 
{
	
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory
			.getLogger(AppStart.class);
	
    public static void main( String[] args )
    {
    	SpringApplication.run(AppStart.class, args);
    }
}
