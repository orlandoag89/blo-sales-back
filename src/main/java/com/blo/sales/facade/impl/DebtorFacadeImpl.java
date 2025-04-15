package com.blo.sales.facade.impl;

import java.math.BigDecimal;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IDebtorFacade;
import com.blo.sales.facade.dto.DtoDebtor;
import com.blo.sales.facade.dto.DtoDebtors;
import com.blo.sales.facade.dto.DtoPartialPyment;
import com.blo.sales.facade.enums.StatusCashboxEnum;
import com.blo.sales.facade.mapper.DtoDebtorMapper;
import com.blo.sales.facade.mapper.DtoDebtorsMapper;
import com.blo.sales.facade.mapper.DtoPartialPymentMapper;

@RestController
public class DebtorFacadeImpl implements IDebtorFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorFacadeImpl.class);
	
	@Autowired
	private IDebtorsBusiness business;
	
	@Autowired
	private ISalesBusiness sales;
	
	@Autowired
	private ICashboxBusiness cashboxes;
	
	@Autowired
	private DtoDebtorMapper debtorMapper;

	@Autowired
	private DtoDebtorsMapper debtorsMapper;
	
	@Autowired
	private DtoPartialPymentMapper partialPymentMapper;

	@Override
	public ResponseEntity<DtoDebtor> retrieveDebtorById(String id) {
		try {
			var debtorFound = business.getDebtorById(id);
			LOGGER.info(String.format("Debtor found %s", String.valueOf(debtorFound)));
			var out = debtorMapper.toOuter(debtorFound);
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
			var out = debtorsMapper.toOuter(debtors);
			return new ResponseEntity<DtoDebtors>(out, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoDebtor> addPay(String id, long time, DtoPartialPyment partialPyment) {
		try {
			LOGGER.info(String.format("adding payment %s to %s", Encode.forJava(String.valueOf(partialPyment)), id));
			var partialPymentMapped = partialPymentMapper.toInner(partialPyment);
			var saved = business.addPay(id, partialPymentMapped, time);
			
			var currentCashbox = cashboxes.getCashboxOpen();
			// valida si hay un nuevo cashbox
			if (currentCashbox == null) {
				// abre una caja
				LOGGER.info("saving a new cashbox");
				var dataCashbox = new DtoIntCashbox();
				dataCashbox.setDate(time);
				dataCashbox.setMoney(partialPymentMapped.getPartial_pyment());
				dataCashbox.setStatus(StatusCashboxIntEnum.valueOf(StatusCashboxEnum.OPEN.name()));
				currentCashbox = cashboxes.saveCashbox(dataCashbox);
				LOGGER.info(String.format("cashbox saved %s", String.valueOf(currentCashbox)));
			} else {
				// agrega dinero a la caja actual
				LOGGER.info("cashbox exists");
				var money = currentCashbox.getMoney().add(partialPymentMapped.getPartial_pyment());
				currentCashbox.setMoney(money);
				cashboxes.updateCashbox(currentCashbox.getId(), currentCashbox);
				LOGGER.info(String.format("cashbox updated", String.valueOf(cashboxes)));
			}
			
			// si la cuenta actual es menor o igual a cero entonces se va a eliminar el deudor y cerrar sus ventas
			if (saved.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
				// cerrar las ventas
				for (var sale: saved.getSales()) {
					LOGGER.info(String.format("search sale %s", sale.getId()));
					var saleFound = sales.getSaleById(sale.getId());
					LOGGER.info("Sale found");
					saleFound.setClose_sale(time);
					var saleUpdated = sales.updateSale(sale.getId(), saleFound);
					LOGGER.info(String.format("sale updated", String.valueOf(saleUpdated)));
				}
				// elimina deudor
				business.deleteDebtorById(id);
				LOGGER.info(String.format("%s was deleted, account was payed", id));
				return new ResponseEntity<>(null, HttpStatus.OK);
			}
			
			var out = debtorMapper.toOuter(saved);
			LOGGER.info(String.format("partial payment was added to %s", id));
			return new ResponseEntity<DtoDebtor>(out, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

}
