package com.blo.sales.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blo.sales.facade.dto.DtoCredit;
import com.blo.sales.facade.dto.DtoCredits;
import com.blo.sales.facade.dto.DtoPartialPyment;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;

@RequestMapping("/api/v1/credits")
public interface ICreditsFacade {
	
	@PostMapping
	ResponseEntity<DtoCommonWrapper<DtoCredit>> registerCredit(@RequestBody DtoCredit credit);
	
	@GetMapping
	ResponseEntity<DtoCommonWrapper<DtoCredits>> getAllCredits();
	
	@PutMapping("/{idCredit}")
	ResponseEntity<DtoCommonWrapper<DtoCredit>> addPartialPayment(@PathVariable String idCredit, @RequestBody DtoPartialPyment payment);
	
}
