package com.blo.sales.business;

import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntProducts;
import com.blo.sales.exceptions.BloSalesBusinessException;

public interface IProductsBusiness {

    DtoIntProducts addProducts(DtoIntProducts products) throws BloSalesBusinessException;
    
    DtoIntProducts getProducts() throws BloSalesBusinessException;
    
    DtoIntProduct getProduct(String productId) throws BloSalesBusinessException;
    
    DtoIntProduct updateProduct(String productId, DtoIntProduct data) throws BloSalesBusinessException;
}
