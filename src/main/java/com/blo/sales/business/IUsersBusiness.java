package com.blo.sales.business;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface IUsersBusiness {

	DtoIntUserToken login(DtoIntUser user) throws BloSalesBusinessException;

	DtoIntUserToken register(DtoIntUser userData) throws BloSalesBusinessException;
	
	DtoIntUser registerTemporaryPassword(DtoIntUser userData) throws BloSalesBusinessException;
	
	DtoIntUser getUserByName(String userName) throws BloSalesBusinessException;
	
	DtoIntUser getUserOrNullByName(String username) throws BloSalesBusinessException;

	DtoIntUser updatePassword(DtoIntUser userData) throws BloSalesBusinessException;
}
