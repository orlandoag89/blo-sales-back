package com.blo.sales.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blo.sales.facade.dto.DtoDebtor;
import com.blo.sales.facade.dto.DtoDebtors;
import com.blo.sales.facade.dto.DtoPartialPyment;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;

@RequestMapping("/api/v1/debtors")
public interface IDebtorFacade {
		
	@GetMapping("/{id}")
	ResponseEntity<DtoCommonWrapper<DtoDebtor>> retrieveDebtorById(@PathVariable String id);
	
	@GetMapping
	ResponseEntity<DtoCommonWrapper<DtoDebtors>> retrieveAllDebtors();
	
	@PutMapping("/{id}")
	ResponseEntity<DtoCommonWrapper<DtoDebtor>> addPay(@PathVariable String id, @RequestParam long time, @RequestBody DtoPartialPyment partialPyment);
	
}
