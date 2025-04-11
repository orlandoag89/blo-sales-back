package com.blo.sales.dao;

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface ICashboxesDao {
	
	DtoIntCashbox addCashbox(DtoIntCashbox cashbox);
	
	DtoIntCashboxes getAllCashboxes();
	
	DtoIntCashbox getCashboxOpen();
	
	DtoIntCashboxes getCashboxesClose();
	
	DtoIntCashbox updateCashbox(String id, DtoIntCashbox cashbox) throws BloSalesBusinessException;
	
}
