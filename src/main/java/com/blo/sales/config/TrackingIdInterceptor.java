package com.blo.sales.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class TrackingIdInterceptor implements HandlerInterceptor {

	 private static final String TRACKING_HEADER = "X-Tracking-Id";
	 
	 private static final Logger LOGGER = LoggerFactory.getLogger(TrackingIdInterceptor.class);
	 
	 @Override
	    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
	        var trackingId = request.getHeader(TRACKING_HEADER);

	        if (trackingId != null && !trackingId.isEmpty()) {
	            // Puedes guardar el trackingId en el logger context (MDC)
	            MDC.put("trackingId", trackingId);
	            LOGGER.info(String.format("Tracking ID: [%s]", trackingId));
	        } else {
	            LOGGER.info("Tracking ID not provided");
	        }

	        return true; // continuar con el flujo normal
	    }

	    @Override
	    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
	        // Limpia el contexto del logger para evitar contaminación entre hilos
	        MDC.remove("trackingId");
	    }
}
