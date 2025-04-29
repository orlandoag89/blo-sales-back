package com.blo.sales.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.facade.dto.DtoCashboxes;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;

@RequestMapping("/api/v1/cashbox")
public interface ICashboxFacade {
	
	@GetMapping
	ResponseEntity<DtoCommonWrapper<DtoCashboxes>> getAllCashboxes();
	
	@PostMapping
	ResponseEntity<DtoCommonWrapper<DtoCashbox>> closeCashbox();

}
