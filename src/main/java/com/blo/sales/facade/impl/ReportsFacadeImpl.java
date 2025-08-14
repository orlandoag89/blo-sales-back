package com.blo.sales.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntProductsOnSalesCounter;
import com.blo.sales.business.dto.DtoIntSalesDetailReport;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IReportsFacade;
import com.blo.sales.facade.dto.DtoProductsOnSalesCounter;
import com.blo.sales.facade.dto.DtoSalesDetailReport;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.dto.commons.DtoError;
import com.blo.sales.facade.enums.ReportTypeEnum;
import com.blo.sales.facade.mapper.DtoProductsOnSalesCounterMapper;
import com.blo.sales.facade.mapper.DtoSalesDetailReportMapper;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300", "http://localhost:4400"})
public class ReportsFacadeImpl implements IReportsFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReportsFacadeImpl.class);
	
	@Autowired
	private ISalesBusiness salesBusiness;
	
	@Autowired
	private DtoProductsOnSalesCounterMapper productsOnSalesCounterMapper;
	
	@Autowired
	private DtoSalesDetailReportMapper salesDetailReportMapper;
	
	@Value("${exceptions.codes.report-period-year-not-valid}")
	private String exceptionsCodesReportPeriodYearNotValid;
	
	@Value("${exceptions.codes.report-period-month-not-valid}")
	private String exceptionsCodesReportPeriodMonthNotValid;
	
	@Value("${exceptions.codes.report-period-year-init-end-not-valid}")
	private String exceptionsCodesReportPeriodYearInitEndNotValid;
	
	@Value("${exceptions.messages.report-period-year-not-valid}")
	private String exceptionsMessgesReportPeriodYearNotValid;
	
	@Value("${exceptions.messages.report-period-month-not-valid}")
	private String exceptionsMessagesReportPeriodMonthNotValid;
	
	@Value("${exceptions.messages.report-period-year-init-end-not-valid}")
	private String exceptionsMessagesReportPeriodYearInitEndNotValid;
	
	@Override
	public ResponseEntity<DtoCommonWrapper<DtoProductsOnSalesCounter>> retrieveProductsOnSalesReport(
		ReportTypeEnum reportType,
		int initialMonth,
		int initYear,
		int endMonth,
		int endYear
	) {
		LOGGER.info(String.format("recuperando todos los productos vendidos por cada venta por [%s] init %s de %s- end %s de %s", String.valueOf(reportType), initialMonth, initYear, endMonth, endYear));
		var body = new DtoCommonWrapper<DtoProductsOnSalesCounter>();
		try {
			var salesProducts = new DtoIntProductsOnSalesCounter();
			if (reportType.compareTo(ReportTypeEnum.ALL) == 0){
				salesProducts = salesBusiness.getBestSellingProducts(0, 0, 0, 0);
				LOGGER.info("todos los productos");
			}
			if (reportType.compareTo(ReportTypeEnum.BY_PERIOD) == 0) {
				LOGGER.info("Validando periodo");
				validatorDatesPeriod(initialMonth, initYear, endMonth, endYear);
				salesProducts = salesBusiness.getBestSellingProducts(initialMonth, initYear, endMonth, endYear);
				LOGGER.info("productos por periodo");
			}
			var data = productsOnSalesCounterMapper.toOuter(salesProducts);
			LOGGER.info(String.format("productos en ventas %s", String.valueOf(data.getProductsOnSales().size())));
			body.setData(data);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			body.setError(error);
			return new ResponseEntity<>(body, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoSalesDetailReport>> retrieveSalesByDate(ReportTypeEnum reportType, int initialMonth,
			int initYear, int endMonth, int endYear) {
		var body = new DtoCommonWrapper<DtoSalesDetailReport>();
		try {
			var salesDetail = new DtoIntSalesDetailReport();
			if (reportType.compareTo(ReportTypeEnum.ALL) == 0) {
				salesDetail = salesBusiness.getSalesByDate(0, 0, 0, 0);
				LOGGER.info("todas las ventas");
			}
			if (reportType.compareTo(ReportTypeEnum.BY_PERIOD) == 0) {
				validatorDatesPeriod(initialMonth, initYear, endMonth, endYear);
				salesDetail = salesBusiness.getSalesByDate(initialMonth, initYear, endMonth, endYear);
				LOGGER.info(String.format("Periodo con %s ventas", String.valueOf(salesDetail.getSales().size())));
			}
			var data = salesDetailReportMapper.toOuter(salesDetail);
			body.setData(data);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			body.setError(error);
			return new ResponseEntity<>(body, e.getExceptHttpStatus());
		}
	}
	
	private void validatorDatesPeriod(int initialMonth, int initYear, int endMonth, int endYear) throws BloSalesBusinessException {
		if (initYear < 2025 || endYear < 2025) {
			LOGGER.error("Fecha inicio no puede ser menor a 2025");
			throw new BloSalesBusinessException(exceptionsMessgesReportPeriodYearNotValid, exceptionsCodesReportPeriodYearNotValid, HttpStatus.BAD_REQUEST);
		}
		if (
			initialMonth < 1 ||
			initialMonth > 12 ||
			endMonth < 1 ||
			endMonth > 12
		) {
			LOGGER.error("Meses menores a 0 y mayores a 12");
			throw new BloSalesBusinessException(exceptionsMessagesReportPeriodMonthNotValid, exceptionsCodesReportPeriodMonthNotValid, HttpStatus.BAD_REQUEST);
		}
		if (initYear > endYear) {
			LOGGER.error("Anio inicial mayor a anio final");
			throw new BloSalesBusinessException(exceptionsMessagesReportPeriodYearInitEndNotValid, exceptionsCodesReportPeriodYearInitEndNotValid, HttpStatus.BAD_REQUEST);
		}
		
	}

}
