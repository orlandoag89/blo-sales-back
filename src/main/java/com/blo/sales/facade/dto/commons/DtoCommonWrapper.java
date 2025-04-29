package com.blo.sales.facade.dto.commons;

import java.io.Serializable;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class DtoCommonWrapper<T> implements Serializable {

	private static final long serialVersionUID = 4197587736064640309L;

	private DtoError error;
	
	private T data;
}
