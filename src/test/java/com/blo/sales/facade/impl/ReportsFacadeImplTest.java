package com.blo.sales.facade.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.facade.mapper.DtoProductsOnSalesCounterMapper;
import com.blo.sales.factory.MocksFactory;
import com.blo.sales.factory.MocksUtils;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ReportsFacadeImplTest {
		
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ISalesBusiness salesBusiness;
	
	@MockBean
	private DtoProductsOnSalesCounterMapper productsOnSalesCounterMapper;
	
	@Test
	public void retrieveProductsOnSalesReportAllTest() throws Exception {
		Mockito.when(salesBusiness.getBestSellingProducts(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntProductsOnSalesCounter());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales?reportType=ALL&initialMonth=0&initYear=0&endMonth=0&endYear=0")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertFalse(objtProducts.getData().getProductsOnSales().isEmpty());
		assertNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveProductsOnSalesReportPeriodTest() throws Exception {
		Mockito.when(salesBusiness.getBestSellingProducts(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntProductsOnSalesCounter());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales?reportType=BY_PERIOD&initialMonth=8&initYear=2025&endMonth=8&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportPeriodTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertFalse(objtProducts.getData().getProductsOnSales().isEmpty());
		assertNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveProductsOnSalesReportPeriodMen2025Test() throws Exception {
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales?reportType=BY_PERIOD&initialMonth=8&initYear=2024&endMonth=8&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportPeriodMen2025Test");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveProductsOnSalesReportPeriodMenInitMothTest() throws Exception {
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales?reportType=BY_PERIOD&initialMonth=0&initYear=2024&endMonth=8&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportPeriodMenInitMothTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveProductsOnSalesReportPeriodMayInitMothTest() throws Exception {
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales?reportType=BY_PERIOD&initialMonth=13&initYear=2024&endMonth=8&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportPeriodMayInitMothTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveProductsOnSalesReportPeriodMinEndMothTest() throws Exception {
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales?reportType=BY_PERIOD&initialMonth=1&initYear=2025&endMonth=0&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportPeriodMinEndMothTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveProductsOnSalesReportPeriodMayEndMothTest() throws Exception {
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales?reportType=BY_PERIOD&initialMonth=10&initYear=2025&endMonth=13&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportPeriodMayEndMothTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveProductsOnSalesReportPeriodYearsErrorsTest() throws Exception {
		var result = mockMvc.perform(get("/api/v1/reports/products-on-sales?reportType=BY_PERIOD&initialMonth=10&initYear=2026&endMonth=12&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveProductsOnSalesReportTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveProductsOnSalesReportPeriodYearsErrorsTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoProductsOnSalesCounter());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveSalesByDateAllSalesTest() throws Exception {
		Mockito.when(salesBusiness.getSalesByDate(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntSalesDetailReport());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/sales?reportType=ALL&initialMonth=0&initYear=0&endMonth=0&endYear=0")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveSalesByDateAllSalesTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveSalesByDateAllSalesTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoSalesDetailReport());
		
		assertNotNull(objtProducts);
		assertFalse(objtProducts.getData().getSales().isEmpty());
		assertNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveSalesByDatePeriodSalesTest() throws Exception {
		Mockito.when(salesBusiness.getSalesByDate(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntSalesDetailReport());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/sales?reportType=BY_PERIOD&initialMonth=3&initYear=2025&endMonth=4&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveSalesByDatePeriodSalesTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isOk())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveSalesByDatePeriodSalesTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoSalesDetailReport());
		
		assertNotNull(objtProducts);
		assertFalse(objtProducts.getData().getSales().isEmpty());
		assertNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveSalesByDatePeriodErrorInitYearTest() throws Exception {
		Mockito.when(salesBusiness.getSalesByDate(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntSalesDetailReport());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/sales?reportType=BY_PERIOD&initialMonth=3&initYear=2024&endMonth=4&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveSalesByDatePeriodErrorInitYearTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveSalesByDatePeriodErrorInitYearTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoSalesDetailReport());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveSalesByDatePeriodErrorEndYearTest() throws Exception {
		Mockito.when(salesBusiness.getSalesByDate(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntSalesDetailReport());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/sales?reportType=BY_PERIOD&initialMonth=3&initYear=2025&endMonth=4&endYear=2024")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveSalesByDatePeriodErrorEndYearTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveSalesByDatePeriodErrorEndYearTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoSalesDetailReport());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveSalesByDatePeriodErrorInitMonthTest() throws Exception {
		Mockito.when(salesBusiness.getSalesByDate(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntSalesDetailReport());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/sales?reportType=BY_PERIOD&initialMonth=0&initYear=2025&endMonth=1&endYear=2024")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveSalesByDatePeriodErrorInitMonthTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveSalesByDatePeriodErrorInitMonthTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoSalesDetailReport());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveSalesByDatePeriodErrorEndMonthTest() throws Exception {
		Mockito.when(salesBusiness.getSalesByDate(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntSalesDetailReport());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/sales?reportType=BY_PERIOD&initialMonth=3&initYear=2025&endMonth=13&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveSalesByDatePeriodErrorEndMonthTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveSalesByDatePeriodErrorEndMonthTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoSalesDetailReport());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
	
	@Test
	public void retrieveSalesByDatePeriodErrorYearsTest() throws Exception {
		Mockito.when(salesBusiness.getSalesByDate(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(MocksFactory.createDtoIntSalesDetailReport());
		Mockito.when(productsOnSalesCounterMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsOnSalesCounter());
		
		var result = mockMvc.perform(get("/api/v1/reports/sales?reportType=BY_PERIOD&initialMonth=3&initYear=2026&endMonth=13&endYear=2025")
	    		.header(MocksUtils.X_TRACKING_ID, "retrieveSalesByDatePeriodErrorEndMonthTest")
	            .contentType(MediaType.APPLICATION_JSON))
	            .andExpect(status().isBadRequest())
	            .andReturn();
		
		var productsOnSales = MocksUtils.getContentAsString(result, "retrieveSalesByDatePeriodErrorEndMonthTest");
		var objtProducts = MocksUtils.parserToCommonWrapper(productsOnSales,  MocksFactory.getReferenceFromDtoSalesDetailReport());
		
		assertNotNull(objtProducts);
		assertNotNull(objtProducts.getError());
	}
}
