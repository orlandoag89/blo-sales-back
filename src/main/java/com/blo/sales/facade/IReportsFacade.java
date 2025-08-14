package com.blo.sales.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blo.sales.facade.dto.DtoProductsOnSalesCounter;
import com.blo.sales.facade.dto.DtoSalesDetailReport;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.enums.ReportTypeEnum;

@RequestMapping("/api/v1/reports")
public interface IReportsFacade {
	
	@GetMapping("/products-on-sales")
	ResponseEntity<DtoCommonWrapper<DtoProductsOnSalesCounter>> retrieveProductsOnSalesReport(
		@RequestParam ReportTypeEnum reportType,
		@RequestParam int initialMonth,
		@RequestParam int initYear,
		@RequestParam int endMonth,
		@RequestParam int endYear
	);

	@GetMapping("/sales")
	ResponseEntity<DtoCommonWrapper<DtoSalesDetailReport>> retrieveSalesByDate(
			@RequestParam ReportTypeEnum reportType,
			@RequestParam int initialMonth,
			@RequestParam int initYear,
			@RequestParam int endMonth,
			@RequestParam int endYear);
}
