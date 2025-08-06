package com.blo.sales.dao;

import java.math.BigDecimal;

import com.blo.sales.business.dto.DtoIntCredit;
import com.blo.sales.business.dto.DtoIntCredits;
import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface ICreditsDao {
	
	DtoIntCredits getAllCredits();
	
	DtoIntCredit registerNewCredit(DtoIntCredit credit) throws BloSalesBusinessException;
	
	DtoIntCredit addPayment(String idCredit, DtoIntPartialPyment partialPayment) throws BloSalesBusinessException;
	
	DtoIntCredit updateCreditBasicData(String idCredit, DtoIntCredit credit) throws BloSalesBusinessException;
	
	DtoIntCredit updateAmount(String idCredit, BigDecimal amount) throws BloSalesBusinessException;

}
