package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.facade.dto.DtoUserToken;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoUserTokenMapper implements IToOuter<DtoIntUserToken, DtoUserToken> {
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public DtoUserToken toOuter(DtoIntUserToken inner) {
		if (inner == null) {
			return null;
		}
		return mapper.map(inner, DtoUserToken.class);
	}

}
