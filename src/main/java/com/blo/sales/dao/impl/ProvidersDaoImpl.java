package com.blo.sales.dao.impl;

import java.util.List;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntProvider;
import com.blo.sales.business.dto.DtoIntProviders;
import com.blo.sales.dao.IProvidersDao;
import com.blo.sales.dao.docs.Provider;
import com.blo.sales.dao.docs.Providers;
import com.blo.sales.dao.mapper.ProviderMapper;
import com.blo.sales.dao.mapper.ProvidersMapper;
import com.blo.sales.dao.repository.ProvidersRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.utils.Utils;

@Service
public class ProvidersDaoImpl implements IProvidersDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProvidersDaoImpl.class);
	
	@Autowired
	private ProviderMapper providerMapper;
	
	@Autowired
	private ProvidersMapper providersMapper;
	
	@Autowired
	private ProvidersRepository repository;

	@Value("${exceptions.codes.not-provider-found}")
	private String exceptionsCodesNotProviderFound;
	
	@Value("${exceptions.messages.not-provider-found}")
	private String exceptionsMessagesNotProviderFound;
	
	@Value("${exceptions.codes.days-not-valid}")
	private String exceptionsCodesDaysNotValud;
	
	@Value("${exceptionsMessagesDaysNotValid}")
	private String exceptionsMessagesDaysNotValid;
	
	@Override
	public DtoIntProvider registerNewProvider(DtoIntProvider provider) {
		LOGGER.info(String.format("guardando proveedor %s", Encode.forJava(String.valueOf(provider))));
		var inner = providerMapper.toInner(provider);
		var providerSaved = repository.save(inner);
		LOGGER.info(String.format("proveedor guardado %s", String.valueOf(providerSaved)));
		return providerMapper.toOuter(providerSaved);
	}

	@Override
	public DtoIntProviders getAllProviders() {
		List<Provider> providers = repository.findAll();
		LOGGER.info(String.format("proveedores registrados %s", String.valueOf(providers)));
		var providersTmp = new Providers();
		providersTmp.setProviders(providers);
		return providersMapper.toOuter(providersTmp);
	}

	@Override
	public DtoIntProvider getProviderById(String id) throws BloSalesBusinessException {
		var providerFound = getProbiderById(id);
		return providerMapper.toOuter(providerFound);
	}

	@Override
	public DtoIntProvider updateProvider(String id, DtoIntProvider provider) throws BloSalesBusinessException  {
		
		LOGGER.info(String.format("Actualizando datos de proveedor con id", id));
		var providerFound = getProbiderById(id);
		LOGGER.info(String.format("Proveedor encontrado [%s] nuevos datos [%s]", providerFound, Encode.forJava(String.valueOf(provider))));
		providerFound.setName(provider.getName());
		providerFound.setPhoneNumber(provider.getPhoneNumber());
		
		LOGGER.info(String.format("actualizando productos que ofrece %s", Encode.forJava(String.valueOf(provider.getProducts_offered()))));
		providerFound.setProducts_offered(Utils.mergeStringList(providerFound.getProducts_offered(), provider.getProducts_offered()));
		LOGGER.info(String.format("productos actualizados %s", String.valueOf(providerFound.getProducts_offered())));
		
		LOGGER.info(String.format("actualizando dias de visita %s", Encode.forJava(String.valueOf(provider.getDays_visit()))));
		providerFound.setDays_visit(Utils.mergeStringList(providerFound.getDays_visit(), provider.getDays_visit()));
		LOGGER.info(String.format("dias de visita actualizados %s", String.valueOf(providerFound.getDays_visit())));
		
		var providerSaved = repository.save(providerFound);
		LOGGER.info(String.format("proveedor actualizado %s", String.valueOf(providerFound)));
		return providerMapper.toOuter(providerSaved);
	}

	@Override
	public void deleteProvider(String id) throws BloSalesBusinessException {
		getProbiderById(id);
		repository.deleteById(id);
		LOGGER.info("proveedor eliminado");
	}
	
	private Provider getProbiderById(String id) throws BloSalesBusinessException {
		LOGGER.info("buscando proveedor por id");
		var providerFound = repository.findById(id);
		LOGGER.info(String.format("proveedor encontrado %s", String.valueOf(providerFound)));
		if (!providerFound.isPresent()) {
			LOGGER.error("Proveedor no encontrado");
			throw new BloSalesBusinessException(exceptionsMessagesNotProviderFound, exceptionsCodesNotProviderFound, HttpStatus.NOT_FOUND);
		}
		return providerFound.get();
		
	}
	
	

}
