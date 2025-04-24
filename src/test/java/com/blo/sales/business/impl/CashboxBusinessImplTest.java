package com.blo.sales.business.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.dao.ICashboxesDao;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class CashboxBusinessImplTest {
	
	@Mock
	private ICashboxesDao dao;
	
	@InjectMocks
	private CashboxBusinessImpl serv;
	
	@Test
	public void saveCashboxTest() {
		Mockito.when(dao.addCashbox(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxOpen());
		
		var out = serv.saveCashbox(MocksFactory.createDtoIntCashboxOpen());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).addCashbox(Mockito.any());
		
		assertNotNull(out);
		assertEquals(StatusCashboxIntEnum.OPEN.name(), out.getStatus().name());
	}

	@Test
	public void getCashboxOpen() {
		Mockito.when(dao.getCashboxOpen()).thenReturn(MocksFactory.createDtoIntCashboxOpen());
		
		var out = serv.getCashboxOpen();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getCashboxOpen();
		
		assertNotNull(out);
		assertEquals(StatusCashboxIntEnum.OPEN.name(), out.getStatus().name());
	}
	
	@Test
	public void updateCashboxTest() throws BloSalesBusinessException {
		Mockito.when(dao.updateCashbox(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxClose());
		
		var out = serv.updateCashbox(MocksFactory.getId(), MocksFactory.createDtoIntCashboxOpen());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).updateCashbox(Mockito.anyString(), Mockito.any());
		
		assertNotNull(out);
		assertEquals(StatusCashboxIntEnum.CLOSE.name(), out.getStatus().name());
		
	}
	
	@Test
	public void getAllCashboxesTest() {
		Mockito.when(dao.getAllCashboxes()).thenReturn(MocksFactory.createDtoIntCashboxes());
		
		var out = serv.getAllCashboxes();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getAllCashboxes();
		
		assertNotNull(out);
		assertNotNull(out.getBoxes());
		assertFalse(out.getBoxes().isEmpty());
	}
}
