package com.blo.sales.business.impl;

import java.math.BigDecimal;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blo.sales.business.ICreditsBusiness;
import com.blo.sales.business.dto.DtoIntCredit;
import com.blo.sales.business.dto.DtoIntCredits;
import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.dao.ICreditsDao;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class CreditBusinessImpl implements ICreditsBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CreditBusinessImpl.class);
	
	@Autowired
	private ICreditsDao dao;

	@Override
	public DtoIntCredits getAllCredits() {
		LOGGER.info("recuperando todos los creditos");
		return dao.getAllCredits();
	}

	@Override
	public DtoIntCredit registerNewCredit(DtoIntCredit credit) throws BloSalesBusinessException {
		LOGGER.info(String.format("registrando nuevo credito [%s]", Encode.forJava(String.valueOf(credit))));
		return dao.registerNewCredit(credit);
	}

	@Override
	public DtoIntCredit addPayment(String idCredit, DtoIntPartialPyment partialPayment)
			throws BloSalesBusinessException {
		LOGGER.info(String.format("agregando pago [%s] a credito %s", Encode.forJava(idCredit), Encode.forJava(String.valueOf(partialPayment))));
		return dao.addPayment(idCredit, partialPayment);
	}

	@Override
	public DtoIntCredit updateCreditBasicData(String idCredit, DtoIntCredit credit) throws BloSalesBusinessException {
		LOGGER.info(String.format("actualizando informacion basica de credito %s a %s", Encode.forJava(idCredit), Encode.forJava(String.valueOf(credit))));
		return dao.updateCreditBasicData(idCredit, credit);
	}

	@Override
	public DtoIntCredit updateAmount(String idCredit, BigDecimal amount) throws BloSalesBusinessException {
		LOGGER.info(String.format("actualizando monto de credito %s a %s", Encode.forJava(idCredit), Encode.forJava(String.valueOf(amount))));
		return dao.updateAmount(idCredit, amount);
	}

}
