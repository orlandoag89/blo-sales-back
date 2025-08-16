package com.blo.sales.business.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.business.enums.CommonStatusIntEnum;
import com.blo.sales.dao.ICreditsDao;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class CreditBusinessImplTest {

	@InjectMocks
	private CreditBusinessImpl business;
	
	@Mock
	private ICreditsDao dao;
	
	@Test
	public void getAllCreditsTest() {
		Mockito.when(dao.getAllCredits()).thenReturn(MocksFactory.createDtoIntCredits());
		
		var out = business.getAllCredits();
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getAllCredits();
		
		assertFalse(out.getCredits().isEmpty());
	}
	
	@Test
	public void getCreditsByStatusTest() {
		Mockito.when(dao.getCreditsByStatus(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCredits());
		
		var out = business.getCreditsByStatus(MocksFactory.getOpenStatus());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).getCreditsByStatus(Mockito.anyString());
		
		assertFalse(out.getCredits().isEmpty());
	}
	
	@Test
	public void registerNewCreditTest() throws BloSalesBusinessException {
		Mockito.when(dao.registerNewCredit(Mockito.any())).thenReturn(MocksFactory.createDtoIntCreditSaved());
		
		var out = business.registerNewCredit(MocksFactory.createNewDtoIntCredit());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).registerNewCredit(Mockito.any());
		
		assertNotNull(out.getId());
	}
	
	@Test
	public void addPaymentTest() throws BloSalesBusinessException {
		var creditAdded = MocksFactory.createDtoIntCreditSaved();
		creditAdded.getPartial_payment().add(MocksFactory.createDtoIntPartialPyment());
		
		Mockito.when(dao.addPayment(Mockito.anyString(), Mockito.any())).thenReturn(creditAdded);
		
		var out = business.addPayment(MocksFactory.getId(), MocksFactory.createDtoIntPartialPyment());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).addPayment(Mockito.anyString(), Mockito.any());
		
		assertFalse(out.getPartial_payment().isEmpty());
	}
	
	@Test
	public void updateCreditBasicDataTest() throws BloSalesBusinessException {
		var creditEdited = MocksFactory.createDtoIntCreditSaved();
		creditEdited.setLender_name("ANY_NAME");
		Mockito.when(dao.updateCreditBasicData(Mockito.anyString(), Mockito.any())).thenReturn(creditEdited);
		
		var out = business.updateCreditBasicData(MocksFactory.getId(), MocksFactory.createDtoIntCreditSaved());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).updateCreditBasicData(Mockito.anyString(), Mockito.any());
		
		assertEquals("ANY_NAME", out.getLender_name());
	}
	
	@Test
	public void updateAmountTest() throws BloSalesBusinessException {
		Mockito.when(dao.updateAmount(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoInCreditSavedClose());
		
		var out = business.updateAmount(MocksFactory.getId(), MocksFactory.createBigDecimal50());
		
		Mockito.verify(dao, Mockito.atLeastOnce()).updateAmount(Mockito.anyString(), Mockito.any());
		
		assertEquals(CommonStatusIntEnum.CLOSE, out.getStatus_credit());
	}
}
