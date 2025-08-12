package com.blo.sales.business.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.dao.ISalesDao;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class SalesBusinessImplTest {
	
	@Mock
	private ISalesDao dao;

	@InjectMocks
	private SalesBusinessImpl impl;
	
	@Test
	public void addSaleTest() throws BloSalesBusinessException {
		Mockito.when(dao.addSale(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		
		var out = impl.addSale(MocksFactory.createNewDtoIntSaleOnCashbox());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).addSale(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getId());
	}
	
	@Test
	public void getSalesTest() throws BloSalesBusinessException {
		Mockito.when(dao.getSales()).thenReturn(MocksFactory.createDtoIntSales());
		
		var out = impl.getSales();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getSales();
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}
	
	@Test
	public void getSalesOpenTest() throws BloSalesBusinessException {
		Mockito.when(dao.getSales()).thenReturn(MocksFactory.createDtoIntSales());
		
		var out = impl.getSales();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getSales();
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}

	@Test
	public void getSalesCloseTest() throws BloSalesBusinessException {
		Mockito.when(dao.getSalesClose()).thenReturn(MocksFactory.createDtoIntSales());
		
		var out = impl.getSalesClose();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getSalesClose();
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}

	@Test
	public void getSaleByIdTest() throws BloSalesBusinessException {
		Mockito.when(dao.getSaleById(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		
		var out = impl.getSaleById(MocksFactory.getId());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getSaleById(Mockito.anyString());
		assertNotNull(out);
	}
	
	@Test
	public void updateSaleTest() throws BloSalesBusinessException {
		Mockito.when(dao.updateSale(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleOnCashbox());
		
		var out = impl.updateSale(MocksFactory.getId(), MocksFactory.createDtoIntSaleNoCashbox());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).updateSale(Mockito.anyString(), Mockito.any());
		
		assertNotNull(out);
	}
	
	@Test
	public void getSalesNotCashboxTest() throws BloSalesBusinessException {
		Mockito.when(dao.getSalesNotCashbox()).thenReturn(MocksFactory.createDtoIntSales());
		
		var out = impl.getSalesNotCashbox();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getSalesNotCashbox();
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}
	
	@Test
	public void getBestSellingProducts() throws BloSalesBusinessException {
		Mockito.when(dao.getBestSellingProducts()).thenReturn(MocksFactory.createDtoIntProductsOnSalesCounter());
		
		var out = impl.getBestSellingProducts();
		
		assertNotNull(out);
		assertFalse(out.getProductsOnSales().isEmpty());
	}
}
