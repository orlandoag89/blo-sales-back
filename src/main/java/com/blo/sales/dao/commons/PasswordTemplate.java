package com.blo.sales.dao.commons;

import com.blo.sales.dao.docs.Contrasenia;
import com.blo.sales.utils.Utils;

public final class PasswordTemplate {
	
	private PasswordTemplate() { }
	
	public static Contrasenia generatePasswordTemplate(String password) {
		var contrasenia = new Contrasenia();
		contrasenia.setCreated_date(Utils.getTimeNow());
		contrasenia.setProcess_reset(false);
		contrasenia.setPassword(password);
		return contrasenia;
	}

	public static Contrasenia generatePasswordTemplateResetting(String password) {
		var pass = generatePasswordTemplate(password);
		pass.setProcess_reset(true);
		return pass;
	}
}
