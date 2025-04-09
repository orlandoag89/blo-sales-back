package com.blo.sales.business;

import java.math.BigDecimal;

import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntDebtors;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface IDebtorsBusiness {

	DtoIntDebtor addDebtor(DtoIntDebtor debtor) throws BloSalesBusinessException;
	
	DtoIntDebtors getDebtors() throws BloSalesBusinessException;
	
	DtoIntDebtor getDebtorById(String id) throws BloSalesBusinessException;
	
	void deleteDebtorById(String id) throws BloSalesBusinessException;
	
	DtoIntDebtor updateDebtor(String id, DtoIntDebtor debtor) throws BloSalesBusinessException;
	
	DtoIntDebtor addPayment(BigDecimal partialPyment, String id) throws BloSalesBusinessException;
	
}
