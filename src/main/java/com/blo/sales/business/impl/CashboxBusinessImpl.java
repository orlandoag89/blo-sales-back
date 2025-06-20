package com.blo.sales.business.impl;

import java.math.BigDecimal;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	/** se agrega una nueva caja */
	@Override
	public DtoIntCashbox saveCashbox(DtoIntCashbox cashbox) {
		LOGGER.info(String.format("cashbox data %s", Encode.forJava(String.valueOf(cashbox))));
		cashbox.setStatus(StatusCashboxIntEnum.valueOf(StatusCashboxIntEnum.OPEN.name()));
		return dao.addCashbox(cashbox);
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

	@Override
	public void addingCash(BigDecimal cash, long time) throws BloSalesBusinessException {
		LOGGER.info(String.format("creando una caja con $%s en fecha %s", cash, time));
		var openCashbox = getCashboxOpen();
		LOGGER.info(String.format("caja abierta %s", String.valueOf(openCashbox)));
		if (openCashbox == null) {
			LOGGER.info("no hay caja ahora");
			var cashbox = new DtoIntCashbox();
			cashbox.setDate(time);
			cashbox.setMoney(cash);
			cashbox.setStatus(StatusCashboxIntEnum.OPEN);
			openCashbox = saveCashbox(cashbox);
			LOGGER.info(String.format("Cashbox creada %s", String.valueOf(openCashbox)));
		} else {
			var newAmount = openCashbox.getMoney().add(cash);
			openCashbox.setMoney(newAmount);
			LOGGER.info(String.format("cashbox nuevos datos %s", String.valueOf(openCashbox)));
			updateCashbox(openCashbox.getId(), openCashbox);
		}
	}

}
