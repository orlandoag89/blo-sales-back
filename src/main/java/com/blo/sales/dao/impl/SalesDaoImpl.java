package com.blo.sales.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
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
import com.blo.sales.dao.docs.Sale;
import com.blo.sales.dao.repository.SalesRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class SalesDaoImpl implements ISalesDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesDaoImpl.class);
	
	@Autowired
	private SalesRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;

	@Value("${exceptions.codes.not-found}")
	private String notFoundCode;
	
	@Value("${exceptions.messages.not-found}")
	private String notFoundMessage;

	@Override
	public DtoIntSale addSale(DtoIntSale sale) throws BloSalesBusinessException {
		LOGGER.info(String.format("saving sale %s", Encode.forJava(String.valueOf(sale))));
		var inSale = modelMapper.map(sale, Sale.class);
		var saved = repository.save(inSale);
		var out = modelMapper.map(saved, DtoIntSale.class);
		LOGGER.info(String.format("Sale saved %s", String.valueOf(out)));
		return out;
	}

	@Override
	public DtoIntSales getSales() throws BloSalesBusinessException {
		var sales = repository.findAll();
		
		return toDtoIntSales(sales);
	}
	
	@Override
	public DtoIntSales getSalesOpen() throws BloSalesBusinessException {
		var openSales = repository.findSalesOpen();
		
		return toDtoIntSales(openSales);
	}
	
	@Override
	public DtoIntSales getSalesClose() throws BloSalesBusinessException {
		var closeSales = repository.findSalesClosed();
		
		return toDtoIntSales(closeSales);
	}

	@Override
	public DtoIntSale getSaleById(String id) throws BloSalesBusinessException {
		var saleFound = repository.findById(id);
		if (!saleFound.isPresent()) {
			LOGGER.error(String.format("sale id %s not found", id));
			throw new BloSalesBusinessException(notFoundMessage, notFoundCode, HttpStatus.NOT_FOUND);
		}
		
		var sale = modelMapper.map(saleFound, DtoIntSale.class);
		LOGGER.info(String.format("sale found %s", String.valueOf(sale)));
		return sale;
	}

	@Override
	public DtoIntSale updateSale(String id, DtoIntSale sale) throws BloSalesBusinessException {
		var saleFound = getSaleById(id);
		LOGGER.info(String.format("sale new data %s", Encode.forJava(String.valueOf(sale))));
		saleFound.set_on_cashbox(sale.is_on_cashbox());
		saleFound.setClose_sale(sale.getClose_sale());
		var saleForUpdate = modelMapper.map(saleFound, Sale.class);
		
		var saved = repository.save(saleForUpdate);
		var out = modelMapper.map(saved, DtoIntSale.class);
		LOGGER.info(String.format("sale updated %s", String.valueOf(out)));
		return out;
	}

	/** convierte las ventas de la base de datos a dto */
	private DtoIntSales toDtoIntSales(List<Sale> sales){
		var salesOut = new DtoIntSales();
		if (sales == null || sales.size() == 0) {
			LOGGER.info("sales list is empty");
			return salesOut;
		}
		List<DtoIntSale> salesItems = new ArrayList<>();
		sales.forEach(sale -> {
			LOGGER.info(String.format("parsing %s", String.valueOf(sale)));
			var item = modelMapper.map(sale, DtoIntSale.class);
			salesItems.add(item);
		});
		salesOut.setSales(salesItems);
		
		LOGGER.info(String.format("sales on db %s", String.valueOf(salesOut)));
		return salesOut;
	}

	@Override
	public DtoIntSales getSalesNotCashbox() throws BloSalesBusinessException {
		LOGGER.info("retrieving sales no cashbox");
		var sales = repository.findSaleNoCashbox();
		DtoIntSales salesOut = new DtoIntSales();
		List<DtoIntSale> salesItems = new ArrayList<>();
		
		sales.forEach(s -> {
			var item = modelMapper.map(s, DtoIntSale.class);
			salesItems.add(item);
		});
		
		salesOut.setSales(salesItems);
		
		LOGGER.info(String.format("sales is not cashbox %s", String.valueOf(salesOut)));
		return salesOut;
	}
	
}
