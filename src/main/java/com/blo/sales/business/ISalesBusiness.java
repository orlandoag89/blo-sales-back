package com.blo.sales.business;

import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface ISalesBusiness {
	
	//DtoIntWrapperSale saveSaleAndDebtor(DtoIntSale sale, DtoIntDebtor debtor, BigDecimal partialPyment) throws BloSalesBusinessException;
	
	DtoIntSale addSale(DtoIntSale sale) throws BloSalesBusinessException;
	
	DtoIntSales getSales() throws BloSalesBusinessException;
	
	DtoIntSales getSalesOpen() throws BloSalesBusinessException;
	
	DtoIntSales getSalesClose() throws BloSalesBusinessException;
	
	DtoIntSale getSaleById(String id) throws BloSalesBusinessException;
	
	DtoIntSale updateSale(String id, DtoIntSale sale) throws BloSalesBusinessException;
	
	DtoIntSales getSalesNotCashbox() throws BloSalesBusinessException;

}
