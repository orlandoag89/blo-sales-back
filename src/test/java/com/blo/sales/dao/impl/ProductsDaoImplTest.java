package com.blo.sales.dao.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.dao.mapper.ProductMapper;
import com.blo.sales.dao.mapper.ProductsMapper;
import com.blo.sales.dao.repository.ProductsRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class ProductsDaoImplTest {
	
	@Mock
	private ProductsRepository repository;
	
	@Mock
	private ProductMapper productMapper;
	
	@Mock
	private ProductsMapper productsMapper;
	
	@InjectMocks
	private ProductsDaoImpl impl;

	@Test
	public void addProductsTest() throws BloSalesBusinessException {
		Mockito.when(productsMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createProducts());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createProduct());
		Mockito.when(productMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		
		var out = impl.addProducts(MocksFactory.createDtoIntProducts());
		
		Mockito.verify(productsMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(productMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertFalse(out.getProducts().isEmpty());
	}
	
	@Test
	public void addProductsEmtyTest() throws BloSalesBusinessException {
		var productsToSave = MocksFactory.createProducts();
		productsToSave.getProducts().clear();
		
		Mockito.when(productsMapper.toInner(Mockito.any())).thenReturn(productsToSave);
		
		var products = MocksFactory.createDtoIntProducts();
		products.getProducts().clear();
		
		var out = impl.addProducts(products);
				
		assertNotNull(out);
		assertTrue(out.getProducts().isEmpty());
	}
	
	@Test
	public void getProductsTest() throws BloSalesBusinessException {
		Mockito.when(repository.findAll()).thenReturn(MocksFactory.createProducts().getProducts());
		Mockito.when(productsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntProducts());
		
		var products = impl.getProducts();
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findAll();
		
		assertNotNull(products);
		assertFalse(products.getProducts().isEmpty());
	}
	
	@Test
	public void getProductsEmptyTest() throws BloSalesBusinessException {
		var products = MocksFactory.createDtoIntProducts();
		products.getProducts().clear();
		
		Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
		Mockito.when(productsMapper.toOuter(Mockito.any())).thenReturn(products);
		
		var emptyProducts = impl.getProducts();
		
		assertNotNull(emptyProducts);
		assertTrue(emptyProducts.getProducts().isEmpty());
	}
	
	@Test
	public void getProductTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalProduct());
		Mockito.when(productMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		
		var out = impl.getProduct(MocksFactory.getId());
		
		assertNotNull(out);
		assertNotNull(out.getId());
	}
	
	@Test
	public void getProductNotFoundTest() {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(BloSalesBusinessException.class, () -> impl.getProduct(MocksFactory.getId()));
	}
	
	@Test
	public void updateProductTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalProduct());
		Mockito.when(productMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createProduct());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createProduct());
		Mockito.when(productMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		
		var out = impl.updateProduct(MocksFactory.getId(), MocksFactory.createDtoIntProduct());
				
		assertNotNull(out);
	}
	
	@Test
	public void updateProductNotFoundTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(BloSalesBusinessException.class, () -> impl.updateProduct(MocksFactory.getId(), MocksFactory.createDtoIntProduct()));
	}
	
	@Test
	public void getAnotherProductServicesExistsAnother() throws BloSalesBusinessException {
		Mockito.when(repository.findProductByName(Mockito.anyString())).thenReturn(MocksFactory.createOptionalProduct());
		Mockito.when(productMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSpecialProduct());
		
		var out = impl.getAnotherProductServices();
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findProductByName(Mockito.anyString());
		Mockito.verify(productMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
	}
	
	@Test
	public void getAnotherProductServicesNotExistsAnother() throws BloSalesBusinessException {
		Mockito.when(repository.findProductByName(Mockito.anyString())).thenReturn(Optional.empty());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createProductSpecial());
		Mockito.when(productMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntSpecialProduct());
		
		var out = impl.getAnotherProductServices();
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findProductByName(Mockito.anyString());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(productMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
	}
}
