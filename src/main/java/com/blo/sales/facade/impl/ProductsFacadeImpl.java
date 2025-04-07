package com.blo.sales.facade.impl;

import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IProductsFacade;
import com.blo.sales.facade.dto.DtoProducts;
import com.blo.sales.service.IProductsService;
import com.blo.sales.service.dto.DtoIntProducts;

@RestController
public class ProductsFacadeImpl implements IProductsFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsFacadeImpl.class);
    
    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private IProductsService service;

    @Override
    public ResponseEntity<DtoProducts> addProduct(final DtoProducts products) {
    	LOGGER.info(String.format("products %s", Encode.forJava(String.valueOf(products))));
		try {
			var innerProducts = modelMapper.map(products, DtoIntProducts.class);
			var saved = service.addProducts(innerProducts);
			var productsOut = modelMapper.map(saved, DtoProducts.class);
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
			var mapped = modelMapper.map(products, DtoProducts.class);
			return new ResponseEntity<>(mapped, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}
}