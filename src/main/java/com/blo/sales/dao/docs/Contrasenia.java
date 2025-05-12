package com.blo.sales.dao.docs;

import java.io.Serializable;

import lombok.Data;

public @Data class Contrasenia implements Serializable {

	private static final long serialVersionUID = -7644026721704835423L;
	
	private String password; // contraseña
	
	private boolean process_reset; // bandera que indica si fue generada en un proceso de reseteo
	
	private long created_date; // fecha de creación

}
