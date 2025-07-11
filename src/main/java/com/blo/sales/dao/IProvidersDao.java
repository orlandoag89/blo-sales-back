package com.blo.sales.dao;

import com.blo.sales.business.dto.DtoIntProvider;
import com.blo.sales.business.dto.DtoIntProviders;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface IProvidersDao {

	DtoIntProvider registerNewProvider(DtoIntProvider provider);

	DtoIntProviders getAllProviders();
	
	DtoIntProvider getProviderById(String id) throws BloSalesBusinessException;
	
	DtoIntProvider updateProvider(String id, DtoIntProvider provider) throws BloSalesBusinessException;
	
	void deleteProvider(String id) throws BloSalesBusinessException;
}
