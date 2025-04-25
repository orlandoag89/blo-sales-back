package com.blo.sales.dao.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.dao.mapper.DebtorMapper;
import com.blo.sales.dao.mapper.DebtorsMapper;
import com.blo.sales.dao.repository.DebtorsRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class DebtorsDaoImplTest {
	
	@Mock
	private DebtorsRepository repository;
	
	@Mock
	private DebtorMapper mapper;
	
	@Mock
	private DebtorsMapper debtorsMapper;

	@InjectMocks
	private DebtorsDaoImpl impl;
	
	@Test
	public void addDebtorTest() throws BloSalesBusinessException {
		Mockito.when(mapper.toInner(Mockito.any())).thenReturn(MocksFactory.createNewDebtor());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createExistsDebtor());
		Mockito.when(mapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = impl.addDebtor(MocksFactory.createNewDtoIntDebtor());
		
		Mockito.verify(mapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(mapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getId());
	}
	
	@Test
	public void getDebtorsTest() throws BloSalesBusinessException {
		Mockito.when(repository.findAll()).thenReturn(MocksFactory.createDebtors().getDebtors());
		Mockito.when(debtorsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntDebtors());
		
		var out = impl.getDebtors();
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findAll();
		Mockito.verify(debtorsMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertFalse(out.getDebtors().isEmpty());
	}
	
	@Test
	public void getDebtorByIdTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionaDebtor());
		Mockito.when(mapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = impl.getDebtorById(MocksFactory.getId());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(mapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getId());
	}
	
	@Test
	public void getDebtorNullByIdTest() {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(BloSalesBusinessException.class, () -> impl.getDebtorById(MocksFactory.getId()));
	}
	
	@Test
	public void deleteDebtorByIdTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionaDebtor());
		Mockito.when(mapper.toInner(Mockito.any())).thenReturn(MocksFactory.createExistsDebtor());
		
		impl.deleteDebtorById(MocksFactory.getId());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(mapper, Mockito.atLeastOnce()).toInner(Mockito.any());		
	}
	
	@Test
	public void updateDebtorTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionaDebtor());
		Mockito.when(mapper.toInner(Mockito.any())).thenReturn(MocksFactory.createExistsDebtor());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createExistsDebtor());
		Mockito.when(mapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = impl.updateDebtor(MocksFactory.getId(), MocksFactory.createExistsDtoIntDebtor());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(mapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(mapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getId());	
	}
	
	@Test
	public void getDebtorOrNullTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionaDebtor());
		Mockito.when(mapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		
		var out = impl.getDebtorOrNull(MocksFactory.getId());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		
		assertNotNull(out);
	}
	
	@Test
	public void getDebtorNullTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		var out = impl.getDebtorOrNull(MocksFactory.getId());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		
		assertNull(out);
		
	}
}
