package com.blo.sales.facade.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.blo.sales.business.ICreditsBusiness;
import com.blo.sales.facade.mapper.DtoCreditMapper;
import com.blo.sales.facade.mapper.DtoCreditsMapper;
import com.blo.sales.facade.mapper.DtoPartialPymentMapper;
import com.blo.sales.factory.MocksFactory;
import com.blo.sales.factory.MocksUtils;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CreditsFacadeImplTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ICreditsBusiness business;
	
	@MockBean
	private DtoCreditMapper mapper;
	
	@MockBean
	private DtoCreditsMapper creditsMapper;
	
	@MockBean
	private DtoPartialPymentMapper paymentMapper;
	
	@Test
	public void addNewCreditTest() throws Exception {
		Mockito.when(mapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoInCreditSavedClose());
		Mockito.when(business.registerNewCredit(Mockito.any())).thenReturn(MocksFactory.createDtoInCreditSavedClose());
		Mockito.when(mapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCreditSaved());
		
		var result = mockMvc.perform(post("/api/v1/credits")
				.header(MocksUtils.X_TRACKING_ID, "addNewCreditTest")
                .contentType(MediaType.APPLICATION_JSON).content("{\r\n"
                		+ "    \"lender_name\": \"prueba_credito\",\r\n"
                		+ "    \"total_amount\": 1234,\r\n"
                		+ "    \"open_date\": 23123123,\r\n"
                		+ "    \"status_credit\": \"OPEN\"\r\n"
                		+ "}"))
            .andExpect(status().isCreated())
            .andReturn();
		
		var resultAsString = MocksUtils.getContentAsString(result, "addNewCreditTest");
		var obj = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoCredit());
		
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertNotNull(obj.getData().getId());
	}
	
	@Test
	public void getCreditsTest() throws Exception {
		Mockito.when(business.getAllCredits()).thenReturn(MocksFactory.createDtoIntCredits());
		Mockito.when(creditsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCredits());

		var result = mockMvc.perform(get("/api/v1/credits?status=ALL")
				.header(MocksUtils.X_TRACKING_ID, "getCreditsTest")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		var resultAsString = MocksUtils.getContentAsString(result, "getCreditsTest");
		var obj = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoCredits());
		
		assertNotNull(obj);
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertFalse(obj.getData().getCredits().isEmpty());
	}
	
	@Test
	public void getCreditsByOpenStatusTest() throws Exception {
		Mockito.when(business.getCreditsByStatus(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCredits());
		Mockito.when(creditsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCredits());

		var result = mockMvc.perform(get("/api/v1/credits?status=OPEN")
				.header(MocksUtils.X_TRACKING_ID, "getCreditsTest")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		var resultAsString = MocksUtils.getContentAsString(result, "getCreditsByTest");
		var obj = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoCredits());
		
		assertNotNull(obj);
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertFalse(obj.getData().getCredits().isEmpty());
	}
	
	@Test
	public void getCreditsByCloseStatusTest() throws Exception {
		Mockito.when(business.getCreditsByStatus(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCredits());
		Mockito.when(creditsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCredits());

		var result = mockMvc.perform(get("/api/v1/credits?status=CLOSE")
				.header(MocksUtils.X_TRACKING_ID, "getCreditsTest")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		var resultAsString = MocksUtils.getContentAsString(result, "getCreditsByTest");
		var obj = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoCredits());
		
		assertNotNull(obj);
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertFalse(obj.getData().getCredits().isEmpty());
	}
	
	@Test
	public void addPartialPaymentTest() throws Exception {
		Mockito.when(mapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoInCreditSavedClose());
		Mockito.when(business.registerNewCredit(Mockito.any())).thenReturn(MocksFactory.createDtoInCreditSavedClose());
		Mockito.when(mapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCreditSaved());
		
		var result = mockMvc.perform(put("/api/v1/credits/688c31e07266e50b0ebc58e4")
				.header(MocksUtils.X_TRACKING_ID, "addPartialPaymentTest")
                .contentType(MediaType.APPLICATION_JSON).content("{\r\n"
                		+ "    \"partial_pyment\": 50,\r\n"
                		+ "    \"date\": 1234312\r\n"
                		+ "}"))
            .andExpect(status().isOk())
            .andReturn();
		
		var resultAsString = MocksUtils.getContentAsString(result, "addPartialPaymentTest");
		var obj = MocksUtils.parserToCommonWrapper(resultAsString, MocksFactory.getReferenceFromDtoCredit());
		
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertNotNull(obj.getData().getId());
	}
}
