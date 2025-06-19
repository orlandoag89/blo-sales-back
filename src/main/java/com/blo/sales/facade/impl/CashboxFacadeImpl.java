package com.blo.sales.facade.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.ICashboxFacade;
import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.facade.dto.DtoCashboxes;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.dto.commons.DtoError;
import com.blo.sales.facade.enums.StatusCashboxEnum;
import com.blo.sales.facade.mapper.DtoCashboxMapper;
import com.blo.sales.facade.mapper.DtoCashboxesMapper;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:4300", "http://localhost:4400"})
public class CashboxFacadeImpl implements ICashboxFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(CashboxFacadeImpl.class);
	
	@Autowired
	private DtoCashboxMapper cashboxMapper;
	
	@Autowired
	private DtoCashboxesMapper cashboxesMapper;
	
	@Autowired
	private ICashboxBusiness business;
	
	@Value("${exceptions.codes.not-cashbox}")
	private String exceptionsCodesNotCashbox;
	
	@Value("${exceptions.messages.not-cashbox}")
	private String exceptionsMessagesNotCashbox;
	
	@Override
	public ResponseEntity<DtoCommonWrapper<DtoCashbox>> closeCashbox() {
		LOGGER.info("closing cashbox");
		var output = new DtoCommonWrapper<DtoCashbox>();
		try {
			var busCashbox = business.getCashboxOpen();
			LOGGER.info(String.format("open cashbox %s", String.valueOf(busCashbox)));
			if (busCashbox == null) {
				LOGGER.error("no hay una caja abierta");
				throw new BloSalesBusinessException(exceptionsMessagesNotCashbox, exceptionsCodesNotCashbox, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			var openCashbox = cashboxMapper.toOuter(busCashbox);
			openCashbox.setStatus(StatusCashboxEnum.CLOSE);
			var innerCashbox = cashboxMapper.toInner(openCashbox);
			var saved = business.updateCashbox(openCashbox.getId(), innerCashbox);
			var out = cashboxMapper.toOuter(saved);
			LOGGER.info(String.format("save format %s", String.valueOf(out)));
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
	public ResponseEntity<DtoCommonWrapper<DtoCashboxes>> getAllCashboxes() {
		var cashboxes = business.getAllCashboxes();
		var out = cashboxesMapper.toOuter(cashboxes);
		LOGGER.info(String.format("cashboxes %s", String.valueOf(out)));
		var output = new DtoCommonWrapper<DtoCashboxes>();
		output.setData(out);
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoCashbox>> getOpenCashbox() {
		var data = new DtoCommonWrapper<DtoCashbox>();
		var openCashbox = business.getCashboxOpen();
		LOGGER.info(String.format("caja abierta %s", String.valueOf(openCashbox)));
		var out = cashboxMapper.toOuter(openCashbox);
		data.setData(out);
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
