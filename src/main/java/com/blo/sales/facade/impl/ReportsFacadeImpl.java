package com.blo.sales.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IReportsFacade;
import com.blo.sales.facade.dto.DtoProductsOnSalesCounter;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.dto.commons.DtoError;
import com.blo.sales.facade.mapper.DtoProductsOnSalesCounterMapper;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300", "http://localhost:4400"})
public class ReportsFacadeImpl implements IReportsFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportsFacadeImpl.class);
	
	@Autowired
	private ISalesBusiness salesBusiness;
	
	@Autowired
	private DtoProductsOnSalesCounterMapper productsOnSalesCounterMapper;
	
	@Override
	public ResponseEntity<DtoCommonWrapper<DtoProductsOnSalesCounter>> retrieveProductsOnSalesReport() {
		LOGGER.info("recuperando todos los productos vendidos por cada venta");
		var body = new DtoCommonWrapper<DtoProductsOnSalesCounter>();
		try {
			var salesProducts = salesBusiness.getBestSellingProducts();
			var data = productsOnSalesCounterMapper.toOuter(salesProducts);
			LOGGER.info(String.format("productos en ventas %s", String.valueOf(data.getProductsOnSales().size())));
			body.setData(data);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			body.setError(error);
			return new ResponseEntity<>(body, e.getExceptHttpStatus());
		}
	}

}
