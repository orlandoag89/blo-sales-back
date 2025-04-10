package com.blo.sales.business.impl;

import java.math.BigDecimal;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntDebtors;
import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.dao.IDebtorsDao;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class DebtorsService implements IDebtorsBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorsService.class);
	
	@Autowired
	private IDebtorsDao dao;
	
	@Override
	public DtoIntDebtor addDebtor(DtoIntDebtor debtor) throws BloSalesBusinessException {
		LOGGER.info(String.format("Saving debtor: %s", String.valueOf(debtor)));
		return dao.addDebtor(debtor);
	}

	@Override
	public DtoIntDebtors getDebtors() throws BloSalesBusinessException {
		LOGGER.info("Retrieving all debtors");
		return dao.getDebtors();
	}

	@Override
	public DtoIntDebtor getDebtorById(String id) throws BloSalesBusinessException {
		LOGGER.info(String.format("Getting debtor by id %s", id));
		return dao.getDebtorById(id);
	}

	@Override
	public void deleteDebtorById(String id) throws BloSalesBusinessException {
		LOGGER.info(String.format("Deleting debtor %s", id));
		dao.deleteDebtorById(id);
	}

	@Override
	public DtoIntDebtor updateDebtor(String id, DtoIntDebtor debtor) throws BloSalesBusinessException {
		LOGGER.info(String.format("Updating debtor %s", String.valueOf(debtor)));
		return dao.updateDebtor(id, debtor);
	}

	@Override
	public DtoIntDebtor addPartialPyment(String id, DtoIntPartialPyment partialPyment)
			throws BloSalesBusinessException {
		LOGGER.info(String.format("Adding partial pyment %s to %s", Encode.forJava(String.valueOf(partialPyment)), id));
		var debtorFound = getDebtorById(id);
		var newAccount = debtorFound.getTotal().subtract(partialPyment.getPartial_pyment());
		LOGGER.info(String.format("new account %s", newAccount));
		
		if (debtorFound.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
			LOGGER.info("this account was payed");
			dao.deleteDebtorById(id);
			return new DtoIntDebtor();
		}
		
		LOGGER.info("Saving account");
		debtorFound.setTotal(newAccount);
		debtorFound.getPartial_pyments().add(partialPyment);
		return dao.updateDebtor(id, debtorFound);
	}

	@Override
	public DtoIntDebtor getDebtorOrNull(String id) throws BloSalesBusinessException {
		LOGGER.info(String.format("getting debtor %s", id));
		return dao.getDebtorOrNull(id);
	}

}
