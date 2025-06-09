package com.blo.sales.facade.impl;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.IProductsBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.ISalesFacade;
import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.facade.dto.DtoSaleProduct;
import com.blo.sales.facade.dto.DtoSales;
import com.blo.sales.facade.dto.DtoWrapperSale;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.dto.commons.DtoError;
import com.blo.sales.facade.enums.StatusCashboxEnum;
import com.blo.sales.facade.enums.StatusSaleEnum;
import com.blo.sales.facade.mapper.DtoCashboxMapper;
import com.blo.sales.facade.mapper.DtoDebtorMapper;
import com.blo.sales.facade.mapper.DtoProductMapper;
import com.blo.sales.facade.mapper.DtoSaleMapper;
import com.blo.sales.facade.mapper.DtoSalesMapper;
import com.blo.sales.utils.Utils;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class SalesFacadeImpl implements ISalesFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(SalesFacadeImpl.class);
	
	@Autowired
	private ISalesBusiness business;
	
	@Autowired
	private ICashboxBusiness cashboxBusiness;
	
	@Autowired
	private IDebtorsBusiness debtorBusiness;
	
	@Autowired
	private IProductsBusiness productsBusiness;
	
	@Autowired
	private DtoSalesMapper salesMapper;
	
	@Autowired
	private DtoSaleMapper saleMapper;
	
	@Autowired
	private DtoProductMapper productMapper;
	
	@Autowired
	private DtoDebtorMapper debtorMapper;
	
	@Autowired
	private DtoCashboxMapper cashboxMapper;
	
	@Value("${exceptions.messages.product-insufficient}")
	private String productInsufficientMessage;
	
	@Value("${exceptions.codes.product-insufficient}")
	private String productInsufficientCode;
	
	@Value("${exceptions.codes.not-found}")
	private String exceptionsCodesNotFound;
	
	@Value("${exceptions.messages.not-found}")
	private String exceptionsMessagesNotFound;
	
	@Value("${exceptions.codes.field-is-empty}")
	private String exceptionsCodesFieldsIsEmpty;
	
	@Value("${exceptions.messages.field-is-empty}")
	private String exceptionsMessagesFieldsIsEmpty;
	
	@Value("${excpetions.codes.no-equals}")
	private String exceptionsCodesNoEquals;
	
	@Value("${excpetions.messages.no-equals}")
	private String excpetionsMessagesNoEquals;
	
	@Value("${exceptions.codes.total-not-valid}")
	private String exceptionsCodesTotalNotValid;
	
	@Value("${exceptions.messages.total-not-valid}")
	private String exceptionsMessagesTotalNotValid;
	
	@Override
	public ResponseEntity<DtoCommonWrapper<DtoWrapperSale>> registerSale(DtoSale sale) {
		var output = new DtoCommonWrapper<DtoWrapperSale>();
		LOGGER.info(String.format("registering sale %s", Encode.forJava(String.valueOf(sale))));
		try {
			var out = new DtoWrapperSale();
			var productsAlert = getProductsAlertsAndUpdate(sale.getProducts());
			
			var saleIn = saleMapper.toInner(sale);
			
			//valida si es una compra interna que no debe ser registrada en la caja de dinero
			if (saleIn.getTotal().compareTo(new BigDecimal("-1")) == 0) {
				saleIn.setTotal(BigDecimal.ZERO);
			}
			
			var saleSaved = business.addSale(saleIn);
			cashboxAddingCash(sale.getTotal(), sale.getClose_sale());
			LOGGER.info(String.format("sale registered %s", String.valueOf(saleSaved)));
			out.setSale(saleMapper.toOuter(saleSaved));
			out.setProductsWithAlerts(productsAlert);
			output.setData(out);
			return new ResponseEntity<>(output, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			output.setError(error);
			return new ResponseEntity<>(output, e.getExceptionHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoSales>> retrieveSales(StatusSaleEnum status) {
		LOGGER.info(String.format("retrieving sales by flag %s", status));
		var output = new DtoCommonWrapper<DtoSales>();
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
			if (status.name().equals(StatusSaleEnum.NOT_CASHBOX.name())) {
				sales = business.getSalesNotCashbox();
				LOGGER.info(String.format("ventas no en caja %s", String.valueOf(sales)));
			}
			out = salesMapper.toOuter(sales);
			LOGGER.info(String.format("sales %s found %s", String.valueOf(out), status));
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
	public ResponseEntity<DtoCommonWrapper<DtoSale>> retrieveSaleById(String id) {
		LOGGER.info(String.format("retrieving sales by flag %s", id));
		var output = new DtoCommonWrapper<DtoSale>();
		try {
			Utils.isStringIsBlankOrUndefined(id, exceptionsMessagesNotFound, exceptionsCodesNotFound);
			var sale = business.getSaleById(id);
			var out = saleMapper.toOuter(sale);
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
	public ResponseEntity<DtoCommonWrapper<DtoWrapperSale>> registerSaleAndDebtor(DtoWrapperSale saleData, BigDecimal partialPyment, long time) {
		var wrapperResponse = new DtoCommonWrapper<DtoWrapperSale>();
		try {
			LOGGER.info(String.format("saving debtor %s", Encode.forJava(String.valueOf(saleData.getDebtor()))));
			if (saleData.getDebtor() == null) {
				LOGGER.error("debtor is required");
				throw new BloSalesBusinessException(exceptionsCodesFieldsIsEmpty, exceptionsMessagesFieldsIsEmpty, HttpStatus.BAD_REQUEST);
			}
			
			// valida que los totales sean iguales
			if (saleData.getSale().getTotal().compareTo(saleData.getDebtor().getTotal()) != 0) {
				LOGGER.error("totals is not equals");
				throw new BloSalesBusinessException(excpetionsMessagesNoEquals, exceptionsCodesNoEquals, HttpStatus.BAD_REQUEST);
			}
			
			// resta entre el total de la venta menos el pago parcial
			var totalFromProducts = saleData.getSale().getProducts().stream().
				map(p -> p.getQuantity_on_sale().multiply(p.getTotal_price())).
				reduce(BigDecimal.ZERO, BigDecimal::add);
			LOGGER.info(String.format("total from products %s", totalFromProducts));
			var totalSubPartial = totalFromProducts.subtract(partialPyment);
			LOGGER.info(String.format("diff total - partial pyment = %s", totalSubPartial));
			// si la diferencia entre la cuenta total y pago parcial no es igual al pago parcial envia un error
			if (totalSubPartial.compareTo(saleData.getDebtor().getTotal()) != 0 && totalFromProducts.compareTo(saleData.getSale().getTotal()) != 0) {
				LOGGER.error("ambos totales no son la diferencia entre total menos pago parcial");
				throw new BloSalesBusinessException(exceptionsMessagesTotalNotValid, exceptionsCodesTotalNotValid, HttpStatus.BAD_REQUEST);
			}
			
			var isNewDebtor = StringUtils.isBlank(saleData.getDebtor().getId());
			DtoIntDebtor debtorInDb = null;
			if (!isNewDebtor) {
				// valida existencia de deudor por id
				debtorInDb = debtorBusiness.getDebtorById(saleData.getDebtor().getId());
				LOGGER.info(String.format("debtor found %s", String.valueOf(debtorInDb)));
			}
			
			var output = new DtoWrapperSale();
			
			LOGGER.info(String.format("new data info %s", Encode.forJava(String.valueOf(saleData))));
			output.setProductsWithAlerts(getProductsAlertsAndUpdate(saleData.getSale().getProducts()));
			var saleInn = saleMapper.toInner(saleData.getSale());
			var saleSaved = business.addSale(saleInn);
			LOGGER.info(String.format("sale saved %s", String.valueOf(saleSaved)));
			var saleSavedOut = saleMapper.toOuter(saleSaved);
			output.setSale(saleSavedOut);
			
			// nuevo deudor flujo
			if (isNewDebtor) {
				LOGGER.info(String.format("New debor %s", Encode.forJava(String.valueOf(saleData.getDebtor()))));
				List<DtoSale> sales = new ArrayList<>();
				sales.add(saleSavedOut);
				saleData.getDebtor().setSales(sales);
				var debtorToSave = debtorMapper.toInner(saleData.getDebtor());
				var debtorSaved = debtorBusiness.addDebtor(debtorToSave);
				var out = debtorMapper.toOuter(debtorSaved);
				LOGGER.info(String.format("Debtor saved %s", String.valueOf(out)));
				if (partialPyment.compareTo(BigDecimal.ZERO) > 0) {
					cashboxAddingCash(partialPyment, time);
				}
				output.setDebtor(out);
				wrapperResponse.setData(output);
				return new ResponseEntity<>(wrapperResponse, HttpStatus.CREATED);
			}
			
			//actualizar deudor
			if (debtorInDb == null) {
				LOGGER.error(String.format("debtor not found %s", String.valueOf(debtorInDb)));
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
			// actualiza montos
			var newAmount = debtorInDb.getTotal().add(saleData.getDebtor().getTotal());
			LOGGER.info(String.format("new account from payment %s", newAmount));
			debtorInDb.setTotal(newAmount);
			
			// actualiza fecha de compra
			debtorInDb.setUpdate_date(saleData.getDebtor().getUpdate_date());
			// actualiza su lista compras
			debtorInDb.getSales().add(saleSaved);
			
			//flujo cuando se dejó un abono
			if (partialPyment.compareTo(BigDecimal.ZERO) > 0) {
				LOGGER.info("partial pyment flow and update partial pyment list");
				var itemPartialPyment = new DtoIntPartialPyment();
				itemPartialPyment.setDate(time);
				itemPartialPyment.setPartial_pyment(partialPyment);
				debtorInDb.getPartial_pyments().add(itemPartialPyment);
				LOGGER.info(String.format("partial payment from debtor %s", String.valueOf(debtorInDb.getPartial_pyments())));
				// abrir o actualizar una caja de dinero
				cashboxAddingCash(partialPyment, time);
			}
			
			var debtorUpdated = debtorBusiness.updateDebtor(debtorInDb.getId(), debtorInDb);
			LOGGER.info(String.format("debtor updated %s", String.valueOf(debtorUpdated)));
			var mappedDebtor = debtorMapper.toOuter(debtorUpdated);
			output.setDebtor(mappedDebtor);
			wrapperResponse.setData(output);
			return new ResponseEntity<>(wrapperResponse, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.info(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			wrapperResponse.setError(error);
			return new ResponseEntity<>(wrapperResponse, e.getExceptHttpStatus());
		}
	}
	
	private void cashboxAddingCash(BigDecimal cash, long time) throws BloSalesBusinessException {
		var openCashbox = cashboxBusiness.getCashboxOpen();
		LOGGER.info(String.format("caja abierta %s", String.valueOf(openCashbox)));
		if (openCashbox == null) {
			var cashbox = new DtoCashbox();
			cashbox.setDate(time);
			cashbox.setMoney(cash);
			cashbox.setStatus(StatusCashboxEnum.OPEN);
			var innerCashbox = cashboxMapper.toInner(cashbox);
			openCashbox = cashboxBusiness.saveCashbox(innerCashbox);
		} else {
			var newCash = openCashbox.getMoney().add(cash);
			openCashbox.setMoney(newCash);
			cashboxBusiness.updateCashbox(openCashbox.getId(), openCashbox);
		}
		LOGGER.info(String.format("nformacion de cashbox %s", openCashbox));
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
				var productOut = productMapper.toOuter(productFound);
				productOut.setQuantity(newQuantity);
				productsWithAlert.add(productOut);
			}
			
			productFound.setQuantity(newQuantity);
			LOGGER.info(String.format("product data %s", String.valueOf(productFound)));
			productsBusiness.updateProduct(productFound.getId(), productFound);
		}
		
		return productsWithAlert;
	}

}
