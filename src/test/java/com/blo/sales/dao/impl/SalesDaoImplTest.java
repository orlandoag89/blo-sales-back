package com.blo.sales.dao.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.dao.mapper.ProductOnSaleCounterMapper;
import com.blo.sales.dao.mapper.SaleMapper;
import com.blo.sales.dao.mapper.SalesMapper;
import com.blo.sales.dao.repository.SalesRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class SalesDaoImplTest {
	
	@Mock
	private SalesRepository repository;
	
	@Mock
	private SalesMapper salesMapper;
	
	@Mock
	private SaleMapper saleMapper;
	
	@Mock
	private ProductOnSaleCounterMapper productOnSaleCounterMapper;
	
	@InjectMocks
	private SalesDaoImpl impl;

	@Test
	public void addSaleTest() throws BloSalesBusinessException {
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createSaleNoCashbox());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createSaleNoCashbox());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		
		var out = impl.addSale(MocksFactory.createDtoIntSaleNoCashbox());
		
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
	}
	
	@Test
	public void getSalesTest() throws BloSalesBusinessException {
		Mockito.when(repository.findAll()).thenReturn(MocksFactory.createSales().getSales());
		Mockito.when(salesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSales());
		
		var out = impl.getSales();
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findAll();
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}
	
	@Test
	public void getSalesOpenTest() throws BloSalesBusinessException {
		Mockito.when(repository.findSalesOpen()).thenReturn(MocksFactory.createSales().getSales());
		Mockito.when(salesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSales());
		
		var out = impl.getSalesOpen();
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findSalesOpen();
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}
	
	@Test
	public void getSalesCloseTest() throws BloSalesBusinessException {
		Mockito.when(repository.findSalesClosed()).thenReturn(MocksFactory.createSales().getSales());
		Mockito.when(salesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSales());
		
		var out = impl.getSalesClose();
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findSalesClosed();
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}
	
	@Test
	public void getSaleByIdTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalSale());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		
		var out = impl.getSaleById(MocksFactory.getId());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertFalse(out.getProducts().isEmpty());
	}
	
	@Test
	public void getSaleByIdEmptyTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(BloSalesBusinessException.class, () -> impl.getSaleById(MocksFactory.getId()));
	}
	
	@Test
	public void updateSaleTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalSale());
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createSaleNoCashbox());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createSaleOnCashbox());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleOnCashbox());
		
		var out = impl.updateSale(MocksFactory.getId(), MocksFactory.createDtoIntSaleNoCashbox());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertFalse(out.is_on_cashbox());
	}
	
	@Test
	public void getSalesNotCashboxTest() throws BloSalesBusinessException {
		Mockito.when(repository.findSaleNoCashbox()).thenReturn(MocksFactory.createSales().getSales());
		Mockito.when(salesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSales());
		
		var out = impl.getSalesNotCashbox();
		
		assertNotNull(out);
		assertFalse(out.getSales().isEmpty());
	}
	
	@Test
	public void getBestSellingProductsByAllTest() throws BloSalesBusinessException {
		Mockito.when(repository.countSalesByProduct(Mockito.anyList())).thenReturn(MocksFactory.createProductsOnSaleCounter());
		Mockito.when(productOnSaleCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntProductOnSaleCounter());
		
		var out = impl.getBestSellingProducts(0,0,0,0);
		
		assertNotNull(out.getProductsOnSales());
		assertFalse(out.getProductsOnSales().isEmpty());
	}
	
	@Test
	public void getBestSellingProductsByPeriodTest() throws BloSalesBusinessException {
		Mockito.when(repository.countSalesByProduct(Mockito.anyList())).thenReturn(MocksFactory.createProductsOnSaleCounter());
		Mockito.when(productOnSaleCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntProductOnSaleCounter());
		
		var out = impl.getBestSellingProducts(1, 2025, 2, 2025);
		
		assertNotNull(out.getProductsOnSales());
		assertFalse(out.getProductsOnSales().isEmpty());
	}
}
