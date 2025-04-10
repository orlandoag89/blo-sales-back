package com.blo.sales.facade.impl;

import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.ISalesFacade;
import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.facade.dto.DtoSales;
import com.blo.sales.facade.enums.StatusSaleEnum;

@RestController
public class SalesFacadeImpl implements ISalesFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesFacadeImpl.class);
	
	@Autowired
	private ISalesBusiness business;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public ResponseEntity<DtoSale> registerSale(DtoSale sale) {
		LOGGER.info(String.format("registering sale %s", Encode.forJava(String.valueOf(sale))));
		try {
			var saleIn = modelMapper.map(sale, DtoIntSale.class);
			var saleSaved = business.addSale(saleIn);
			LOGGER.info(String.format("sale registered %s", String.valueOf(saleSaved)));
			var out = modelMapper.map(saleSaved, DtoSale.class);
			return new ResponseEntity<DtoSale>(out, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoSales> retrieveSales(StatusSaleEnum status) {
		LOGGER.info(String.format("retrieving sales by flag %s", status));
		try {
			var out = new DtoSales();
			var sales = new DtoIntSales();
			if (status.name() == "ALL") {
				sales = business.getSales();
				LOGGER.info(String.format("all sales %s", String.valueOf(sales)));
			}
			if (status.name() == "OPEN") {
				sales = business.getSalesOpen();
				LOGGER.info(String.format("open sales %s", String.valueOf(sales)));
			}
			if (status.name() == "CLOSE") {
				sales = business.getSalesClose();
				LOGGER.info(String.format("close sales %s", String.valueOf(sales)));
			}
			out = modelMapper.map(sales, DtoSales.class);
			LOGGER.info(String.format("sales %s found %s", String.valueOf(out), status));
			return new ResponseEntity<DtoSales>(out, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoSale> retrieveSaleById(String id) {
		LOGGER.info(String.format("retrieving sales by flag %s", id));
		try {
			var sale = business.getSaleById(id);
			var out = modelMapper.map(sale, DtoSale.class);
			return new ResponseEntity<DtoSale>(out, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

}
