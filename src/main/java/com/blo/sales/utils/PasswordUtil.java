package com.blo.sales.utils;

import org.mindrot.jbcrypt.BCrypt;

public final class PasswordUtil {
	
	private PasswordUtil() { }
	
	// crea el hash de una contraseña
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	 // Verifica una contraseña ingresada contra el hash almacenado
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
