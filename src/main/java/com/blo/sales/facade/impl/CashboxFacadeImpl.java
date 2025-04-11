package com.blo.sales.facade.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.ICashboxFacade;
import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.facade.dto.DtoCashboxes;

@RestController
public class CashboxFacadeImpl implements ICashboxFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(CashboxFacadeImpl.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ICashboxBusiness business;
	
	@Override
	public ResponseEntity<DtoCashbox> closeCashbox() {
		LOGGER.info("closing cashbox");
		
		try {
			var closedCashbox = business.closeCashbox();
			var saved = modelMapper.map(closedCashbox, DtoCashbox.class);
			LOGGER.info(String.format("out %s", String.valueOf(saved)));
			return new ResponseEntity<DtoCashbox>(saved, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCashboxes> getAllCashboxes() {
		var cashboxes = business.getAllCashboxes();
		var out = modelMapper.map(cashboxes, DtoCashboxes.class);
		LOGGER.info(String.format("cashboxes %s", String.valueOf(out)));
		return new ResponseEntity<DtoCashboxes>(out, HttpStatus.OK);
	}

}
