package com.blo.sales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.blo.sales")
@SpringBootApplication
public class SalesApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SalesApplication.class, args);
		LOGGER.info("App up!");
	}

}
