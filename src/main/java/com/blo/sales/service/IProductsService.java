package com.blo.sales.service;

import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.service.dto.DtoIntProducts;

public interface IProductsService {

    DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException;
    
    DtoIntProducts getProducts() throws BloSalesBusinessException;
}
