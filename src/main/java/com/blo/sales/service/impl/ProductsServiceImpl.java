package com.blo.sales.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.service.IProductsService;
import com.blo.sales.service.dto.DtoIntProducts;

@Service
public class ProductsServiceImpl implements IProductsService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsServiceImpl.class);

    @Override
    public DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException {
    	LOGGER.info("products on service %s", String.valueOf(products));
        return null;
    }
}
