package com.blo.sales.dao;

import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.service.dto.DtoIntProduct;
import com.blo.sales.service.dto.DtoIntProducts;

public interface IProductsDao {
	
	DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException;
	
	DtoIntProducts getProducts() throws BloSalesBusinessException;
	
	DtoIntProduct getProduct(String productId) throws BloSalesBusinessException;
	
	DtoIntProduct updateProduct(String productId, DtoIntProduct data) throws BloSalesBusinessException;

}
