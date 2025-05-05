package com.blo.sales.business;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface IUsersBusiness {

	DtoIntUserToken login(DtoIntUser user) throws BloSalesBusinessException;

	DtoIntUserToken register(DtoIntUser userData) throws BloSalesBusinessException;
	
	DtoIntUser resetUser(DtoIntUser rootUserData, String idUser) throws BloSalesBusinessException;
	
	DtoIntUser updateUser(DtoIntUser user) throws BloSalesBusinessException;
	
	DtoIntUser getUserByIdOrNull(String idUser) throws BloSalesBusinessException;

	DtoIntUser getUserByNameOrNull(String username) throws BloSalesBusinessException;
}
