package com.blo.sales.business.impl;

import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.dao.ICashboxesDao;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class CashboxBusinessImpl implements ICashboxBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CashboxBusinessImpl.class);
	
	@Autowired
	private ICashboxesDao dao;
		
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${exceptions.messages.cashbox-not-exists}")
	private String cashboxDoesNotExistsCode;
	
	@Value("${exceptions.codes.cashbox-not-exists}")
	private String cashboxDoesNotExistsMessage;

	/** se agrega una nueva caja */
	@Override
	public DtoIntCashbox saveCashbox(DtoIntCashbox cashbox) {
		LOGGER.info(String.format("cashbox data %s", Encode.forJava(String.valueOf(cashbox))));
		cashbox.setStatus(StatusCashboxIntEnum.valueOf(StatusCashboxIntEnum.OPEN.name()));
		var saved = dao.addCashbox(cashbox);
		var out = modelMapper.map(saved, DtoIntCashbox.class);
		LOGGER.info(String.format("cashbox saved %s", Encode.forJava(String.valueOf(out))));
		return out;
	}

	@Override
	public DtoIntCashbox getCashboxOpen() {
		LOGGER.info("retrieving cashbox open");
		return dao.getCashboxOpen();
	}

	@Override
	public DtoIntCashbox updateCashbox(String id, DtoIntCashbox cashbox) throws BloSalesBusinessException {
		LOGGER.info(String.format("updating cashbox %s", String.valueOf(cashbox)));
		return dao.updateCashbox(id, cashbox);
	}

	@Override
	public DtoIntCashboxes getAllCashboxes() {
		LOGGER.info("getting all cashboxes");
		return dao.getAllCashboxes();
	}

}
