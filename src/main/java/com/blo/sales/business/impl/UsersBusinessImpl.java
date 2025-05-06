package com.blo.sales.business.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blo.sales.business.IUsersBusiness;
import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.dao.IUsersDao;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class UsersBusinessImpl implements IUsersBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersBusinessImpl.class);
	
	@Autowired
	private IUsersDao dao;

	@Override
	public DtoIntUserToken login(DtoIntUser user) throws BloSalesBusinessException {
		LOGGER.info(String.format("login with user data %s", Encode.forJava(user.getUsername())));
		return dao.login(user);
	}

	@Override
	public DtoIntUserToken register(DtoIntUser userData) throws BloSalesBusinessException {
		LOGGER.info(String.format("register user data %s", Encode.forJava(userData.getUsername())));
		return dao.register(userData);
	}

	@Override
	public DtoIntUser registerTemporaryPassword(DtoIntUser userData) throws BloSalesBusinessException {
		LOGGER.info(String.format("updating user %s [%s]", Encode.forJava(userData.getUsername()), userData.getId()));
		return dao.registerTemporaryPassword(userData);
	}

	@Override
	public DtoIntUser getUserByName(String username) throws BloSalesBusinessException {
		LOGGER.info(String.format("get user by id %s", Encode.forJava(username)));
		return dao.getUserByName(username);
	}

	@Override
	public DtoIntUser getUserOrNullByName(String username) throws BloSalesBusinessException {
		LOGGER.info(String.format("buscando usuario %s", username));
		return dao.getUserOrNullByName(username);
	}

}
