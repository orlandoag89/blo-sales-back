package com.blo.sales.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntProducts;
import com.blo.sales.dao.IProductsDao;
import com.blo.sales.dao.docs.Products;
import com.blo.sales.dao.mapper.ProductMapper;
import com.blo.sales.dao.mapper.ProductsMapper;
import com.blo.sales.dao.repository.ProductsRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class ProductsDaoImpl implements IProductsDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductsDaoImpl.class);
	
	@Autowired
	private ProductsRepository repository;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Autowired
	private ProductsMapper productsMapper;
	
	@Value("${exceptions.codes.product-not-found}")
	private String productNotFoundCode;
	
	@Value("${exceptions.messages.product-not-found}")
	private String productNotFoundMessage;

	@Override
	public DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException {
		var productsToSave = productsMapper.toInner(products);
		LOGGER.info(String.format("dao products %s", String.valueOf(productsToSave)));
		
		DtoIntProducts toOut = new DtoIntProducts();
		List<DtoIntProduct> productsList = new ArrayList<>();
		
		if (productsToSave.getProducts().isEmpty()) {
			LOGGER.info("products is empty");
			toOut.setProducts(productsList);
			return toOut;
		}
		
		productsToSave.getProducts().forEach(p -> {
			var saved = repository.save(p);
			LOGGER.info(String.format("product saved %s", String.valueOf(saved)));
			productsList.add(productMapper.toOuter(saved));
		});
		
		LOGGER.info(String.format("Porducts list %s", String.valueOf(productsList)));
		toOut.setProducts(productsList);
		return toOut;
	}

	@Override
	public DtoIntProducts getProducts() throws BloSalesBusinessException {
		var allProducts = repository.findAll();
		var wrapperProducts = new Products();
		wrapperProducts.setProducts(allProducts);
		LOGGER.info(String.format("Products found: %s", String.valueOf(wrapperProducts)));
		return productsMapper.toOuter(wrapperProducts);
	}

	@Override
	public DtoIntProduct getProduct(String productId) throws BloSalesBusinessException {
		var productFound = repository.findById(productId);
		if (!productFound.isPresent()) {
			LOGGER.error(String.format("id %s not found", productId));
			throw new BloSalesBusinessException(productNotFoundMessage, productNotFoundCode, HttpStatus.NOT_FOUND);
		}
		var product = productMapper.toOuter(productFound.get());
		LOGGER.info(String.format("product found: %s", String.valueOf(product)));
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
		
		var product = productMapper.toInner(productFound);
		
		var saved = repository.save(product);
		var out = productMapper.toOuter(saved);
		LOGGER.info(String.format("product updated: %s", String.valueOf(out)));
		return out;
		
	}

}
