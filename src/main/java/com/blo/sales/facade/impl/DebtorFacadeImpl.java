package com.blo.sales.facade.impl;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IDebtorFacade;
import com.blo.sales.facade.dto.DtoDebtor;
import com.blo.sales.facade.dto.DtoDebtors;
import com.blo.sales.facade.dto.DtoPartialPyment;

@RestController
public class DebtorFacadeImpl implements IDebtorFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorFacadeImpl.class);
	
	@Autowired
	private IDebtorsBusiness business;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public ResponseEntity<DtoDebtor> saveDebtor(DtoDebtor debtor) {
		LOGGER.info(String.format("Saving debtor %s", Encode.forJava(String.valueOf(debtor))));
		var debtorInt = modelMapper.map(debtor, DtoIntDebtor.class);
		try {
			var saved = business.addDebtor(debtorInt);
			LOGGER.info(String.format("Debtor saved %s", String.valueOf(saved)));
			var out = modelMapper.map(saved, DtoDebtor.class);
			return new ResponseEntity<DtoDebtor>(out, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoDebtor> retrieveDebtorById(String id) {
		try {
			var debtorFound = business.getDebtorById(id);
			LOGGER.info(String.format("Debtor found %s", String.valueOf(debtorFound)));
			var out = modelMapper.map(debtorFound, DtoDebtor.class);
			return new ResponseEntity<DtoDebtor>(out, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoDebtors> retrieveAllDebtors() {
		try {
			var debtors = business.getDebtors();
			LOGGER.info(String.format("Debtors %s", String.valueOf(debtors)));
			var out = modelMapper.map(debtors, DtoDebtors.class);
			return new ResponseEntity<DtoDebtors>(out, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoDebtor> addPay(String id, DtoPartialPyment partialPyment) {
		try {
			LOGGER.info(String.format("adding payment %s to %s", Encode.forJava(String.valueOf(partialPyment)), id));
			var partialPymentMapped = modelMapper.map(partialPyment, DtoIntPartialPyment.class);
			var saved = business.addPay(id, partialPymentMapped);
			
			if (StringUtils.isEmpty(saved.getId())) {
				LOGGER.info(String.format("%s was deleted, account was payed", id));
				return new ResponseEntity<>(null, HttpStatus.OK); 
			}
			
			var out = modelMapper.map(saved, DtoDebtor.class);
			LOGGER.info(String.format("partial payment was added to %s", id));
			return new ResponseEntity<DtoDebtor>(out, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

}
