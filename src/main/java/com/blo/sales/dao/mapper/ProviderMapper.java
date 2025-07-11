package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProvider;
import com.blo.sales.dao.docs.Provider;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class ProviderMapper implements IToInner<Provider, DtoIntProvider>, IToOuter<Provider, DtoIntProvider> {

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Provider toInner(DtoIntProvider outer) {
		if (outer == null) {
			return null;
		}
		return modelMapper.map(outer, Provider.class);
	}

	@Override
	public DtoIntProvider toOuter(Provider inner) {
		if (inner == null) {
			return null;
		}
		return modelMapper.map(inner, DtoIntProvider.class);
	}
}
