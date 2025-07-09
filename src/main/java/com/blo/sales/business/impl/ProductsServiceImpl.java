package com.blo.sales.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${products.messages.special}")
	private String productsMessagesSpecial;

    @Override
    public DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException {
    	LOGGER.info(String.format("products on service %s", String.valueOf(products)));
        return dao.addProducts(products);
    }

	@Override
	public DtoIntProducts getProducts() throws BloSalesBusinessException {
		LOGGER.info("Get products");
		var output = dao.getProducts();
		output.getProducts().add(0, getAnotherProductServices());
		LOGGER.info("Productos y otros productos");
		return output;
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

	private DtoIntProduct getAnotherProductServices() throws BloSalesBusinessException {
		LOGGER.info("recuperando productos especiales y servicios");
		var anotherProduct = dao.getAnotherProductServices();
		anotherProduct.setName(productsMessagesSpecial);
		return anotherProduct;
	}
}
