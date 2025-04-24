package com.blo.sales.business.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.dao.IDebtorsDao;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class DebtorsServiceImplTest {
	
	@Mock
	private IDebtorsDao dao;
	
	@InjectMocks
	private DebtorsService service;

	@Test
	public void addDebtorTest() throws BloSalesBusinessException {
		Mockito.when(dao.addDebtor(Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = service.addDebtor(MocksFactory.createNewDtoIntDebtor());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).addDebtor(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getId());
	}
	
	@Test
	public void getDebtorsTest() throws BloSalesBusinessException {
		Mockito.when(dao.getDebtors()).thenReturn(MocksFactory.createDtoIntDebtors());
		
		var out = service.getDebtors();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getDebtors();
		
		assertNotNull(out);
		assertFalse(out.getDebtors().isEmpty());
	}
	
	@Test
	public void getDebtorByIdTest() throws BloSalesBusinessException {
		Mockito.when(dao.getDebtorById(Mockito.anyString())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = service.getDebtorById(MocksFactory.getId());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getDebtorById(Mockito.anyString());
		
		assertNotNull(out);
		assertNotNull(out.getId());
	}
	
	@Test
	public void deleteDebtorByIdTest() throws BloSalesBusinessException {
		Mockito.doNothing().when(dao).deleteDebtorById(Mockito.anyString());
		
		service.deleteDebtorById(MocksFactory.getId());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).deleteDebtorById(Mockito.anyString());
	}
	
	@Test
	public void updateDebtorTest() throws BloSalesBusinessException {
		Mockito.when(dao.updateDebtor(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = service.updateDebtor(MocksFactory.getId(), MocksFactory.createExistsDtoIntDebtor());
		
		Mockito.verify(dao, Mockito.atLeast(1)).updateDebtor(Mockito.anyString(), Mockito.any());
		
		assertNotNull(out);
	}
	
	@Test
	public void getDebtorOrNullTest() throws BloSalesBusinessException {
		Mockito.when(dao.getDebtorOrNull(Mockito.anyString())).thenReturn(null);
		
		var out = service.getDebtorOrNull(MocksFactory.getId());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getDebtorOrNull(Mockito.anyString());
		
		assertNull(out);
	}
	
	@Test
	public void getDebtorOrNull2Test() throws BloSalesBusinessException {
		Mockito.when(dao.getDebtorOrNull(Mockito.anyString())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = service.getDebtorOrNull(MocksFactory.getId());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getDebtorOrNull(Mockito.anyString());
		
		assertNotNull(out);
	}
	
	@Test
	public void addPayTest() throws BloSalesBusinessException {
		Mockito.when(dao.updateDebtor(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		Mockito.when(dao.getDebtorById(Mockito.anyString())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = service.addPay(MocksFactory.getId(), MocksFactory.createDtoIntPartialPyment(), MocksFactory.getNowDate());
		
		Mockito.verify(dao, Mockito.atLeast(1)).updateDebtor(Mockito.anyString(), Mockito.any());
		Mockito.verify(dao, Mockito.atLeastOnce()).getDebtorById(Mockito.anyString());
		
		assertNotNull(out);
	}
}
