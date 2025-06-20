package com.blo.sales.facade.impl;

import java.math.BigDecimal;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IDebtorFacade;
import com.blo.sales.facade.dto.DtoDebtor;
import com.blo.sales.facade.dto.DtoDebtors;
import com.blo.sales.facade.dto.DtoPartialPyment;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.dto.commons.DtoError;
import com.blo.sales.facade.mapper.DtoDebtorMapper;
import com.blo.sales.facade.mapper.DtoDebtorsMapper;
import com.blo.sales.facade.mapper.DtoPartialPymentMapper;
import com.blo.sales.utils.Utils;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300", "http://localhost:4400"})
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
	
	@Value("${exceptions.messages.debtor-not-valid}")
	private String exceptionsMessagesDebtorNotValid;
	
	@Value("${exceptions.codes.debtor-not-valid}")
	private String exceptionsCodesDebtorNotValid;

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoDebtor>> retrieveDebtorById(String id) {
		var output = new DtoCommonWrapper<DtoDebtor>();
		try {
			Utils.isStringIsBlankOrUndefined(id, exceptionsMessagesDebtorNotValid, exceptionsCodesDebtorNotValid);
			
			var debtorFound = business.getDebtorById(id);
			LOGGER.info(String.format("Debtor found %s", String.valueOf(debtorFound)));
			var out = debtorMapper.toOuter(debtorFound);
			output.setData(out);
			return new ResponseEntity<>(output, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			output.setError(error);
			return new ResponseEntity<>(output, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoDebtors>> retrieveAllDebtors() {
		var output = new DtoCommonWrapper<DtoDebtors>();
		try {
			var debtors = business.getDebtors();
			LOGGER.info(String.format("Debtors %s", String.valueOf(debtors)));
			var out = debtorsMapper.toOuter(debtors);
			output.setData(out);
			return new ResponseEntity<>(output, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getMessage());
			output.setError(error);
			return new ResponseEntity<>(output, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoDebtor>> addPay(String id, long time, DtoPartialPyment partialPyment) {
		var output = new DtoCommonWrapper<DtoDebtor>();
		try {
			
			Utils.isStringIsBlankOrUndefined(id, exceptionsMessagesDebtorNotValid, exceptionsCodesDebtorNotValid);
			
			LOGGER.info(String.format("adding payment %s to %s", Encode.forJava(String.valueOf(partialPyment)), id));
			var partialPymentMapped = partialPymentMapper.toInner(partialPyment);
			var saved = business.addPay(id, partialPymentMapped, time);
			LOGGER.info(String.format("datos de deudor actualizados %s", String.valueOf(saved)));			
			// si la cuenta actual es menor o igual a cero entonces se va a eliminar el deudor y cerrar sus ventas
			if (saved.getTotal().compareTo(BigDecimal.ZERO) <= 0) {
				// cerrar las ventas
				for (var sale: saved.getSales()) {
					LOGGER.info(String.format("search sale %s", sale.getId()));
					var saleFound = sales.getSaleById(sale.getId());
					LOGGER.info("Sale found");
					saleFound.setClose_sale(time);
					saleFound.set_on_cashbox(true);
					var saleUpdated = sales.updateSale(sale.getId(), saleFound);
					LOGGER.info(String.format("sale updated %s", String.valueOf(saleUpdated)));
				}
				// elimina deudor
				business.deleteDebtorById(id);
				LOGGER.info(String.format("%s was deleted, account was payed", id));
				output.setData(new DtoDebtor());
				// guarda el dinero en una caja
				var money = partialPyment.getPartial_pyment().add(saved.getTotal());
				//saveMoneyOnCashbox(time, money);
				cashboxes.addingCash(money, time);
				return new ResponseEntity<>(output, HttpStatus.OK);
			}
			//saveMoneyOnCashbox(time, partialPymentMapped.getPartial_pyment());
			cashboxes.addingCash(partialPyment.getPartial_pyment(), time);
			var out = debtorMapper.toOuter(saved);
			LOGGER.info(String.format("partial payment was added to %s", id));
			output.setData(out);
			return new ResponseEntity<>(output, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			output.setError(error);
			return new ResponseEntity<>(output, e.getExceptHttpStatus());
		}
	}
	
}
