package com.blo.sales.dao.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.dao.ISalesDao;
import com.blo.sales.dao.docs.Sales;
import com.blo.sales.dao.mapper.SaleMapper;
import com.blo.sales.dao.mapper.SalesMapper;
import com.blo.sales.dao.repository.SalesRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class SalesDaoImpl implements ISalesDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesDaoImpl.class);
	
	@Autowired
	private SalesRepository repository;
	
	@Autowired
	private SalesMapper salesMapper;
	
	@Autowired
	private SaleMapper saleMapper;

	@Value("${exceptions.codes.sale-not-found}")
	private String saleNotFoundCode;
	
	@Value("${exceptions.messages.sale-not-found}")
	private String saleNotFoundMessage;

	@Override
	public DtoIntSale addSale(DtoIntSale sale) throws BloSalesBusinessException {
		LOGGER.info(String.format("saving sale %s", Encode.forJava(String.valueOf(sale))));
		var inSale = saleMapper.toInner(sale);
		var saved = repository.save(inSale);
		var out = saleMapper.toOuter(saved);
		LOGGER.info(String.format("Sale saved %s", String.valueOf(out)));
		return out;
		
	}

	@Override
	public DtoIntSales getSales() throws BloSalesBusinessException {
		var sales = repository.findAll();
		LOGGER.info(String.format("Sales found %s", String.valueOf(sales)));
		var salesWrapper = new Sales();
		salesWrapper.setSales(sales);
		return salesMapper.toOuter(salesWrapper);
	}
	
	@Override
	public DtoIntSales getSalesOpen() throws BloSalesBusinessException {
		var openSales = repository.findSalesOpen();
		LOGGER.info(String.format("open sales found %s", String.valueOf(openSales)));
		
		var salesWrapper = new Sales();
		salesWrapper.setSales(openSales);
		
		return salesMapper.toOuter(salesWrapper);
	}
	
	@Override
	public DtoIntSales getSalesClose() throws BloSalesBusinessException {
		var closeSales = repository.findSalesClosed();
		
		LOGGER.info(String.format("close sales found %s", String.valueOf(closeSales)));
		
		var salesWrapper = new Sales();
		salesWrapper.setSales(closeSales);
		
		return salesMapper.toOuter(salesWrapper);
	}

	@Override
	public DtoIntSale getSaleById(String id) throws BloSalesBusinessException {
		var saleFound = repository.findById(id);
		if (!saleFound.isPresent()) {
			LOGGER.error(String.format("sale id %s not found", id));
			throw new BloSalesBusinessException(saleNotFoundMessage, saleNotFoundCode, HttpStatus.NOT_FOUND);
		}
		
		var sale = saleMapper.toOuter(saleFound.get());;
		LOGGER.info(String.format("sale found %s", String.valueOf(sale)));
		return sale;
	}

	@Override
	public DtoIntSale updateSale(String id, DtoIntSale sale) throws BloSalesBusinessException {
		var saleFound = getSaleById(id);
		LOGGER.info(String.format("sale new data %s", Encode.forJava(String.valueOf(sale))));
		saleFound.set_on_cashbox(sale.is_on_cashbox());
		saleFound.setClose_sale(sale.getClose_sale());
		var saleForUpdate = saleMapper.toInner(saleFound);
		
		var saved = repository.save(saleForUpdate);
		var out = saleMapper.toOuter(saved);
		LOGGER.info(String.format("sale updated %s", String.valueOf(out)));
		return out;
	}


	@Override
	public DtoIntSales getSalesNotCashbox() throws BloSalesBusinessException {
		LOGGER.info("retrieving sales no cashbox");
		var sales = repository.findSaleNoCashbox();
		var salesWrapper = new Sales();
		salesWrapper.setSales(sales);
		LOGGER.info(String.format("sales no cashbox %s", String.valueOf(salesWrapper)));
		
		var salesOut = salesMapper.toOuter(salesWrapper);
		
		LOGGER.info(String.format("sales is not cashbox %s", String.valueOf(salesOut)));
		return salesOut;
	}
	
}
