package com.blo.sales.facade.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ICreditsBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.ICreditsFacade;
import com.blo.sales.facade.dto.DtoCredit;
import com.blo.sales.facade.dto.DtoCredits;
import com.blo.sales.facade.dto.DtoPartialPyment;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.dto.commons.DtoError;
import com.blo.sales.facade.mapper.DtoCreditMapper;
import com.blo.sales.facade.mapper.DtoCreditsMapper;
import com.blo.sales.facade.mapper.DtoPartialPymentMapper;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300", "http://localhost:4400"})
public class CreditsFacadeImpl implements ICreditsFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditsFacadeImpl.class);
	
	@Autowired
	private ICreditsBusiness business;
	
	@Autowired
	private DtoCreditMapper mapper;
	
	@Autowired
	private DtoCreditsMapper creditsMapper;
	
	@Autowired
	private DtoPartialPymentMapper paymentMapper;
	
	@Override
	public ResponseEntity<DtoCommonWrapper<DtoCredit>> registerCredit(DtoCredit credit) {
		var toBody = new DtoCommonWrapper<DtoCredit>();
		try {
			LOGGER.info(String.format("guardando credito %s", Encode.forJava(String.valueOf(credit))));
			var creditInn = mapper.toInner(credit);
			var creditSaved = business.registerNewCredit(creditInn);
			var data = mapper.toOuter(creditSaved);
			LOGGER.info(String.format("credito guardado %s", String.valueOf(data)));
			toBody.setData(data);
			return new ResponseEntity<>(toBody, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			toBody.setError(error);
			return new ResponseEntity<>(toBody, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoCredits>> getAllCredits() {
		var toBody = new DtoCommonWrapper<DtoCredits>();
		LOGGER.info("recuperando todos los creditos");
		var out = business.getAllCredits();
		LOGGER.info(String.format("creditos [%s]", String.valueOf(out)));
		var data = creditsMapper.toOuter(out);
		toBody.setData(data);
		return new ResponseEntity<>(toBody, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoCredit>> addPartialPayment(String idCredit, DtoPartialPyment payment) {
		var toBody = new DtoCommonWrapper<DtoCredit>();
		try {
			var inner = paymentMapper.toInner(payment);
			var paymentAdded = business.addPayment(idCredit, inner);
			LOGGER.info(String.format("se ha agregado el pago al credito %s", String.valueOf(paymentAdded)));
			var data = mapper.toOuter(paymentAdded);
			toBody.setData(data);
			return new ResponseEntity<>(toBody, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			toBody.setError(error);
			return new ResponseEntity<>(toBody, e.getExceptHttpStatus());
		}
	}

}
