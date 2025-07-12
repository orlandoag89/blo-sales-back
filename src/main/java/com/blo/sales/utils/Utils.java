package com.blo.sales.utils;

import java.math.BigDecimal;
import java.time.Instant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.blo.sales.exceptions.BloSalesBusinessException;

public final class Utils {
	
	public static final String EMPTY_STRING = "";
	
	public static final BigDecimal LIMIT_FROM_PRODUCTS_NOT_KG = new BigDecimal(2);
	
	public static final BigDecimal LIMIT_FROM_PRODUCTS_KG = new BigDecimal("0.200");
	
	private static final String UNDEFINED = "undefined";
	
	private Utils() { }
	
	/**
	 * metodo para recuperar el tiempo en mili segundos
	 * @return la fecha actual en milisegundos
	 */
	public static long getTimeNow() {
		return Instant.now().toEpochMilli();
	}
	
	public static boolean includesUndefined(String input) {
		return StringUtils.contains(UNDEFINED, input.toLowerCase());
	}
	
	/**
	 * 
	 * Valida que un string no sea válida o sea undefined
	 * 
	 * @param str string a evaluar
	 * @param errMsg mensaje de error
	 * @param errCod código de error
	 * 
	 * @throws BloSalesBusinessException
	 */
	public static void isStringIsBlankOrUndefined(String str, String errMsg, String errCod) throws BloSalesBusinessException {
		if (StringUtils.isBlank(str) || Utils.includesUndefined(str)) {
			throw new BloSalesBusinessException(errMsg, errCod, HttpStatus.BAD_REQUEST);
		}
	}
}
