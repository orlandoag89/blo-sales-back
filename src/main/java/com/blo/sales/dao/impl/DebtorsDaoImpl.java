package com.blo.sales.dao.impl;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntDebtors;
import com.blo.sales.dao.IDebtorsDao;
import com.blo.sales.dao.docs.Debtor;
import com.blo.sales.dao.repository.DebtorsRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;

@Service
public class DebtorsDaoImpl implements IDebtorsDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DebtorsDaoImpl.class);
	
	@Autowired
	private DebtorsRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${exceptions.codes.not-found}")
	private String notFoundCode;
	
	@Value("${exceptions.messages.not-found}")
	private String notFoundMessage;

	@Override
	public DtoIntDebtor addDebtor(DtoIntDebtor debtor) throws BloSalesBusinessException {
		var debtorDoc = modelMapper.map(debtor, Debtor.class);
		LOGGER.info(String.format("Debtor parsed: %s", String.valueOf(debtor)));
		
		var saved = repository.save(debtorDoc);
		LOGGER.info(String.format("Debtor saved: %s", String.valueOf(saved)));
		
		var out = modelMapper.map(saved, DtoIntDebtor.class);
		return out;
	}

	@Override
	public DtoIntDebtors getDebtors() throws BloSalesBusinessException {
		var debtors = repository.findAll();
		
		var out = new DtoIntDebtors();		
		var debtorsmapped = debtors.stream().map(d -> modelMapper.map(d, DtoIntDebtor.class)).collect(Collectors.toList());
		out.setDebtors(debtorsmapped);
		LOGGER.info(String.format("Debtors: %s", String.valueOf(out)));
		
		return out;
	}

	@Override
	public DtoIntDebtor getDebtorById(String id) throws BloSalesBusinessException {
		var debtorFound = repository.findById(id);
		if (!debtorFound.isPresent()) {
			LOGGER.error(String.format("id %s not found", id));
			throw new BloSalesBusinessException(notFoundMessage, notFoundCode, HttpStatus.NOT_FOUND);
		}
		var debtor = debtorFound.get();
		var out = modelMapper.map(debtor, DtoIntDebtor.class);
		LOGGER.info(String.format("debtor found %s", String.valueOf(out)));
		return out;
	}

	@Override
	public void deleteDebtorById(String id) throws BloSalesBusinessException {
		var debtor = getDebtorById(id);
		var toDel = modelMapper.map(debtor, Debtor.class);
		LOGGER.info(String.format("Debtor delete: %s", id));
		repository.delete(toDel);
	}

	@Override
	public DtoIntDebtor updateDebtor(String id, DtoIntDebtor debtor) throws BloSalesBusinessException {
		var debtorFound = getDebtorById(id);
		debtorFound.setName(debtor.getName());
		debtorFound.setPartial_pyments(debtor.getPartial_pyments());
		debtorFound.setSales(debtor.getSales());
		debtorFound.setTotal(debtor.getTotal());
		
		var parsedDebtor = modelMapper.map(debtorFound, Debtor.class);
		LOGGER.info(String.format("Debtor updated: %s", String.valueOf(parsedDebtor)));
		
		var saved = repository.save(parsedDebtor);
		LOGGER.info(String.format("Saved: %s", id));
		
		return modelMapper.map(saved, DtoIntDebtor.class);
	}

	@Override
	public DtoIntDebtor getDebtorOrNull(String id) throws BloSalesBusinessException {
		var debtorFound = repository.findById(id);
		if (!debtorFound.isPresent()) {
			LOGGER.error(String.format("id %s not found", id));
			return null;
		}
		var debtor = debtorFound.get();
		var out = modelMapper.map(debtor, DtoIntDebtor.class);
		LOGGER.info(String.format("debtor found %s", String.valueOf(out)));
		return out;
	}

}
