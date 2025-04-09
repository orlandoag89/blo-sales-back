package com.blo.sales.business.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntDebtors;
import com.blo.sales.business.dto.DtoIntProduct;
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
	public DtoIntDebtor addPayment(BigDecimal partialPyment, String id) throws BloSalesBusinessException {
		LOGGER.info(String.format("adding partial payment: %s to: %s", partialPyment, id));
		var debtor = dao.getDebtorById(id);
		var newAccount = debtor.getTotal().subtract(partialPyment);
		LOGGER.info(String.format("new account min partial pyment %s", newAccount));
		/** si la deuda es menor o igual a 0 entonces se debe eliminar el deudor */
		if (newAccount.compareTo(BigDecimal.ZERO) == 0 || newAccount.compareTo(BigDecimal.ZERO) < 0) {
			LOGGER.info("account is <= 0");
			dao.deleteDebtorById(id);
			return null;
		}
		/** Actualiza la deuda y agrega el producto */
		debtor.getPartial_pyments().add(partialPyment);
		debtor.setTotal(partialPyment);
		LOGGER.info(String.format("debtor updated: %s", String.valueOf(debtor)));
		return dao.updateDebtor(id, debtor);
	}

	@Override
	public DtoIntDebtor addProducts(String idDebtor, List<DtoIntProduct> products, BigDecimal payment)
			throws BloSalesBusinessException {
		var debtorFound = dao.getDebtorById(idDebtor);
		debtorFound.getProducts().addAll(products);
		debtorFound.getTotal().add(payment);
		// agregar la venta hecha
		LOGGER.info(String.format("debtor updated %s", String.valueOf(debtorFound)));
		return dao.updateDebtor(idDebtor, debtorFound);
	}

}
