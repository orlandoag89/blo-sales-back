package com.blo.sales.business.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.dao.IProductsDao;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class ProductsServiceImplTest {
	
	@Mock
	private IProductsDao dao;
	
	@InjectMocks
	private ProductsServiceImpl impl;
	
	@Test
	public void addProductTest() throws BloSalesBusinessException {
		Mockito.when(dao.addProducts(Mockito.any())).thenReturn(MocksFactory.createDtoIntProducts());
		
		var out = impl.addProducts(MocksFactory.createDtoIntProducts());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).addProducts(Mockito.any());
		
		assertNotNull(out);
		assertFalse(out.getProducts().isEmpty());
	}
	
	@Test
	public void getProductsTest() throws BloSalesBusinessException {
		Mockito.when(dao.getProducts()).thenReturn(MocksFactory.createDtoIntProducts());
		
		var out = impl.getProducts();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getProducts();
		
		assertNotNull(out);
		assertFalse(out.getProducts().isEmpty());
	}
	
	@Test
	public void getProductTest() throws BloSalesBusinessException {
		Mockito.when(dao.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		
		var out = impl.getProduct(MocksFactory.getId());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getProduct(MocksFactory.getId());
		
		assertNotNull(out);
		assertNotNull(out.getId());
	}
	
	@Test
	public void updateProductTest() throws BloSalesBusinessException {
		Mockito.when(dao.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		
		var out = impl.updateProduct(MocksFactory.getId(), MocksFactory.createDtoIntProduct());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		
		assertNotNull(out);
	}

}
