package com.blo.sales.utils;

import java.time.Instant;

import org.apache.commons.lang3.StringUtils;

public final class Utils {
	
	private Utils() { }
	
	private static final String UNDEFINED = "undefined"; 
	
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
}
