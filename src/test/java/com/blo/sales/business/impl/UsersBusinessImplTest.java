package com.blo.sales.business.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.dao.IUsersDao;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class UsersBusinessImplTest {
	
	@Mock
	private IUsersDao dao;
	
	@InjectMocks
	private UsersBusinessImpl impl;

	@Test
	public void loginTest() throws BloSalesBusinessException {
		Mockito.when(dao.login(Mockito.any())).thenReturn(MocksFactory.createDtoIntUserToken());
		
		var out = impl.login(MocksFactory.createDtoIntCommonUser());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).login(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getToken());
	}

	@Test
	public void registerTest() throws BloSalesBusinessException {
		Mockito.when(dao.register(Mockito.any())).thenReturn(MocksFactory.createDtoIntUserToken());
		
		var out = impl.register(MocksFactory.createDtoIntCommonUser());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).register(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getToken());
	}

	@Test
	public void registerTemporaryPasswordTest() throws BloSalesBusinessException {
		Mockito.when(dao.registerTemporaryPassword(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var out = impl.registerTemporaryPassword(MocksFactory.createDtoIntCommonUser());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).registerTemporaryPassword(Mockito.any());
		
		assertNotNull(out);
	}

	@Test
	public void getUserByNameTest() throws BloSalesBusinessException {
		Mockito.when(dao.getUserByName(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var out = impl.getUserByName(MocksFactory.getAnyString());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getUserByName(Mockito.anyString());
		
		assertNotNull(out);
	}

	@Test
	public void getUserOrNullByNameTest() throws BloSalesBusinessException {
		Mockito.when(dao.getUserOrNullByName(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var out = impl.getUserOrNullByName(MocksFactory.getAnyString());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getUserOrNullByName(Mockito.anyString());
		
		assertNotNull(out);
	}

	@Test
	public void updatePasswordTest() throws BloSalesBusinessException {
		Mockito.when(dao.updatePassword(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var out = impl.updatePassword(MocksFactory.createDtoIntCommonUser());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).updatePassword(Mockito.any());
		
		assertNotNull(out);
	}

}
