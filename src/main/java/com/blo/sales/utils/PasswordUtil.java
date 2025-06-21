package com.blo.sales.utils;

import java.security.SecureRandom;
import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {

	private static final SecureRandom secureRandom = new SecureRandom();
	private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	private PasswordUtil() {
	}
	
	// crea el hash de una contraseña
	public static String hashPassword(String password, boolean isCipher) {
		if (!isCipher) {
			return password;
		}
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	// Verifica una contraseña ingresada contra el hash almacenado
	public static boolean checkPassword(String plainPassword, String hashedPassword, boolean ciphered) {
		if (!ciphered) {
			return plainPassword.equals(hashedPassword);
		}
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}

	/**
	 * Genera una cadena alfanumérica segura de longitud especificada.
	 */
	public static String generateAlphanumeric(int length) {
		return generateFromCharset(ALPHANUMERIC, length);
	}
	
	/**
	 * Genera un UUID estándar (versión 4).
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	
	/**
	 * Genera una cadena aleatoria usando un conjunto personalizado de caracteres.
	 */
	private static String generateFromCharset(String charset, int length) {
		if (charset == null || charset.isEmpty()) {
			throw new IllegalArgumentException("Charset must not be empty");
		}

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			int index = secureRandom.nextInt(charset.length());
			sb.append(charset.charAt(index));
		}
		return sb.toString();
	}

	
}
