package com.blo.sales.dao;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface IUsersDao {
	
	DtoIntUserToken register(DtoIntUser userData) throws BloSalesBusinessException;

}
