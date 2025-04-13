package com.blo.sales.facade.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.IProductsBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.ISalesFacade;
import com.blo.sales.facade.dto.DtoDebtor;
import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.facade.dto.DtoSaleProduct;
import com.blo.sales.facade.dto.DtoSales;
import com.blo.sales.facade.dto.DtoWrapperSale;
import com.blo.sales.facade.enums.StatusSaleEnum;

@RestController
public class SalesFacadeImpl implements ISalesFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesFacadeImpl.class);
	
	@Autowired
	private ISalesBusiness business;
	
	@Autowired
	private IDebtorsBusiness debtorBusiness;
	
	@Autowired
	private IProductsBusiness productsBusiness;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${exceptions.messages.product-insufficient}")
	private String productInsufficientMessage;
	
	@Value("${exceptions.codes.product-insufficient}")
	private String productInsufficientCode;
	
	@Override
	public ResponseEntity<DtoWrapperSale> registerSale(DtoSale sale) {
		LOGGER.info(String.format("registering sale %s", Encode.forJava(String.valueOf(sale))));
		try {
			var out = new DtoWrapperSale();
			var productsAlert = getProductsAlertsAndUpdate(sale.getProducts());
			
			var saleIn = modelMapper.map(sale, DtoIntSale.class);
			var saleSaved = business.addSale(saleIn);
			
			LOGGER.info(String.format("sale registered %s", String.valueOf(saleSaved)));
			out.setSale(modelMapper.map(saleSaved, DtoSale.class));
			out.setProductsWithAlerts(productsAlert);
			return new ResponseEntity<DtoWrapperSale>(out, HttpStatus.CREATED);
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
			if (status.name().equals(StatusSaleEnum.ALL.name())) {
				sales = business.getSales();
				LOGGER.info(String.format("all sales %s", String.valueOf(sales)));
			}
			if (status.name().equals(StatusSaleEnum.OPEN.name())) {
				sales = business.getSalesOpen();
				LOGGER.info(String.format("open sales %s", String.valueOf(sales)));
			}
			if (status.name().equals(StatusSaleEnum.CLOSE.name())) {
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

	@Override
	public ResponseEntity<DtoWrapperSale> registerSaleAndDebtor(DtoWrapperSale sale, BigDecimal partialPyment) {
		try {
			/*LOGGER.info(String.format("sale data %s and total %s", Encode.forJava(String.valueOf(sale)), String.valueOf(totalDebtor)));
			var saleMapped = modelMapper.map(sale.getSale(), DtoIntSale.class);
			var debtorMapped = modelMapper.map(sale.getDetor(), DtoIntDebtor.class);
			var wrapperSaleSaved = business.saveSaleAndDebtor(saleMapped, debtorMapped, totalDebtor);
			var out = modelMapper.map(wrapperSaleSaved, DtoWrapperSale.class);
			LOGGER.info(String.format("sale has been saved %s", String.valueOf(out)));
			return new ResponseEntity<>(out, HttpStatus.CREATED);*/
			
			LOGGER.info(String.format("saving debtor %s", Encode.forJava(String.valueOf(sale.getDetor()))));
			if (sale.getDetor() == null) {
				LOGGER.error("debtor is required");
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			
			var isNewDebtor = StringUtils.isBlank(sale.getDetor().getId());
			DtoIntDebtor debtorInDb = null;
			if (!isNewDebtor) {
				// valida existencia de deudor por id
				debtorInDb = debtorBusiness.getDebtorById(sale.getDetor().getId());
				LOGGER.info(String.format("debtor found %s", String.valueOf(debtorInDb)));
			}
			
			var output = new DtoWrapperSale();
			
			LOGGER.info(String.format("new data info %s", Encode.forJava(String.valueOf(sale))));
			output.setProductsWithAlerts(getProductsAlertsAndUpdate(sale.getSale().getProducts()));
			var saleInn = modelMapper.map(sale.getSale(), DtoIntSale.class);
			var saleSaved = business.addSale(saleInn);
			LOGGER.info(String.format("sale saved %s", String.valueOf(saleSaved)));
			var saleSavedOut = modelMapper.map(saleSaved, DtoSale.class);
			output.setSale(saleSavedOut);
			
			// nuevo deudor flujo
			if (StringUtils.isBlank(sale.getDetor().getId())) {
				LOGGER.info(String.format("New debor %s", Encode.forJava(String.valueOf(sale.getDetor()))));
				List<DtoSale> sales = new ArrayList<>();
				sales.add(saleSavedOut);
				sale.getDetor().setSales(sales);
				var debtorToSave = modelMapper.map(sale.getDetor(), DtoIntDebtor.class);
				var debtorSaved = debtorBusiness.addDebtor(debtorToSave);
				var out = modelMapper.map(debtorSaved, DtoDebtor.class);
				LOGGER.info(String.format("Debtor saved %s", String.valueOf(out)));
				output.setDetor(out);
				return new ResponseEntity<DtoWrapperSale>(output, HttpStatus.CREATED);
			}
			
			//actualizar deudor
			if (debtorInDb == null) {
				LOGGER.error(String.format("debtor not found %s", String.valueOf(debtorInDb)));
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			
			// actualiza su lista compras
			debtorInDb.getSales().add(saleSaved);
			var newAmount = new BigDecimal("0");
			// actualiza montos
			
			// no dejó algún pago
			if (partialPyment.compareTo(BigDecimal.ZERO) == 0) {
				LOGGER.info("not partial pyment flow");
				newAmount = debtorInDb.getTotal().add(sale.getDetor().getTotal());
				debtorInDb.setTotal(newAmount);
			}
			//dejó pago
			if (partialPyment.compareTo(BigDecimal.ZERO) > 0) {
				LOGGER.info("partial pyment flow");
				newAmount = debtorInDb.getTotal().subtract(partialPyment);
				// pago todo
				if (newAmount.compareTo(BigDecimal.ZERO) <= 0) {
					LOGGER.info("partial pyment is more that debt");
					debtorBusiness.deleteDebtorById(debtorInDb.getId());
					output.setDetor(new DtoDebtor());
					return new ResponseEntity<>(output, HttpStatus.CREATED);
				}
				LOGGER.info("update partial pyment");
				debtorInDb.setTotal(newAmount);
			}
			
			var debtorUpdated = debtorBusiness.updateDebtor(debtorInDb.getId(), debtorInDb);
			LOGGER.info(String.format("debtor updated %s", String.valueOf(debtorUpdated)));
			var mappedDebtor = modelMapper.map(debtorUpdated, DtoDebtor.class);
			output.setDetor(mappedDebtor);
			return new ResponseEntity<>(output, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.info(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}
	
	private List<DtoProduct> getProductsAlertsAndUpdate(List<DtoSaleProduct> products) throws BloSalesBusinessException {
		
		final List<DtoProduct> productsWithAlert = new ArrayList<>();
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
				var productOut = modelMapper.map(productFound, DtoProduct.class);
				productsWithAlert.add(productOut);
			}
			
			productFound.setQuantity(newQuantity);
			LOGGER.info(String.format("product data %s", String.valueOf(productFound)));
			productsBusiness.updateProduct(productFound.getId(), productFound);
		}
		
		return productsWithAlert;
	}

}
