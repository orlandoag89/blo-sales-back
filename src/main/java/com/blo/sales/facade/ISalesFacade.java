package com.blo.sales.facade;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.facade.dto.DtoSales;
import com.blo.sales.facade.dto.DtoWrapperSale;
import com.blo.sales.facade.enums.StatusSaleEnum;

@RequestMapping("/api/v1/sales")
public interface ISalesFacade {
	
	@PostMapping
	ResponseEntity<DtoSale> registerSale(@RequestBody DtoSale sale);
	
	@GetMapping
	ResponseEntity<DtoSales> retrieveSales(@RequestParam StatusSaleEnum status);
	
	@GetMapping("/{id}")
	ResponseEntity<DtoSale> retrieveSaleById(@PathVariable String id);
	
	@PostMapping("/debtors/{totalDebtor}")
	ResponseEntity<DtoWrapperSale> registerSaleAndDebtor(@RequestBody DtoWrapperSale sale, @PathVariable BigDecimal totalDebtor);
}
