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

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.dao.ICashboxesDao;
import com.blo.sales.dao.docs.Cashbox;
import com.blo.sales.dao.enums.DocStatusCashboxEnum;
import com.blo.sales.dao.repository.CashboxesRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class CashboxesDaoImpl implements ICashboxesDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CashboxesDaoImpl.class);
	
	@Autowired
	private CashboxesRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${exceptions.codes.not-found}")
	private String notFoundCode;
	
	@Value("${exceptions.messages.not-found}")
	private String notFoundMessage;

	@Override
	public DtoIntCashbox addCashbox(DtoIntCashbox cashbox) {
		var toSave = modelMapper.map(cashbox, Cashbox.class);
		LOGGER.info(String.format("new cashbox %s", Encode.forJava(String.valueOf(cashbox))));
		var saved = repository.save(toSave);
		var out = modelMapper.map(saved, DtoIntCashbox.class);
		LOGGER.info(String.format("new cashbox saved %s", String.valueOf(out)));
		return out;
	}

	@Override
	public DtoIntCashboxes getAllCashboxes() {
		DtoIntCashboxes cashboxes = new DtoIntCashboxes();
		List<DtoIntCashbox> boxes = new ArrayList<>();
		
		repository.findAll().forEach(c -> {
			LOGGER.info(String.format("parsing %s", String.valueOf(c)));
			var item = modelMapper.map(c, DtoIntCashbox.class);
			boxes.add(item);
		});
		
		LOGGER.info(String.format("boxes %s", String.valueOf(boxes)));
		cashboxes.setBoxes(boxes);
		return cashboxes;
	}

	@Override
	public DtoIntCashbox getCashboxOpen() {
		LOGGER.info("retrieving open cashbox");
		var openCashbox = repository.findCashboxByStatus(DocStatusCashboxEnum.OPEN.name());
		LOGGER.info(String.format("open cashboxes %s", String.valueOf(openCashbox)));
		var cashbox = openCashbox.getBoxes().stream().findFirst().orElse(null);
		LOGGER.info(String.format("cashbox data %s", String.valueOf(cashbox)));
		var outCashbox = modelMapper.map(cashbox, DtoIntCashbox.class);
		return outCashbox;
		
	}

	@Override
	public DtoIntCashboxes getCashboxesClose() {
		LOGGER.info("retrieving close cashboxes");
		var closeCashboxes = repository.findCashboxByStatus(DocStatusCashboxEnum.CLOSE.name());
		LOGGER.info(String.format("close cashboxes %s", String.valueOf(closeCashboxes)));
		var cashbox = closeCashboxes.getBoxes();
		LOGGER.info(String.format("close cashbox data %s", String.valueOf(cashbox)));
		
		var cashboxes = new DtoIntCashboxes();
		List<DtoIntCashbox> boxes = new ArrayList<>();
		
		cashbox.forEach(c -> {
			var item = modelMapper.map(c, DtoIntCashbox.class);
			LOGGER.info(String.format("cashbox %s", String.valueOf(item)));
			boxes.add(item);
		});
		
		cashboxes.setBoxes(boxes);
		LOGGER.info(String.format("close cashbox %s", String.valueOf(cashboxes)));
		return cashboxes;
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
		var out = modelMapper.map(saved, DtoIntCashbox.class);
		LOGGER.info(String.format("cashbox updated %s", String.valueOf(out)));
		return out;
		
	}

}
