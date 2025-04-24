package com.blo.sales.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import com.blo.sales.dao.enums.DocStatusCashboxEnum;
import com.blo.sales.dao.mapper.CashboxMapper;
import com.blo.sales.dao.mapper.CashboxesMapper;
import com.blo.sales.dao.repository.CashboxesRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = { 
	"exceptions.codes.not-found=ERR01",
	"exceptions.messages.not-found=No encontrado"
})
public class CashboxesDaoImplTest {

	@Mock
	private CashboxesRepository repository;

	@Mock
	private CashboxMapper cashboxMapper;

	@Mock
	private CashboxesMapper cashboxesMapper;

	@InjectMocks
	private CashboxesDaoImpl impl;

	@Test
	public void addCashboxTest() {
		Mockito.when(cashboxMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createOpenCashbox());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createOpenCashbox());
		Mockito.when(cashboxMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxOpen());
		
		var out = impl.addCashbox(MocksFactory.createDtoIntNewCashbox());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		
		assertNotNull(out);
		assertNotNull(out.getId());
	}
	
	@Test
	public void getAllCashboxesTest() {
		Mockito.when(repository.findAll()).thenReturn(MocksFactory.createCashboxesList());
		Mockito.when(cashboxesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxes());
		
		var out = impl.getAllCashboxes();
		
		assertNotNull(out);
		assertFalse(out.getBoxes().isEmpty());
	}
	
	@Test
	public void getCashboxOpenTest() {
		Mockito.when(repository.findCashboxByStatus(Mockito.anyString())).thenReturn(MocksFactory.createCashboxes());
		Mockito.when(cashboxMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxOpen());
		
		var out = impl.getCashboxOpen();
		
		assertNotNull(out);
		assertEquals(DocStatusCashboxEnum.OPEN.name(), out.getStatus().name());
	}
	
	@Test
	public void getCashboxCloseTest() {
		Mockito.when(repository.findCashboxByStatus(Mockito.anyString())).thenReturn(MocksFactory.createCashboxes());
		Mockito.when(cashboxesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxes());
		
		var out = impl.getCashboxesClose();
		
		assertNotNull(out);
		assertFalse(out.getBoxes().isEmpty());
	}
	
	@Test
	public void updateCashboxTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalCashbox());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createOpenCashbox());
		Mockito.when(cashboxMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxClose());
		
		var out = impl.updateCashbox(MocksFactory.getId(), MocksFactory.createDtoIntCashboxClose());
		
		assertNotNull(out);
		assertEquals(DocStatusCashboxEnum.CLOSE.name(), out.getStatus().name());
	}
	
	@Test
	public void updateCashboxNotPresentTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(BloSalesBusinessException.class, () -> impl.updateCashbox(MocksFactory.getId(), MocksFactory.createDtoIntCashboxClose()));
	}
}
