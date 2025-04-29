package com.blo.sales.facade.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.facade.mapper.DtoDebtorMapper;
import com.blo.sales.facade.mapper.DtoDebtorsMapper;
import com.blo.sales.facade.mapper.DtoPartialPymentMapper;
import com.blo.sales.factory.MocksFactory;
import com.blo.sales.factory.MocksUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class DebtorFacadeImplTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private IDebtorsBusiness business;
	
	@MockBean
	private ISalesBusiness sales;
	
	@MockBean
	private ICashboxBusiness cashboxes;
	
	@MockBean
	private DtoDebtorMapper debtorMapper;

	@MockBean
	private DtoDebtorsMapper debtorsMapper;
	
	@MockBean
	private DtoPartialPymentMapper partialPymentMapper;
	
	@Test
	public void retrieveDebtorByIdTest() throws Exception {
		Mockito.when(business.getDebtorById(Mockito.anyString())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		Mockito.when(debtorMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createExistsDtoDebtor());
		
		 var result = mockMvc.perform(get("/api/v1/debtors/1a2b3c")
	                .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
		 
		 Mockito.verify(business, Mockito.atLeastOnce()).getDebtorById(Mockito.anyString());
		 Mockito.verify(debtorMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		 
		 var resultAsString = MocksUtils.getContentAsString(result, "retrieveDebtorById");
		 var objResult = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoDebtor());
		 
		 assertNotNull(objResult);
		 assertNotNull(objResult.getData());
		 assertNotNull(objResult.getData().getId());
	}
	
	@Test
	public void retrieveAllDebtorsTest() throws Exception {
		Mockito.when(business.getDebtors()).thenReturn(MocksFactory.createDtoIntDebtors());
		Mockito.when(debtorsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoDebtors());
		
		var result = mockMvc.perform(get("/api/v1/debtors")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		Mockito.verify(business, Mockito.atLeastOnce()).getDebtors();
		Mockito.verify(debtorsMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		var resultAsString = MocksUtils.getContentAsString(result, "retrieveAllDebtorsTest");
		var objResult = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoDebtors());
		
		assertNotNull(objResult);
		assertNotNull(objResult.getData());
		assertFalse(objResult.getData().getDebtors().isEmpty());
	}
	
	/**
	 * se agrega un pago en donde da como resultado 0 y no existe una caja abierta
	 * @throws Exception
	 */
	@Test
	public void addAllPayNoCashboxTest() throws Exception {
		var existsDebtor = MocksFactory.createExistsDtoIntDebtor();
		existsDebtor.setTotal(MocksUtils.BIG_DECIMAL_00);
		
		Mockito.when(partialPymentMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntPartialPyment());
		Mockito.when(business.addPay(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(existsDebtor);
		Mockito.when(cashboxes.getCashboxOpen()).thenReturn(null);
		Mockito.when(cashboxes.saveCashbox(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxOpen());
		Mockito.when(sales.getSaleById(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		Mockito.when(sales.updateSale(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleOnCashbox());
		Mockito.doNothing().when(business).deleteDebtorById(Mockito.anyString());
		
		var result = mockMvc.perform(put("/api/v1/debtors/1a2b3c4d?time=" + MocksFactory.getNowDate())
                .contentType(MediaType.APPLICATION_JSON)
                .content(MocksUtils.parserToString(MocksFactory.createDtoPartialPyment())))
            .andExpect(status().isOk())
            .andReturn();
		
		Mockito.verify(partialPymentMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addPay(Mockito.anyString(), Mockito.any(), Mockito.anyLong());
		Mockito.verify(cashboxes, Mockito.atLeastOnce()).getCashboxOpen();
		Mockito.verify(cashboxes, Mockito.atLeastOnce()).saveCashbox(Mockito.any());
		Mockito.verify(sales, Mockito.atLeastOnce()).getSaleById(Mockito.anyString());
		Mockito.verify(sales, Mockito.atLeastOnce()).updateSale(Mockito.anyString(), Mockito.any());
		
		var resultAsString = MocksUtils.getContentAsString(result, "addPayNoCashboxTest");
		var objResult = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoDebtor());
		
		assertNotNull(objResult);
		assertNotNull(objResult.getData());
	}
	
	/**
	 * se agrega un pago completo y con una caja abierta
	 * @throws Exception
	 */
	@Test
	public void addAllPayCashboxTest() throws Exception {
		var existsDebtor = MocksFactory.createExistsDtoIntDebtor();
		existsDebtor.setTotal(MocksUtils.BIG_DECIMAL_00);
		
		Mockito.when(partialPymentMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntPartialPyment());
		Mockito.when(business.addPay(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(existsDebtor);
		Mockito.when(cashboxes.getCashboxOpen()).thenReturn(MocksFactory.createDtoIntCashboxOpen());
		Mockito.when(cashboxes.updateCashbox(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxClose());
		Mockito.when(sales.getSaleById(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		Mockito.when(sales.updateSale(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleOnCashbox());
		Mockito.doNothing().when(business).deleteDebtorById(Mockito.anyString());
		
		var result = mockMvc.perform(put("/api/v1/debtors/1a2b3c4d?time=" + MocksFactory.getNowDate())
                .contentType(MediaType.APPLICATION_JSON)
                .content(MocksUtils.parserToString(MocksFactory.createDtoPartialPyment())))
            .andExpect(status().isOk())
            .andReturn();
		
		Mockito.verify(partialPymentMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addPay(Mockito.anyString(), Mockito.any(), Mockito.anyLong());
		Mockito.verify(cashboxes, Mockito.atLeastOnce()).getCashboxOpen();
		Mockito.verify(cashboxes, Mockito.atLeastOnce()).updateCashbox(Mockito.anyString(), Mockito.any());
		Mockito.verify(sales, Mockito.atLeastOnce()).getSaleById(Mockito.anyString());
		Mockito.verify(sales, Mockito.atLeastOnce()).updateSale(Mockito.anyString(), Mockito.any());
		
		var resultAsString = MocksUtils.getContentAsString(result, "addPayNoCashboxTest");
		var objResult = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoDebtor());
		
		assertNotNull(objResult);
		assertNotNull(objResult.getData());
	}
	
	/**
	 * se agregapago incompleto y sin caja
	 * @throws Exception
	 */
	@Test
	public void addPartialPayNoCashboxTest() throws Exception {
		var existsDebtor = MocksFactory.createExistsDtoIntDebtor();
		existsDebtor.setTotal(MocksUtils.BIG_DECIMAL_3);
		
		Mockito.when(partialPymentMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntPartialPyment());
		Mockito.when(business.addPay(Mockito.anyString(), Mockito.any(), Mockito.anyLong())).thenReturn(existsDebtor);
		Mockito.when(cashboxes.getCashboxOpen()).thenReturn(null);
		Mockito.when(cashboxes.saveCashbox(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxOpen());
		Mockito.when(debtorMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createExistsDtoDebtor());
		
		var result = mockMvc.perform(put("/api/v1/debtors/1a2b3c4d?time=" + MocksFactory.getNowDate())
                .contentType(MediaType.APPLICATION_JSON)
                .content(MocksUtils.parserToString(MocksFactory.createDtoPartialPyment())))
            .andExpect(status().isOk())
            .andReturn();
		
		var resultAsString = MocksUtils.getContentAsString(result, "addPayNoCashboxTest");
		var objResult = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoDebtor());
		
		assertNotNull(objResult);
		assertNotNull(objResult.getData());
		assertNotNull(objResult.getData().getId());
	}

}
