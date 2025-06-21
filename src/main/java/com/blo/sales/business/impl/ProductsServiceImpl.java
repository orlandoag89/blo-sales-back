package com.blo.sales.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blo.sales.business.IProductsBusiness;
import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntProducts;
import com.blo.sales.dao.IProductsDao;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class ProductsServiceImpl implements IProductsBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsServiceImpl.class);
	
	@Autowired
	private IProductsDao dao;

    @Override
    public DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException {
    	LOGGER.info(String.format("products on service %s", String.valueOf(products)));
        return dao.addProducts(products);
    }

	@Override
	public DtoIntProducts getProducts() throws BloSalesBusinessException {
		LOGGER.info("Get products");
		return dao.getProducts();
	}

	@Override
	public DtoIntProduct getProduct(String productId) throws BloSalesBusinessException {
		LOGGER.info(String.format("getting product by id: %s", productId));
		return dao.getProduct(productId);
	}

	@Override
	public DtoIntProduct updateProduct(String productId, DtoIntProduct data) throws BloSalesBusinessException {
		LOGGER.info(String.format("Updating product by id: %s", String.valueOf(data)));
		return dao.updateProduct(productId, data);
	}
}
