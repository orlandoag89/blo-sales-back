package com.blo.sales.facade.impl;

import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.facade.IProducts;
import com.blo.sales.facade.dto.DtoProducts;
import com.blo.sales.service.dto.DtoIntProducts;

@RestController
public class ProductsImpl implements IProducts {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductsImpl.class);
    
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public DtoProducts addProduct(DtoProducts products) {
    	LOGGER.info(String.format("products %s", Encode.forJava(String.valueOf(products))));
    	DtoIntProducts productsMapped = modelMapper.map(products, DtoIntProducts.class);
    	LOGGER.info(String.format("products inner %s", productsMapped));
        return null;
    }
}