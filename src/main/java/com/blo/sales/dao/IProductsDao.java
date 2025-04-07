package com.blo.sales.dao;

import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.service.dto.DtoIntProducts;

public interface IProductsDao {
	
	DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException;

}
