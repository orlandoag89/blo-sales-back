package com.blo.sales.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class TrackingIdConfig implements WebMvcConfigurer {

	@Autowired
    private TrackingIdInterceptor trackingIdInterceptor;

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(trackingIdInterceptor);
    }
}
