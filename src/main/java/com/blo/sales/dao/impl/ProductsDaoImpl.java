package com.blo.sales.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blo.sales.dao.IProductsDao;
import com.blo.sales.dao.docs.Products;
import com.blo.sales.dao.repository.ProductsRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.service.dto.DtoIntProduct;
import com.blo.sales.service.dto.DtoIntProducts;

@Service
public class ProductsDaoImpl implements IProductsDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsDaoImpl.class);
	
	@Autowired
	private ProductsRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException {
		var productsToSave = modelMapper.map(products, Products.class);
		LOGGER.info(String.format("dao products %s", String.valueOf(productsToSave)));
		
		DtoIntProducts toOut = new DtoIntProducts();
		List<DtoIntProduct> productsList = new ArrayList<>();
		
		productsToSave.getProducts().forEach(p -> {
			var saved = repository.save(p);
			LOGGER.info(String.format("product saved %s", String.valueOf(saved)));
			productsList.add(modelMapper.map(saved, DtoIntProduct.class));
		});
		
		LOGGER.info(String.format("Porducts list %s", String.valueOf(productsList)));
		toOut.setProducts(productsList);
		return toOut;
	}

	@Override
	public DtoIntProducts getProducts() throws BloSalesBusinessException {
		var allProducts = repository.findAll();
		LOGGER.info(String.format("Products found: %s", String.valueOf(allProducts)));
		
		DtoIntProducts toOut = new DtoIntProducts();
		List<DtoIntProduct> productsList = new ArrayList<>();
		
		allProducts.forEach(p -> {
			var product = modelMapper.map(p, DtoIntProduct.class);
			LOGGER.info(String.format("product mapped: %s", String.valueOf(product)));
			productsList.add(product);
		});
		
		toOut.setProducts(productsList);
		return toOut;
	}

}
