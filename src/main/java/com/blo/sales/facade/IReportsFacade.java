package com.blo.sales.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blo.sales.facade.dto.DtoProductsOnSalesCounter;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;

@RequestMapping("/api/v1/reports")
public interface IReportsFacade {
	
	@GetMapping("/products-on-sales")
	ResponseEntity<DtoCommonWrapper<DtoProductsOnSalesCounter>> retrieveProductsOnSalesReport();

}
