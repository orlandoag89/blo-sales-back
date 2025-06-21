package com.blo.sales.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MongoUriLogger {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MongoUriLogger.class);
	
    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @PostConstruct
    public void log() {
    	LOGGER.info(String.format("URI MONGO EN USO %s", uri));
    }
    
}
