package com.blo.sales.utils;

import java.time.Instant;

public final class Utils {
	
	/**
	 * metodo para recuperar el tiempo en mili segundos
	 * @return la fecha actual en milisegundos
	 */
	public static long getTimeNow() {
		return Instant.now().toEpochMilli();
	}

}
