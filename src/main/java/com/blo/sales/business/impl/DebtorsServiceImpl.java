package com.blo.sales.business.impl;

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
public class DebtorsServiceImpl implements IDebtorsBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorsServiceImpl.class);
	
	@Autowired
	private IDebtorsDao dao;
		
	@Override
	public DtoIntDebtor addDebtor(DtoIntDebtor debtor) throws BloSalesBusinessException {
		LOGGER.info(String.format("Saving debtor: %s", Encode.forJava(String.valueOf(debtor))));
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
	public DtoIntDebtor getDebtorOrNull(String id) throws BloSalesBusinessException {
		LOGGER.info(String.format("getting debtor %s", id));
		return dao.getDebtorOrNull(id);
	}

	@Override
	public DtoIntDebtor addPay(String idDebtor, DtoIntPartialPyment partiaylPyment, long dateOnMils) throws BloSalesBusinessException {
		LOGGER.info(String.format("adding pyment to %s. %s", idDebtor, Encode.forJava(String.valueOf(partiaylPyment))));
		var debtorFound = getDebtorById(idDebtor);
		var newAccount = debtorFound.getTotal().subtract(partiaylPyment.getPartial_pyment());
		LOGGER.info(String.format("new account %s", newAccount));
		debtorFound.setTotal(newAccount);
		debtorFound.getPartial_pyments().add(partiaylPyment);
		var debtorUpdated = updateDebtor(idDebtor, debtorFound);
		LOGGER.info(String.format("debtor updated %s", String.valueOf(debtorUpdated)));
		return debtorUpdated;
	}

}
