package com.blo.sales.facade.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.IProductsBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IProductsFacade;
import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.facade.dto.DtoProducts;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.dto.commons.DtoError;
import com.blo.sales.facade.mapper.DtoProductMapper;
import com.blo.sales.facade.mapper.DtoProductsMapper;
import com.blo.sales.utils.Utils;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ProductsFacadeImpl implements IProductsFacade {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsFacadeImpl.class);
    
    @Autowired
    private DtoProductMapper productMapper;
    
    @Autowired
    private DtoProductsMapper productsMapper;
    
    @Autowired
    private IProductsBusiness service;
    
    @Value("${excpetions.codes.products-empty}")
    private String productsEmptyCode;
    
    @Value("${excpetions.messages.products-empty}")
    private String productsEmptyMessage;
    
    @Value("${excpetions.messages.no-id}")
    private String noIdMessage;
    
    @Value("${excpetions.codes.no-id}")
    private String noIdCode;

    @Override
    public ResponseEntity<DtoCommonWrapper<DtoProducts>> addProduct(final DtoProducts products) {
    	LOGGER.info(String.format("products %s", Encode.forJava(String.valueOf(products))));
    	var output = new DtoCommonWrapper<DtoProducts>();
		try {
			if (products == null || products.getProducts() == null || products.getProducts().isEmpty()) {
				throw new BloSalesBusinessException(productsEmptyMessage, productsEmptyCode, HttpStatus.BAD_REQUEST);
			}
			var innerProducts = productsMapper.toInner(products);
			var saved = service.addProducts(innerProducts);
			var productsOut = productsMapper.toOuter(saved);
			output.setData(productsOut);
			return new ResponseEntity<>(output, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			output.setError(error);
			return new ResponseEntity<>(output, e.getExceptHttpStatus());
		}
    }

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoProducts>> retrieveAllProducts() {
		LOGGER.info("Retrieving all products");
		var output = new DtoCommonWrapper<DtoProducts>();
		try {
			var products = service.getProducts();
			var mapped = productsMapper.toOuter(products);
			output.setData(mapped);
			return new ResponseEntity<>(output, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			output.setError(error);
			return new ResponseEntity<>(output, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoProduct>> retrieveProduct(String productId) {
		LOGGER.info(String.format("Retrieving product %s", productId));
		var output = new DtoCommonWrapper<DtoProduct>();
		try {
			Utils.isStringIsBlankOrUndefined(productId, noIdMessage, noIdCode);
			
			var product = service.getProduct(productId);
			var mappedProduct = productMapper.toOuter(product);
			output.setData(mappedProduct);
			return new ResponseEntity<>(output, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			output.setError(error);
			return new ResponseEntity<>(output, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoProduct>> updateProduct(String productId, DtoProduct product) {
		LOGGER.info(String.format("Update info by id: %s, data %s", productId,  Encode.forJava(String.valueOf(product))));
		var output = new DtoCommonWrapper<DtoProduct>();
		try {
			Utils.isStringIsBlankOrUndefined(productId, noIdMessage, noIdCode);
			
			if (product == null) {
				throw new BloSalesBusinessException(productId, productId, HttpStatus.BAD_REQUEST);
			}
			var productInner = productMapper.toInner(product);
			var productUpdated = service.updateProduct(productId, productInner);
			var productToOuter = productMapper.toOuter(productUpdated);
			LOGGER.info(String.format("updated data: %s", String.valueOf(productToOuter)));
			output.setData(productToOuter);
			return new ResponseEntity<>(output, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			output.setError(error);
			return new ResponseEntity<>(output, e.getExceptHttpStatus());
		}
	}
}