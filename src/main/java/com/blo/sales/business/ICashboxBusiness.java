package com.blo.sales.business;

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface ICashboxBusiness {
	
	DtoIntCashbox saveCashbox(DtoIntCashbox cashbox);
	
	DtoIntCashbox getCashboxOpen();
	
	DtoIntCashboxes getAllCashboxes();

	DtoIntCashbox updateCashbox(String id, DtoIntCashbox cashbox) throws BloSalesBusinessException;
	
}
