package com.blo.sales.dao;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface IUsersDao {
	
	DtoIntUserToken register(DtoIntUser userData) throws BloSalesBusinessException;
	
	DtoIntUserToken login(DtoIntUser user) throws BloSalesBusinessException;
	
	DtoIntUser getUserByName(String username) throws BloSalesBusinessException;
	
	DtoIntUser getUserOrNullByName(String username) throws BloSalesBusinessException;
	
	DtoIntUser registerTemporaryPassword(DtoIntUser userData) throws BloSalesBusinessException;
	
	DtoIntUser updatePassword(DtoIntUser userData) throws BloSalesBusinessException;

}
