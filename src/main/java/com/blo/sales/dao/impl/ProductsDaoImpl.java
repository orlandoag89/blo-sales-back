package com.blo.sales.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntProducts;
import com.blo.sales.dao.IProductsDao;
import com.blo.sales.dao.docs.Product;
import com.blo.sales.dao.docs.Products;
import com.blo.sales.dao.repository.ProductsRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class ProductsDaoImpl implements IProductsDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsDaoImpl.class);
	
	@Autowired
	private ProductsRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${exceptions.codes.not-found}")
	private String notFoundCode;
	
	@Value("${exceptions.messages.not-found}")
	private String notFoundMessage;

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

	@Override
	public DtoIntProduct getProduct(String productId) throws BloSalesBusinessException {
		var productFound = repository.findById(productId);
		if (!productFound.isPresent()) {
			LOGGER.error(String.format("id %s not found", productId));
			throw new BloSalesBusinessException(notFoundMessage, notFoundCode, HttpStatus.NOT_FOUND);
		}
		
		var product = modelMapper.map(productFound.get(), DtoIntProduct.class);
		return product;
	}

	@Override
	public DtoIntProduct updateProduct(String productId, DtoIntProduct data) throws BloSalesBusinessException {
		var productFound = getProduct(productId);
		LOGGER.info(String.format("Updating data %s", productFound));
		
		productFound.setDesc(data.getDesc());
		productFound.setName(data.getName());
		productFound.setQuantity(data.getQuantity());
		productFound.setTotal_price(data.getTotal_price());
		
		var product = modelMapper.map(productFound, Product.class);
		
		var saved = repository.save(product);
		var out = modelMapper.map(saved, DtoIntProduct.class);
		LOGGER.info(String.format("product updated: %s", String.valueOf(out)));
		return out;
		
	}

}
