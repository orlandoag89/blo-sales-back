package com.blo.sales.factory;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;
import org.springframework.test.web.servlet.MvcResult;

import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class MocksUtils {
	
	public static final String EMPTY_STRING = "";
	public static final BigDecimal BIG_DECIMAL_00 = new BigDecimal("0");
	public static final BigDecimal BIG_DECIMAL_10 = new BigDecimal("10");
	public static final BigDecimal BIG_DECIMAL_100 = new BigDecimal("100");
	public static final BigDecimal BIG_DECIMAL_3 = new BigDecimal("3");
	public static final BigDecimal BIG_DECIMAL_30 = new BigDecimal("30");
	public static final BigDecimal BIG_DECIMAL_70 = new BigDecimal("70");
	
	private static ObjectMapper objectMapper = new ObjectMapper();
	
	public static String getContentAsString(MvcResult result, String fromMethod) throws UnsupportedEncodingException {
		if (result == null) {
			throw new UnsupportedEncodingException("[" + fromMethod + "] result is null");
		}
		var contentString = result.getResponse().getContentAsString();
		if (StringUtils.isBlank(contentString)) {
			System.err.println("[" + fromMethod + "] contentString is empty");
			return EMPTY_STRING;
		}
		System.out.println("[" + fromMethod + "] " + contentString);
		return contentString;
	}

	public static <T> T parserStringTo(String content, Class<T> type) throws Exception {
		System.out.println(content);
		return objectMapper.readValue(content, type); 
	}
	
	public static <T> DtoCommonWrapper<T> parserToCommonWrapper(String json, TypeReference<DtoCommonWrapper<T>> typeRef)
	        throws JsonProcessingException {
	    return objectMapper.readValue(json, typeRef);
	}
	
	public static String parserToString(Object val) throws Exception {
		return objectMapper.writeValueAsString(val);
	}
}
