package com.blo.sales.dao;

import com.blo.sales.business.dto.DtoIntProductsOnSalesCounter;
import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface ISalesDao {
	
	DtoIntSale addSale(DtoIntSale sale) throws BloSalesBusinessException;
	
	DtoIntSales getSales() throws BloSalesBusinessException;
	
	DtoIntSales getSalesOpen() throws BloSalesBusinessException;
	
	DtoIntSales getSalesClose() throws BloSalesBusinessException;
	
	DtoIntSale getSaleById(String id) throws BloSalesBusinessException;
	
	DtoIntSale updateSale(String id, DtoIntSale sale) throws BloSalesBusinessException;
	
	DtoIntSales getSalesNotCashbox() throws BloSalesBusinessException;

	DtoIntProductsOnSalesCounter getBestSellingProducts(int initMonth, int initYear, int endMonth, int endYear) throws BloSalesBusinessException;
}
