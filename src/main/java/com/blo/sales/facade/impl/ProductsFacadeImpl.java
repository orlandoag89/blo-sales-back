package com.blo.sales.facade.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.IProductsBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IProductsFacade;
import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.facade.dto.DtoProducts;
import com.blo.sales.facade.mapper.DtoProductMapper;
import com.blo.sales.facade.mapper.DtoProductsMapper;

@RestController
public class ProductsFacadeImpl implements IProductsFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsFacadeImpl.class);
    
    @Autowired
    private DtoProductMapper productMapper;
    
    @Autowired
    private DtoProductsMapper productsMapper;
    
    @Autowired
    private IProductsBusiness service;

    @Override
    public ResponseEntity<DtoProducts> addProduct(final DtoProducts products) {
    	LOGGER.info(String.format("products %s", Encode.forJava(String.valueOf(products))));
		try {
			var innerProducts = productsMapper.toInner(products);
			var saved = service.addProducts(innerProducts);
			var productsOut = productsMapper.toOuter(saved);
			return new ResponseEntity<>(productsOut, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
    }

	@Override
	public ResponseEntity<DtoProducts> retrieveAllProducts() {
		LOGGER.info("Retrieving all products");
		try {
			var products = service.getProducts();
			var mapped = productsMapper.toOuter(products);
			return new ResponseEntity<>(mapped, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoProduct> retrieveProduct(String productId) {
		LOGGER.info(String.format("Retrieving product %s", productId));
		try {
			var product = service.getProduct(productId);
			var mappedProduct = productMapper.toOuter(product);
			return new ResponseEntity<DtoProduct>(mappedProduct, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoProduct> updateProduct(String productId, DtoProduct product) {
		LOGGER.info(String.format("Update info by id: %s, data %s", productId,  Encode.forJava(String.valueOf(product))));
		try {
			var productInner = productMapper.toInner(product);
			var productUpdated = service.updateProduct(productId, productInner);
			var productToOuter = productMapper.toOuter(productUpdated);
			return new ResponseEntity<>(productToOuter, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}
}