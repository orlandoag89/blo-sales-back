package com.blo.sales.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntCredit;
import com.blo.sales.business.dto.DtoIntCredits;
import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.business.enums.CommonStatusIntEnum;
import com.blo.sales.dao.ICreditsDao;
import com.blo.sales.dao.docs.Credit;
import com.blo.sales.dao.docs.Credits;
import com.blo.sales.dao.enums.DocCommonStatusEnum;
import com.blo.sales.dao.mapper.CreditMaper;
import com.blo.sales.dao.mapper.CreditsMapper;
import com.blo.sales.dao.repository.CreditsRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class CreditsDaoImpl implements ICreditsDao {

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditsDaoImpl.class);
	
	@Autowired
	private CreditsRepository repository;
	
	@Autowired
	private CreditMaper creditMapper;
	
	@Autowired
	private CreditsMapper creditsMapper;
	
	@Value("${exceptions.messages.credit-close}")
	private String exceptionsMessagesCreditClose;
	
	@Value("${exceptions.messages.credit-not-found}")
	private String exceptionsMessagesCreditNotFound;

	@Value("${exceptions.codes.credit-close}")
	private String exceptionsCodesCreditClose;
	
	@Value("${exceptions.codes.credit-not-found}")
	private String exceptionsCodesCreditNotFound;
	
	@Override
	public DtoIntCredits getAllCredits() {
		List<Credit> allCredits = repository.findAll();
		var credits = new Credits();
		credits.setCredits(allCredits);
		LOGGER.info(String.format("recuperando todos los creditos disponibles %s", String.valueOf(credits)));
		return creditsMapper.toOuter(credits);
	}

	@Override
	public DtoIntCredit registerNewCredit(DtoIntCredit credit) throws BloSalesBusinessException {
		var creditMapped = creditMapper.toInner(credit);
		creditMapped.setStatus_credit(DocCommonStatusEnum.OPEN);
		creditMapped.setPartial_payment(new ArrayList<>());
		creditMapped.setLast_update_date(0);
		creditMapped.setCurrent_amount(credit.getTotal_amount());
		LOGGER.info(String.format("guardando credito %s", Encode.forJava(String.valueOf(credit))));
		var saved = repository.save(creditMapped);
		LOGGER.info(String.format("guardado %s", String.valueOf(saved)));
		return creditMapper.toOuter(saved);
	}

	@Override
	public DtoIntCredit addPayment(String idCredit, DtoIntPartialPyment partialPayment)
			throws BloSalesBusinessException {
		LOGGER.info(String.format("buscando credito por id %s", Encode.forJava(idCredit)));
		var creditFound = getCreditById(idCredit);
		LOGGER.info(String.format("credito encontrado %s", String.valueOf(creditFound)));
		if (creditFound.getStatus_credit().compareTo(CommonStatusIntEnum.CLOSE) == 0) {
			LOGGER.error("Este credito fue cerrado");
			throw new BloSalesBusinessException(exceptionsMessagesCreditClose, exceptionsCodesCreditClose, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		var restCredit = creditFound.getCurrent_amount().subtract(partialPayment.getPartial_pyment());
		LOGGER.info(String.format("credito restante [%s] de un total de %s", String.valueOf(restCredit), String.valueOf(creditFound.getTotal_amount())));
		tryPayCredit(creditFound, restCredit);
		creditFound.setCurrent_amount(restCredit);
		creditFound.getPartial_payment().add(0, partialPayment);
		var toSave = creditMapper.toInner(creditFound);
		var creditSaved = repository.save(toSave);
		LOGGER.info(String.format("credito actualizado %s", String.valueOf(creditSaved)));
		return creditMapper.toOuter(creditSaved);
	}

	@Override
	public DtoIntCredit updateCreditBasicData(String idCredit, DtoIntCredit credit) throws BloSalesBusinessException {
		LOGGER.info(String.format("Actualizando datos [%s] %s", Encode.forJava(idCredit), Encode.forJava(String.valueOf(credit))));
		var creditFound = getCreditById(idCredit);
		creditFound.setLast_update_date(credit.getLast_update_date());
		creditFound.setLender_name(credit.getLender_name());
		var toSave = creditMapper.toInner(creditFound);
		var saved = repository.save(toSave);
		LOGGER.info(String.format("credito actualizado %s", String.valueOf(saved)));
		return creditMapper.toOuter(saved);
	}

	@Override
	public DtoIntCredit updateAmount(String idCredit, BigDecimal amount) throws BloSalesBusinessException {
		LOGGER.info(String.format("buscando credito por id %s", Encode.forJava(idCredit)));
		var creditFound = getCreditById(idCredit);
		LOGGER.info(String.format("credito encontrado %s", String.valueOf(creditFound)));
		if (creditFound.getStatus_credit().compareTo(CommonStatusIntEnum.CLOSE) == 0) {
			LOGGER.error("Este credito fue cerrado");
			throw new BloSalesBusinessException(exceptionsMessagesCreditClose, exceptionsCodesCreditClose, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		if (creditFound.getCurrent_amount().compareTo(amount) < 0) {
			LOGGER.info("la deuda actual es menor al monto a actualizar");
			creditFound.setStatus_credit(CommonStatusIntEnum.CLOSE);
			var toSave = creditMapper.toInner(creditFound);
			var creditSaved = repository.save(toSave);
			LOGGER.info("credito actualizado");
			return creditMapper.toOuter(creditSaved);
		}
		creditFound.setTotal_amount(amount);
		// suma para actualizar el monto total
		var newCurrentAmount = creditFound.getCurrent_amount().subtract(amount);
		LOGGER.info(String.format("amount %s - current_amount %s = %s", String.valueOf(amount), String.valueOf(creditFound.getCurrent_amount()), String.valueOf(newCurrentAmount)));
		tryPayCredit(creditFound, newCurrentAmount);
		var toSave = creditMapper.toInner(creditFound);
		var creditSaved = repository.save(toSave);
		LOGGER.info(String.format("monto de credito actualizado %s", String.valueOf(creditSaved)));
		return creditMapper.toOuter(creditSaved);
	}

	private void tryPayCredit(DtoIntCredit credit, BigDecimal amout) {
		if (amout.compareTo(BigDecimal.ZERO) <= 0) {
			LOGGER.info("credito pagado completamente");
			credit.setStatus_credit(CommonStatusIntEnum.CLOSE);
		}
	}
	
	private DtoIntCredit getCreditById(String idCredit) throws BloSalesBusinessException {
		LOGGER.info(String.format("buscando %s", Encode.forJava(idCredit)));
		var creditFound = repository.findById(idCredit);
		if (!creditFound.isPresent()) {
			LOGGER.error("credito no encontrado");
			throw new BloSalesBusinessException(exceptionsMessagesCreditNotFound, exceptionsCodesCreditNotFound, HttpStatus.NOT_FOUND); 
		}
		LOGGER.info(String.format("credito encontrado %s", String.valueOf(creditFound)));
		return creditMapper.toOuter(creditFound.get());
	}
}
