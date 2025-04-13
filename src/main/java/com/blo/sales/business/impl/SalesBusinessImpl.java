package com.blo.sales.business.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.IProductsBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSaleProduct;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.business.dto.DtoIntWrapperSale;
import com.blo.sales.dao.ISalesDao;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class SalesBusinessImpl implements ISalesBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SalesBusinessImpl.class);
	
	@Autowired
	private ISalesDao dao;
	
	/*@Autowired
	private IDebtorsBusiness debtorBusiness;
	
	@Autowired
	private IProductsBusiness productsBusiness;
	
	@Value("${exceptions.codes.field-is-empty}")
	private String fieldIsEmptyCdode;*/
	
	//@Value("${exceptions.codes.product-insufficient}")
	//private String productInsufficientCode;
	
	@Value("${exceptions.messages.field-is-empty}")
	private String fieldsIsEmptyMessage;
	
	//@Value("${exceptions.messages.product-insufficient}")
	//private String productInsufficientMessage;
	
	/*@Override
	public DtoIntWrapperSale saveSaleAndDebtor(DtoIntSale sale, DtoIntDebtor debtor, BigDecimal partialPyment) throws BloSalesBusinessException {
		LOGGER.info(String.format("saving sale %s and debtor %s", Encode.forJava(String.valueOf(sale)), Encode.forJava(String.valueOf(debtor))));
		
		var output = new DtoIntWrapperSale();
		
		output.setProductsWithAlerts(getProductsAlertsAndUpdate(sale.getProducts()));
		// valida si el total es igual a la suma total de los productos
		LOGGER.info("sale is complete, saving");
		var saleSaved = dao.addSale(sale);
		LOGGER.info(String.format("sale saved %s", Encode.forJava(String.valueOf(saleSaved))));
		output.setSale(sale);
		
		LOGGER.info("Saving debtor");
		if (debtor == null) {
			LOGGER.error("Debtor cannot be null");
			throw new BloSalesBusinessException(fieldsIsEmptyMessage, fieldIsEmptyCdode, HttpStatus.BAD_REQUEST);
		}
		
		// nuevo deudor
		if (StringUtils.isEmpty(debtor.getId())) {
			LOGGER.info(String.format("saving new debtor", Encode.forJava(String.valueOf(debtor))));
			List<DtoIntSale> sales = new ArrayList<>();
			sales.add(sale);
			debtor.setSales(sales);
			var debtorSaved = debtorBusiness.addDebtor(debtor);
			output.setDetor(debtor);
			LOGGER.info(String.format("debtor was saved %s", String.valueOf(debtorSaved)));
			return output;
		}
		
		// deudor existente
		var debtorFound = debtorBusiness.getDebtorById(debtor.getId());
		
		// actualiza su lista compras
		debtorFound.getSales().addAll(debtor.getSales());
		var newAmount = new BigDecimal("0");
		//actualiza el monto
		// no dejo algún pago
		if (partialPyment.compareTo(BigDecimal.ZERO) == 0) {
			LOGGER.info("not partial pyment flow");
			newAmount = debtorFound.getTotal().add(debtor.getTotal());
			debtorFound.setTotal(newAmount);
		}
		// dejó un pago
		if (partialPyment.compareTo(BigDecimal.ZERO) > 0) {
			LOGGER.info("partial pyment flow");
			// si el pago que dejó es mayor que la deuda entonces se elimina el deudor
			newAmount = debtorFound.getTotal().subtract(partialPyment);
			if (newAmount.compareTo(debtorFound.getTotal()) < 0) {
				LOGGER.info("partial pyment is more that debt");
				debtorBusiness.deleteDebtorById(debtorFound.getId());
				return output;
			}
			LOGGER.info("update partial pyment");
			debtorFound.setTotal(newAmount);
		}
		
		var debtorSaved = debtorBusiness.updateDebtor(debtorFound.getId(), debtorFound);
		LOGGER.info(String.format("saved debtor %s width debt %s", Encode.forJava(String.valueOf(debtorSaved))), newAmount);
		output.setDetor(debtorSaved);
		return output;
	}*/

	@Override
	public DtoIntWrapperSale addSale(DtoIntSale sale) throws BloSalesBusinessException {
		LOGGER.info(String.format("saving sale %s", Encode.forJava(String.valueOf(sale))));
		var out = new DtoIntWrapperSale();
		var saleSaved = dao.addSale(sale);
		out.setProductsWithAlerts(getProductsAlertsAndUpdate(sale.getProducts()));
		out.setSale(saleSaved);
		return out;
	}

	@Override
	public DtoIntSales getSales() throws BloSalesBusinessException {
		LOGGER.info("retrieving all sales");
		return dao.getSales();
	}
	
	@Override
	public DtoIntSales getSalesOpen() throws BloSalesBusinessException {
		LOGGER.info("retrieving open sales");
		return dao.getSales();
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
	
	/*private List<DtoIntProduct> getProductsAlertsAndUpdate(List<DtoIntSaleProduct> products) throws BloSalesBusinessException {
		
		final List<DtoIntProduct> productsWithAlert = new ArrayList<>();
		for (var product: products) {
			LOGGER.info(String.format("Validating product %s", Encode.forJava(String.valueOf(product))));
			var productFound = productsBusiness.getProduct(product.getId());
			var newQuantity = productFound.getQuantity().subtract(product.getQuantity_on_sale());
			LOGGER.info(String.format("update quantity %s", newQuantity));
			
			// productos insufcientes
			if (newQuantity.compareTo(BigDecimal.ZERO) < 0) {
				throw new BloSalesBusinessException(productInsufficientMessage, productInsufficientCode, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			if (newQuantity.compareTo(new BigDecimal("2")) <= 0) {
				productsWithAlert.add(productFound);
			}
			
			productFound.setQuantity(newQuantity);
			LOGGER.info(String.format("product data %s", String.valueOf(productFound)));
			productsBusiness.updateProduct(productFound.getId(), product);
		}
		
		return productsWithAlert;
	}*/

}
