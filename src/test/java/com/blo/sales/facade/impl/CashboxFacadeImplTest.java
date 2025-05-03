package com.blo.sales.facade.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.facade.enums.StatusCashboxEnum;
import com.blo.sales.facade.mapper.DtoCashboxMapper;
import com.blo.sales.facade.mapper.DtoCashboxesMapper;
import com.blo.sales.factory.MocksFactory;
import com.blo.sales.factory.MocksUtils;

@SpringBootTest
@AutoConfigureMockMvc
public class CashboxFacadeImplTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private DtoCashboxMapper cashboxMapper;
	
	@MockBean
	private DtoCashboxesMapper cashboxesMapper;
	
	@MockBean
	private ICashboxBusiness business;
	
	@MockBean
	private ISalesBusiness sales;
	
	@Test
	public void getAllCashboxesTest() throws Exception {
		Mockito.when(business.getAllCashboxes()).thenReturn(MocksFactory.createDtoIntCashboxes());
		Mockito.when(cashboxesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCashboxes());
		
		var result = mockMvc.perform(get("/api/v1/cashbox")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		Mockito.verify(business, Mockito.atLeastOnce()).getAllCashboxes();
		Mockito.verify(cashboxesMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		var resultAsString = MocksUtils.getContentAsString(result, "getAllCashboxesTest");
		var obj = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoCashboxes());
		
		assertNotNull(obj);
		assertNotNull(obj.getData().getBoxes());
		assertFalse(obj.getData().getBoxes().isEmpty());
	}
	
	/**
	 * Se cierra una caja que innexistente
	 * @throws Exception
	 */
	@Test
	public void closeCashboxNotCashboxOpenTest() throws Exception {
		Mockito.when(business.getCashboxOpen()).thenReturn(null);
		Mockito.when(sales.getSalesNotCashbox()).thenReturn(MocksFactory.createDtoIntSales());
		Mockito.when(sales.updateSale(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleOnCashbox());
		Mockito.when(cashboxMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxClose());
		Mockito.when(business.saveCashbox(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxClose());
		Mockito.when(cashboxMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCashboxClose());
		
		var result = mockMvc.perform(post("/api/v1/cashbox")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk())
            .andReturn();
		
		Mockito.verify(business, Mockito.atLeastOnce()).getCashboxOpen();
		Mockito.verify(sales, Mockito.atLeastOnce()).getSalesNotCashbox();
		Mockito.verify(sales, Mockito.atLeastOnce()).updateSale(Mockito.anyString(), Mockito.any());
		Mockito.verify(cashboxMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).saveCashbox(Mockito.any());
		Mockito.verify(cashboxMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		var resultAsString = MocksUtils.getContentAsString(result, "closeCashboxNotCashboxOpenTest");
		var obj = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoCashbox());
		
		assertNotNull(obj);
		assertEquals(StatusCashboxEnum.CLOSE.name(), obj.getData().getStatus().name());
	}
	
	/**
	 * 
	 * @throws Exception
	 */
	@Test
	public void closeCashboxCashboxOpenTest() throws Exception {
		Mockito.when(business.getCashboxOpen()).thenReturn(MocksFactory.createDtoIntCashboxOpen());
		Mockito.when(cashboxMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCashboxOpen());
		Mockito.when(sales.getSalesNotCashbox()).thenReturn(MocksFactory.createDtoIntSales());
		Mockito.when(sales.updateSale(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleOnCashbox());
		Mockito.when(cashboxMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxClose());
		Mockito.when(business.updateCashbox(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntCashboxClose());
		Mockito.when(cashboxMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCashboxClose());
		
		var result = mockMvc.perform(post("/api/v1/cashbox")
                .contentType(MediaType.APPLICATION_JSON).content("{}"))
            .andExpect(status().isOk())
            .andReturn();
		
		Mockito.verify(business, Mockito.atLeastOnce()).getCashboxOpen();
		Mockito.verify(cashboxMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(sales, Mockito.atLeastOnce()).getSalesNotCashbox();
		Mockito.verify(sales, Mockito.atLeastOnce()).updateSale(Mockito.anyString(), Mockito.any());
		Mockito.verify(cashboxMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).updateCashbox(Mockito.anyString(), Mockito.any());
		Mockito.verify(cashboxMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		var resultAsString = MocksUtils.getContentAsString(result, "closeCashboxNotCashboxOpenTest");
		var obj = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoCashbox());
		
		assertNotNull(obj);
		assertEquals(StatusCashboxEnum.CLOSE.name(), obj.getData().getStatus().name());
	}
}
