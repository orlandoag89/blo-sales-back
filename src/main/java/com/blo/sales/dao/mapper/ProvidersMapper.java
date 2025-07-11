package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntProvider;
import com.blo.sales.business.dto.DtoIntProviders;
import com.blo.sales.dao.docs.Providers;
import com.blo.sales.utils.IToOuter;

@Component
public class ProvidersMapper implements IToOuter<Providers, DtoIntProviders> {
	
	@Autowired
	private ProviderMapper mapper;

	@Override
	public DtoIntProviders toOuter(Providers inner) {
		if (inner == null) {
			return null;
		}
		var out = new DtoIntProviders();
		List<DtoIntProvider> providers = new ArrayList<>();
		
		if (inner.getProviders() != null && !inner.getProviders().isEmpty()) {
			inner.getProviders().forEach(p -> providers.add(mapper.toOuter(p)));
		}
		
		out.setProvider(providers);
		return out;
	}

}
