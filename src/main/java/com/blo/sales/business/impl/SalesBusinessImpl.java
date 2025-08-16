package com.blo.sales.business.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntProductsOnSalesCounter;
import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.business.dto.DtoIntSalesDetailReport;
import com.blo.sales.dao.ISalesDao;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class SalesBusinessImpl implements ISalesBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesBusinessImpl.class);
	
	@Autowired
	private ISalesDao dao;
	
	@Override
	public DtoIntSale addSale(DtoIntSale sale) throws BloSalesBusinessException {
		LOGGER.info(String.format("saving sale %s", Encode.forJava(String.valueOf(sale))));
		return dao.addSale(sale);
	}

	@Override
	public DtoIntSales getSales() throws BloSalesBusinessException {
		LOGGER.info("retrieving all sales");
		return dao.getSales();
	}
	
	@Override
	public DtoIntSales getSalesOpen() throws BloSalesBusinessException {
		LOGGER.info("retrieving open sales");
		return dao.getSalesOpen();
	}

	@Override
	public DtoIntSales getSalesClose() throws BloSalesBusinessException {
		LOGGER.info("retrieving open sales");
		return dao.getSalesClose();
	}

	@Override
	public DtoIntSale getSaleById(String id) throws BloSalesBusinessException {
		LOGGER.info("get sale by id");
		return dao.getSaleById(id);
	}
	
	@Override
	public DtoIntSale updateSale(String id, DtoIntSale sale) throws BloSalesBusinessException {
		LOGGER.info("updating sale by id");
		return dao.updateSale(id, sale);
	}
	
	@Override
	public DtoIntSales getSalesNotCashbox() throws BloSalesBusinessException {
		LOGGER.info("retrieving sales no cashbox");
		return dao.getSalesNotCashbox();
	}

	@Override
	public DtoIntProductsOnSalesCounter getBestSellingProducts(int initMonth, int initYear, int endMonth, int endYear) throws BloSalesBusinessException {
		LOGGER.info("recuperando informacion de ventas");
		return dao.getBestSellingProducts(initMonth, initYear, endMonth, endYear);
	}

	@Override
	public DtoIntSalesDetailReport getSalesByDate(int initMonth, int initYear, int endMonth, int endYear) {
		LOGGER.info("recuperando ventas por fecha");
		return dao.getSalesByDate(initMonth, initYear, endMonth, endYear);
	}

}
