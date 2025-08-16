package com.blo.sales.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.business.enums.CommonStatusIntEnum;
import com.blo.sales.dao.mapper.CreditMaper;
import com.blo.sales.dao.mapper.CreditsMapper;
import com.blo.sales.dao.repository.CreditsRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class CreditsDaoImplTest {
	
	@InjectMocks
	private CreditsDaoImpl dao;
	
	@Mock
	private CreditsRepository repository;
	
	@Mock
	private CreditMaper creditMapper;
	
	@Mock
	private CreditsMapper creditsMapper;
	
	@Test
	public void getAllCreditsTest() {
		Mockito.when(repository.findAll()).thenReturn(MocksFactory.createCreditList());
		Mockito.when(creditsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCredits());
		
		var out = dao.getAllCredits();
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findAll();
		Mockito.verify(creditsMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertFalse(out.getCredits().isEmpty());
	}
	
	@Test
	public void getCreditsByStatusTest() {
		Mockito.when(repository.retrieveCreditsBy(Mockito.anyString())).thenReturn(MocksFactory.createCreditList());
		Mockito.when(creditsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCredits());
		
		var out = dao.getCreditsByStatus(MocksFactory.getOpenStatus());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).retrieveCreditsBy(Mockito.anyString());
		Mockito.verify(creditsMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertFalse(out.getCredits().isEmpty());
	}

	@Test
	public void registerNewCreditTest() throws BloSalesBusinessException {
		Mockito.when(creditMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createNewCredit());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCreditSaved());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCreditSaved());
		
		var out = dao.registerNewCredit(MocksFactory.createNewDtoIntCredit());
		
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getId());
		assertTrue(out.getPartial_payment().isEmpty());
		assertNotNull(out.getStatus_credit());
	}
	
	@Test
	public void addPaymentSuccesssTest() throws BloSalesBusinessException {
		var creditSaved = MocksFactory.createDtoIntCreditSaved();
		creditSaved.getPartial_payment().add(MocksFactory.createDtoIntPartialPyment());
		
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalCredit());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCreditSaved());
		Mockito.when(creditMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createCreditSaved());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCreditSaved());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(creditSaved);
		
		var out = dao.addPayment(MocksFactory.getAnyString(), MocksFactory.createDtoIntPartialPyment());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
		assertFalse(out.getPartial_payment().isEmpty());
	}
	
	@Test
	public void addPaymentCreditNotFoundTest() {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(BloSalesBusinessException.class, () -> dao.addPayment(Mockito.anyString(), MocksFactory.createDtoIntPartialPyment()));
	}
	
	@Test
	public void addPaymentCreditWasCloseTest() {
		var credit = MocksFactory.createDtoIntCreditSaved();
		credit.setStatus_credit(CommonStatusIntEnum.CLOSE);
		
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalCredit());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(credit);
		
		assertThrows(BloSalesBusinessException.class, () -> dao.addPayment(Mockito.anyString(), MocksFactory.createDtoIntPartialPyment()));
		
	}

	@Test
	public void updateCreditBasicDataTest() throws BloSalesBusinessException {		
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalCredit());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCreditSaved());
		Mockito.when(creditMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createCreditSaved());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCreditSaved());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCreditSaved());
		
		var out = dao.updateCreditBasicData(MocksFactory.getId(), MocksFactory.createDtoIntCreditSaved());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(creditMapper, Mockito.atLeast(2)).toOuter(Mockito.any());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		
		assertEquals("Pepe", out.getLender_name());
	}

	/**
	 * se intenta cerrar un credito cerrado
	 * @throws BloSalesBusinessException
	 */
	@Test
	public void updateAmountCloseCreditTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionaCloselCredit());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoInCreditSavedClose());
		
		assertThrows(BloSalesBusinessException.class, () -> dao.updateAmount(MocksFactory.getAnyString(), MocksFactory.createBigDecimal50()));
	}

	/**
	 * se actualiza el monto del credito cuando tiene 50 como cuenta actual y se aplica un cambio de 5
	 * @throws BloSalesBusinessException
	 */
	@Test
	public void updateAmountSuccessTest() throws BloSalesBusinessException {
		var creditAmountMap = MocksFactory.createDtoIntCreditSaved();
		creditAmountMap.setCurrent_amount(MocksFactory.createBigDecimal50());
		
		var creditAmount = MocksFactory.createDtoIntCreditSaved();
		creditAmount.setCurrent_amount(new BigDecimal("45"));
		
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalCreditCurrentAmountBy50());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(creditAmountMap);
		Mockito.when(creditMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createCreditSavedClose());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCreditSavedClose());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(creditAmount);
		
		var out = dao.updateAmount(MocksFactory.getAnyString(), MocksFactory.getBigDecimal5());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertEquals("45", out.getCurrent_amount().toString());
	}
	/**
	 * caso de prueba cuando se intenta actualizar el monto restante de la deuda 500 a solo 50. Se cierra el credito en automático
	 * @throws BloSalesBusinessException
	 */
	@Test
	public void updateAmountAmountMajTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalCredit());
		Mockito.when(creditMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCreditSaved());
		Mockito.when(creditMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createCreditSavedClose());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCreditSavedClose());
		
		var out = dao.updateAmount(MocksFactory.getId(), MocksFactory.createBigDecimal50());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(creditMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		
		assertEquals(CommonStatusIntEnum.CLOSE, out.getStatus_credit());
	}
		
}
