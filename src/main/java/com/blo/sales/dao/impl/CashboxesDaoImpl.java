package com.blo.sales.dao.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.dao.ICashboxesDao;
import com.blo.sales.dao.docs.Cashboxes;
import com.blo.sales.dao.enums.DocStatusCashboxEnum;
import com.blo.sales.dao.mapper.CashboxMapper;
import com.blo.sales.dao.mapper.CashboxesMapper;
import com.blo.sales.dao.repository.CashboxesRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class CashboxesDaoImpl implements ICashboxesDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CashboxesDaoImpl.class);
	
	@Autowired
	private CashboxesRepository repository;
	
	@Autowired
	private CashboxMapper cashboxMapper;
	
	@Autowired
	private CashboxesMapper cashboxesMapper;
	
	@Value("${exceptions.codes.not-found}")
	private String notFoundCode;
	
	@Value("${exceptions.messages.not-found}")
	private String notFoundMessage;

	@Override
	public DtoIntCashbox addCashbox(DtoIntCashbox cashbox) {
		var toSave = cashboxMapper.toInner(cashbox);
		LOGGER.info(String.format("new cashbox %s", Encode.forJava(String.valueOf(cashbox))));
		var saved = repository.save(toSave);
		var out = cashboxMapper.toOuter(saved);
		LOGGER.info(String.format("new cashbox saved %s", String.valueOf(out)));
		return out;
	}

	@Override
	public DtoIntCashboxes getAllCashboxes() {
		var cashboxes = new Cashboxes();
		var allCashboxes = repository.findAll();
		cashboxes.setBoxes(allCashboxes);
		var out = cashboxesMapper.toOuter(cashboxes);
		LOGGER.info(String.format("cashboxes found %s", String.valueOf(out)));
		return out;
	}

	@Override
	public DtoIntCashbox getCashboxOpen() {
		LOGGER.info("retrieving open cashbox");
		var openCashbox = repository.findCashboxByStatus(DocStatusCashboxEnum.OPEN.name());
		LOGGER.info(String.format("open cashbox %s", String.valueOf(openCashbox)));
		var cashbox = openCashbox.stream().findFirst().orElse(null);
		LOGGER.info(String.format("cashbox data %s", String.valueOf(cashbox)));
		var outCashbox = cashboxMapper.toOuter(cashbox);
		return outCashbox;
	}

	@Override
	public DtoIntCashboxes getCashboxesClose() {
		LOGGER.info("retrieving close cashboxes");
		var closeCashboxes = repository.findCashboxByStatus(DocStatusCashboxEnum.CLOSE.name());
		LOGGER.info(String.format("close cashboxes %s", String.valueOf(closeCashboxes)));
		var cashboxes = new Cashboxes();
		cashboxes.setBoxes(closeCashboxes);
		LOGGER.info(String.format("close cashbox data %s", String.valueOf(cashboxes)));
		var out = cashboxesMapper.toOuter(cashboxes);
		return out;
	}

	@Override
	public DtoIntCashbox updateCashbox(String id, DtoIntCashbox cashbox) throws BloSalesBusinessException {
		LOGGER.info(String.format("updating cashbox %s data %s", id, String.valueOf(cashbox)));
		var foundCashbox = repository.findById(id);
		if (!foundCashbox.isPresent()) {
			LOGGER.error("cashbox not found");
			throw new BloSalesBusinessException(notFoundCode, notFoundMessage, HttpStatus.NOT_FOUND);
		}
		var cashboxData = foundCashbox.get();
		cashboxData.setMoney(cashbox.getMoney());
		cashboxData.setStatus(DocStatusCashboxEnum.valueOf(cashbox.getStatus().name()));
		cashboxData.setDate(cashbox.getDate());
		var saved = repository.save(cashboxData);
		var out = cashboxMapper.toOuter(saved);
		LOGGER.info(String.format("cashbox updated %s", String.valueOf(out)));
		return out;
		
	}

}
