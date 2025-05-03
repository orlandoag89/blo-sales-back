package com.blo.sales.facade.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.blo.sales.business.IDebtorsBusiness;
import com.blo.sales.business.IProductsBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.facade.dto.DtoWrapperSale;
import com.blo.sales.facade.mapper.DtoDebtorMapper;
import com.blo.sales.facade.mapper.DtoProductMapper;
import com.blo.sales.facade.mapper.DtoSaleMapper;
import com.blo.sales.facade.mapper.DtoSalesMapper;
import com.blo.sales.factory.MocksFactory;
import com.blo.sales.factory.MocksUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
	"exceptions.messages.product-insufficient=Productos insuficientes",
	"exceptions.codes.product-insufficient=ERR03"
})
public class SalesFacadeImplTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private ISalesBusiness business;
	
	@MockBean
	private IDebtorsBusiness debtorBusiness;
	
	@MockBean
	private IProductsBusiness productsBusiness;
	
	@MockBean
	private DtoSalesMapper salesMapper;
	
	@MockBean
	private DtoSaleMapper saleMapper;
	
	@MockBean
	private DtoProductMapper productMapper;
	
	@MockBean
	private DtoDebtorMapper debtorMapper;

	/**
	 * Venta cerrada sin deudor y sin productos con alerta de terminar
	 * @throws Exception 
	 */
	@Test
	public void registerSaleTest() throws Exception {
		var sale = MocksFactory.createDtoNewSaleNoCashbox();
		var saleSaved = MocksFactory.createDtoIntSaleNoCashbox();
		var saleAsString = objectMapper.writeValueAsString(sale);
		
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(saleSaved);
		Mockito.when(business.addSale(Mockito.any())).thenReturn(saleSaved);
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoSaleNoCashbox());
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productsBusiness.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		
		 var result = mockMvc.perform(post("/api/v1/sales")
				 	.header(MocksUtils.X_TRACKING_ID, "registerSaleTest")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(saleAsString))
	            .andExpect(status().isCreated())
	            .andReturn();
		
		var registerSale = MocksUtils.getContentAsString(result, "registerSaleTest");
		var objtSale = MocksUtils.parserToCommonWrapper(registerSale,  MocksFactory.getReferenceFromWrapperSale());
		
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addSale(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		
		assertNotNull(result);
		assertNotNull(registerSale);
		assertNotNull(objtSale.getData().getSale());
		assertNotNull(objtSale.getData().getSale().getId());
		assertNull(objtSale.getData().getDebtor());
		assertNull(objtSale.getError());
		assertTrue(objtSale.getData().getProductsWithAlerts().isEmpty());
	}
	
	@Test
	public void registerInvalidSaleTest() throws Exception {
		var sale = MocksFactory.createDtoNewSaleNoCashbox();
		sale.setTotal(new BigDecimal("-1"));
		var saleSaved = MocksFactory.createDtoIntSaleNoCashbox();
		saleSaved.setTotal(new BigDecimal("-1"));
		var saleNoCashbox = MocksFactory.createDtoSaleNoCashbox();
		saleNoCashbox.setTotal(BigDecimal.ZERO);
		var saleAsString = objectMapper.writeValueAsString(sale);
		
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(saleSaved);
		Mockito.when(business.addSale(Mockito.any())).thenReturn(saleSaved);
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(saleNoCashbox);
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productsBusiness.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		
		 var result = mockMvc.perform(post("/api/v1/sales")
				 	.header(MocksUtils.X_TRACKING_ID, "registerInvalidSaleTest")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(saleAsString))
	            .andExpect(status().isCreated())
	            .andReturn();
		
		var registerSale = MocksUtils.getContentAsString(result, "registerInvalidSaleTest");
		var objtSale = MocksUtils.parserToCommonWrapper(registerSale,  MocksFactory.getReferenceFromWrapperSale());
		
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addSale(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		
		assertNotNull(result);
		assertNotNull(registerSale);
		assertNotNull(objtSale.getData().getSale());
		assertNotNull(objtSale.getData().getSale().getId());
		assertNull(objtSale.getData().getDebtor());
		assertEquals(BigDecimal.ZERO, objtSale.getData().getSale().getTotal());
		assertNull(objtSale.getError());
		assertTrue(objtSale.getData().getProductsWithAlerts().isEmpty());
	}
	
	/**
	 * Se intenta registrar una venta con productos insuficientes
	 * @throws Exception
	 */
	@Test
	public void registerSaleWithInsuficientProductsTest() throws Exception {
		var sale = MocksFactory.createDtoNewSaleNoCashbox();
		sale.getProducts().get(0).setQuantity_on_sale(MocksUtils.BIG_DECIMAL_10);
		
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		
		var saleAsString = objectMapper.writeValueAsString(sale);
		
		 var result = mockMvc.perform(post("/api/v1/sales")
				 	.header(MocksUtils.X_TRACKING_ID, "registerSaleWithInsuficientProductsTest")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(saleAsString))
	            .andExpect(status().isUnprocessableEntity())
	            .andReturn();
		 
		 var content = MocksUtils.getContentAsString(result, "registerSaleWithInsuficientProductsTest");
		 var obj = MocksUtils.parserToCommonWrapper(content,  MocksFactory.getReferenceFromWrapperSale());
		 
		 Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		 
		 assertNotNull(obj);
		 assertNotNull(obj.getError());
	}
	
	/**
	 * se registran ventas y productos que traen alerta
	 * @throws Exception
	 */
	@Test
	public void registerSaleWithAlertsProductsTest() throws Exception {
		var saleSaved = MocksFactory.createDtoIntSaleNoCashbox();
		var sale = MocksFactory.createDtoNewSaleNoCashbox();
		sale.getProducts().get(0).setQuantity_on_sale(MocksUtils.BIG_DECIMAL_3);
		
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(saleSaved);
		Mockito.when(business.addSale(Mockito.any())).thenReturn(saleSaved);
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoSaleNoCashbox());
		Mockito.when(productMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProduct());
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productsBusiness.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		
		var saleAsString = objectMapper.writeValueAsString(sale);
		
		 var result = mockMvc.perform(post("/api/v1/sales")
				 	.header(MocksUtils.X_TRACKING_ID, "registerSaleWithAlertsProductsTest")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(saleAsString))
	            .andExpect(status().isCreated())
	            .andReturn();
		 
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addSale(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(productMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		 
		 var product = MocksUtils.getContentAsString(result, "registerSaleWithAlertsProductsTest");
		 var obj = MocksUtils.parserToCommonWrapper(product,  MocksFactory.getReferenceFromWrapperSale());
		 
		 assertNotNull(product);
		 assertNotNull(obj);
		 assertNotNull(obj.getData());
		 assertFalse(obj.getData().getProductsWithAlerts().isEmpty());
	}
	
	/**
	 * Recupera todas las ventas
	 * @throws Exception
	 */
	@Test
	public void retrieveAllSalesTest() throws Exception {
		Mockito.when(business.getSales()).thenReturn(MocksFactory.createDtoIntSales());
		Mockito.when(salesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoSales());
		
		var result = mockMvc.perform(get("/api/v1/sales?status=ALL")
				.header(MocksUtils.X_TRACKING_ID, "retrieveAllSalesTest")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		var product = MocksUtils.getContentAsString(result, "retrieveAllSalesTest");
		var obj = MocksUtils.parserToCommonWrapper(product, MocksFactory.getReferenceFromDtoSales());
		
		Mockito.verify(business, Mockito.atLeastOnce()).getSales();
		Mockito.verify(salesMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(product);
		assertNotNull(obj.getData());
		assertFalse(obj.getData().getSales().isEmpty());
		assertNull(obj.getError());
	}
	
	/**
	 * Recupera las ventas incompletas
	 * @throws Exception
	 */
	@Test
	public void retrieveOpenSalesTest() throws Exception {
		Mockito.when(business.getSalesOpen()).thenReturn(MocksFactory.createOpenDtoIntSales());
		Mockito.when(salesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createOpenDtoSales());
		
		var result = mockMvc.perform(get("/api/v1/sales?status=OPEN")
				.header(MocksUtils.X_TRACKING_ID, "retrieveOpenSalesTest")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		var product = MocksUtils.getContentAsString(result, "retrieveOpenSalesTest");
		var obj = MocksUtils.parserToCommonWrapper(product,  MocksFactory.getReferenceFromDtoSales());
		
		Mockito.verify(business, Mockito.atLeastOnce()).getSalesOpen();
		Mockito.verify(salesMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(product);
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertFalse(obj.getData().getSales().isEmpty());
		assertNull(obj.getError());
	}
	
	/**
	 * recupera todas las ventas
	 * @throws Exception
	 */
	@Test
	public void retrieveCloseTest() throws Exception {
		Mockito.when(business.getSalesClose()).thenReturn(MocksFactory.createOpenDtoIntSales());
		Mockito.when(salesMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createOpenDtoSales());
		
		var result = mockMvc.perform(get("/api/v1/sales?status=CLOSE")
				.header(MocksUtils.X_TRACKING_ID, "retrieveCloseTest")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		var product = MocksUtils.getContentAsString(result, "retrieveCloseTest");
		var obj = MocksUtils.parserToCommonWrapper(product,  MocksFactory.getReferenceFromDtoSales());
		
		Mockito.verify(business, Mockito.atLeastOnce()).getSalesClose();
		Mockito.verify(salesMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(product);
		assertNotNull(obj);
		assertNotNull(obj.getData().getSales());
		assertFalse(obj.getData().getSales().isEmpty());
		assertNull(obj.getError());
	}
	
	/**
	 * Recupera todos los detalles de una venta por id
	 * @throws Exception
	 */
	@Test
	public void retrieveSaleByIdTest() throws Exception {
		Mockito.when(business.getSaleById(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntSaleOnCashbox());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoSaleOnCashbox());
		
		var result = mockMvc.perform(get("/api/v1/sales/1a2b3c4d5e")
				.header(MocksUtils.X_TRACKING_ID, "retrieveSaleByIdTest")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		Mockito.verify(business, Mockito.atLeastOnce()).getSaleById(Mockito.anyString());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		var sale = MocksUtils.getContentAsString(result, "retrieveSaleByIdTest");
		var obj = objectMapper.readValue(sale, MocksFactory.getReferenceFromDtoSale());
		
		assertNotNull(sale);
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertNotNull(obj.getData().getId());
	}
	
	/**
	 * intenta recuperar los ids cuando el campo no es informado
	 * @throws Exception
	 */
	@Test
	public void retrieveSaleByIdEmptyTest() throws Exception {
		var result = mockMvc.perform(get("/api/v1/sales/ ")
				.header(MocksUtils.X_TRACKING_ID, "retrieveSaleByIdEmptyTest")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
            .andReturn();
		
		var sale = MocksUtils.getContentAsString(result, "retrieveSaleByIdEmptyTest");
		var obj = objectMapper.readValue(sale, MocksFactory.getReferenceFromDtoSale());
		
		assertNotNull(sale);
		assertNotNull(obj);
		assertNotNull(obj.getError());
	}
	
	/**
	 * Se registra una venta con un nuevo deudor que no existe en la base de datos y
	 * en la respuesta no tiene productos con alerta
	 * @throws Exception
	 */
	@Test
	public void registerSaleAndNewDebtorTest() throws Exception {
		var newDebtor = MocksFactory.createNewDtoDebtor();
		var saleFromDebtor = MocksFactory.createDtoSaleNoCashboxAndOpen();
		var wrapperSale = new DtoWrapperSale();
		wrapperSale.setDebtor(newDebtor);
		wrapperSale.setSale(saleFromDebtor);
		
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productsBusiness.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashboxAndOpen());
		Mockito.when(business.addSale(Mockito.any())).thenReturn(MocksFactory.createSavedDtoIntSaleNoCashbox());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoNewSaleNoCashbox());
		Mockito.when(debtorMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createNewDtoIntDebtor());
		Mockito.when(debtorBusiness.addDebtor(Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		Mockito.when(debtorMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createExistsDtoDebtor());
		
		var body = objectMapper.writeValueAsString(wrapperSale);
		
		var response = mockMvc.perform(post("/api/v1/sales/debtors?partialPyment=10")
				.header(MocksUtils.X_TRACKING_ID, "registerSaleAndNewDebtorTest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		var data = MocksUtils.getContentAsString(response, "registerSaleAndNewDebtorTest");
		var obj = objectMapper.readValue(data, MocksFactory.getReferenceFromWrapperSale());
		
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addSale(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(debtorMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(debtorBusiness, Mockito.atLeastOnce()).addDebtor(Mockito.any());
		Mockito.verify(debtorMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(obj);
		assertNotNull(obj.getData().getDebtor());
		assertNotNull(obj.getData().getDebtor().getId());
		assertFalse(obj.getData().getDebtor().getPartial_pyments().isEmpty());
	}
	
	/**
	 * Se registra una venta con un nuevo deudor que no existe en la base de datos y
	 * en la respuesta tiene productos con alerta
	 * @throws Exception
	 */
	@Test
	public void registerSaleAndNewDebtorAlertProductsTest() throws Exception {
		var newDebtor = MocksFactory.createNewDtoDebtor();
		var saleFromDebtor = MocksFactory.createDtoSaleNoCashboxAndOpen();
		saleFromDebtor.getProducts().get(0).setQuantity_on_sale(MocksUtils.BIG_DECIMAL_3);
		var wrapperSale = new DtoWrapperSale();
		wrapperSale.setDebtor(newDebtor);
		wrapperSale.setSale(saleFromDebtor);
		
		
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productsBusiness.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashboxAndOpen());
		Mockito.when(business.addSale(Mockito.any())).thenReturn(MocksFactory.createSavedDtoIntSaleNoCashbox());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoNewSaleNoCashbox());
		Mockito.when(debtorMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createNewDtoIntDebtor());
		Mockito.when(debtorBusiness.addDebtor(Mockito.any())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		Mockito.when(debtorMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createExistsDtoDebtor());
		Mockito.when(productMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProduct());
		
		var body = objectMapper.writeValueAsString(wrapperSale);
		
		var response = mockMvc.perform(post("/api/v1/sales/debtors?partialPyment=100")
				.header(MocksUtils.X_TRACKING_ID, "registerSaleAndNewDebtorAlertProductsTest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		var data = MocksUtils.getContentAsString(response, "registerSaleAndNewDebtorAlertProductsTest");
		var obj = objectMapper.readValue(data, MocksFactory.getReferenceFromWrapperSale());
		
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addSale(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(debtorMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(debtorBusiness, Mockito.atLeastOnce()).addDebtor(Mockito.any());
		Mockito.verify(debtorMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(productMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(obj);
		assertNotNull(obj.getData().getDebtor());
		assertNotNull(obj.getData().getDebtor().getId());
		assertNotNull(obj.getData().getDebtor().getPartial_pyments().isEmpty());
		assertNotNull(obj.getData().getProductsWithAlerts().isEmpty());
	}
	
	/**
	 * Error cuando la diferencia entre la cuenta total y pago parcial no es igual al pago parcial
	 * @throws Exception
	 */
	@Test
	public void registerSalePartialPymentoErrorTest() throws Exception {
		var newDebtor = MocksFactory.createNewDtoDebtor();
		var saleFromDebtor = MocksFactory.createDtoSaleNoCashboxAndOpen();
		saleFromDebtor.getProducts().get(0).setQuantity_on_sale(MocksUtils.BIG_DECIMAL_3);
		var wrapperSale = new DtoWrapperSale();
		wrapperSale.setDebtor(newDebtor);
		wrapperSale.setSale(saleFromDebtor);
		
		var body = objectMapper.writeValueAsString(wrapperSale);
		
		var response = mockMvc.perform(post("/api/v1/sales/debtors?partialPyment=10")
				.header(MocksUtils.X_TRACKING_ID, "registerSalePartialPymentoErrorTest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isBadRequest()).andReturn();
		
		var data = MocksUtils.getContentAsString(response, "registerSalePartialPymentoErrorTest");
		var obj = objectMapper.readValue(data, MocksFactory.getReferenceFromWrapperSale());
		
		assertNotNull(obj);
		assertNotNull(obj.getError());
	}
	
	/**
	 * Error cuando la diferencia entre la cuenta total y pago parcial no es igual al pago parcial
	 * @throws Exception
	 */
	@Test
	public void registerSalePartialPymentsNotEqualsoErrorTest() throws Exception {
		var newDebtor = MocksFactory.createNewDtoDebtor();
		var saleFromDebtor = MocksFactory.createDtoSaleNoCashboxAndOpen();
		saleFromDebtor.getProducts().get(0).setQuantity_on_sale(MocksUtils.BIG_DECIMAL_3);
		var wrapperSale = new DtoWrapperSale();
		wrapperSale.setDebtor(newDebtor);
		wrapperSale.setSale(saleFromDebtor);
		wrapperSale.getSale().setTotal(MocksUtils.BIG_DECIMAL_100);
		
		var body = objectMapper.writeValueAsString(wrapperSale);
		
		var response = mockMvc.perform(post("/api/v1/sales/debtors?partialPyment=10")
				.header(MocksUtils.X_TRACKING_ID, "registerSalePartialPymentsNotEqualsoErrorTest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isBadRequest()).andReturn();
		
		var data = MocksUtils.getContentAsString(response, "registerSalePartialPymentsNotEqualsoErrorTest");
		var obj = objectMapper.readValue(data, MocksFactory.getReferenceFromWrapperSale());
		
		assertNotNull(obj);
		assertNotNull(obj.getError());
	}
	
	/**
	 * se registra una venta con un deudor que existe y no tiene productos con alerta
	 * el deudor tiene una deuda de 50 pesos y una compra de 50
	 * no dejó abono, así que su deuda ahora será 100
	 * @throws Exception
	 */
	@Test
	public void registerSaleAndOldDebtorTest() throws Exception {
		var debtor = MocksFactory.createExistsDtoDebtor();
		var sale = MocksFactory.createDtoSaleNoCashboxAndOpen();
		var wrapper = new DtoWrapperSale();
		wrapper.setDebtor(debtor);
		wrapper.setSale(sale);
		
		var body = objectMapper.writeValueAsString(wrapper);
		
		var debtorUpdated = MocksFactory.createExistsDtoIntDebtor();
		var total = debtorUpdated.getTotal().add(sale.getTotal());
		debtorUpdated.setTotal(total);
		
		var debtorUpdated2 = MocksFactory.createExistsDtoDebtor();
		debtorUpdated2.setTotal(MocksUtils.BIG_DECIMAL_100);
				
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productsBusiness.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(debtorBusiness.getDebtorById(Mockito.anyString())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashboxAndOpen());
		Mockito.when(business.addSale(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoNewSaleNoCashbox());
		Mockito.when(debtorBusiness.updateDebtor(Mockito.anyString(), Mockito.any())).thenReturn(debtorUpdated);
		Mockito.when(debtorMapper.toOuter(Mockito.any())).thenReturn(debtorUpdated2);
		
		var response = mockMvc.perform(post("/api/v1/sales/debtors?partialPyment=0")
				.header(MocksUtils.X_TRACKING_ID, "registerSaleAndOldDebtorTest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		var data = MocksUtils.getContentAsString(response, "registerSaleAndOldDebtorTest");
		var obj = objectMapper.readValue(data, MocksFactory.getReferenceFromWrapperSale());
		
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		Mockito.verify(debtorBusiness, Mockito.atLeastOnce()).getDebtorById(Mockito.anyString());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addSale(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(debtorBusiness, Mockito.atLeastOnce()).updateDebtor(Mockito.anyString(), Mockito.any());
		Mockito.verify(debtorMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(data);
		assertNotNull(obj);
		assertNotNull(obj);
		assertNotNull(obj.getData().getDebtor());
		assertNotNull(obj.getData().getDebtor().getId());
		assertNotNull(obj.getData().getDebtor().getPartial_pyments().isEmpty());
		assertNotNull(obj.getData().getProductsWithAlerts().isEmpty());
		assertEquals(MocksUtils.BIG_DECIMAL_100, obj.getData().getDebtor().getTotal());
	}
	
	/**
	 * se registra una venta con un deudor que existe y no tiene productos con alerta
	 * el deudor tiene una deuda de 50 pesos y una compra de 50
	 * dejó abono de 30, así que su deuda se reduce a 70 
	 * @throws Exception
	 */
	@Test
	public void registerSaleAndOldDebtorWithPartialPymentTest() throws Exception {
		var debtor = MocksFactory.createExistsDtoDebtor();
		var sale = MocksFactory.createDtoSaleNoCashboxAndOpen();
		var wrapper = new DtoWrapperSale();
		wrapper.setDebtor(debtor);
		wrapper.setSale(sale);
		
		var body = objectMapper.writeValueAsString(wrapper);
		
		var debtorUpdated = MocksFactory.createExistsDtoIntDebtor();
		debtorUpdated.setTotal(MocksUtils.BIG_DECIMAL_70);
		
		var debtorUpdated2 = MocksFactory.createExistsDtoDebtor();
		debtorUpdated2.setTotal(MocksUtils.BIG_DECIMAL_70);
				
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productsBusiness.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(debtorBusiness.getDebtorById(Mockito.anyString())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashboxAndOpen());
		Mockito.when(business.addSale(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoNewSaleNoCashbox());
		Mockito.when(debtorBusiness.updateDebtor(Mockito.anyString(), Mockito.any())).thenReturn(debtorUpdated);
		Mockito.when(debtorMapper.toOuter(Mockito.any())).thenReturn(debtorUpdated2);
		
		var response = mockMvc.perform(post("/api/v1/sales/debtors?partialPyment=30")
				.header(MocksUtils.X_TRACKING_ID, "registerSaleAndOldDebtorWithPartialPymentTest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		var data = MocksUtils.getContentAsString(response, "registerSaleAndOldDebtorWithPartialPymentTest");
		var obj = objectMapper.readValue(data, MocksFactory.getReferenceFromWrapperSale());
		
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		Mockito.verify(debtorBusiness, Mockito.atLeastOnce()).getDebtorById(Mockito.anyString());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addSale(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(debtorBusiness, Mockito.atLeastOnce()).updateDebtor(Mockito.anyString(), Mockito.any());
		Mockito.verify(debtorMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(data);
		assertNotNull(obj);
		assertNotNull(obj);
		assertNotNull(obj.getData().getDebtor());
		assertNotNull(obj.getData().getDebtor().getId());
		assertNotNull(obj.getData().getDebtor().getPartial_pyments().isEmpty());
		assertNotNull(obj.getData().getProductsWithAlerts().isEmpty());
		assertEquals(MocksUtils.BIG_DECIMAL_70, obj.getData().getDebtor().getTotal());
	}
	
	/**
	 * se registra una venta con un deudor que existe y no tiene productos con alerta
	 * el deudor tiene una deuda de 50 pesos y una compra de 50
	 * dejó abono de 500, así que el deudor se elimina
	 * @throws Exception
	 */
	@Test
	public void registerSaleAndOldDebtorWithAllPymentTest() throws Exception {
		var debtor = MocksFactory.createExistsDtoDebtor();
		var sale = MocksFactory.createDtoSaleNoCashboxAndOpen();
		var wrapper = new DtoWrapperSale();
		wrapper.setDebtor(debtor);
		wrapper.setSale(sale);
		
		var body = objectMapper.writeValueAsString(wrapper);
		
		var debtorUpdatedInt = MocksFactory.createExistsDtoIntDebtor();
		debtorUpdatedInt.setTotal(MocksUtils.BIG_DECIMAL_70);
		
		var debtorUpdatedOuter = MocksFactory.createExistsDtoDebtor();
		debtorUpdatedOuter.setTotal(MocksUtils.BIG_DECIMAL_70);
		
		Mockito.when(productsBusiness.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(productsBusiness.updateProduct(Mockito.anyString(), Mockito.any())).thenReturn(MocksFactory.createDtoIntProduct());
		Mockito.when(debtorBusiness.getDebtorById(Mockito.anyString())).thenReturn(MocksFactory.createExistsDtoIntDebtor());
		Mockito.when(saleMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashboxAndOpen());
		Mockito.when(business.addSale(Mockito.any())).thenReturn(MocksFactory.createDtoIntSaleNoCashbox());
		Mockito.when(saleMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoSaleNoCashbox());
		Mockito.doNothing().when(debtorBusiness).deleteDebtorById(Mockito.anyString());
		
		var response = mockMvc.perform(post("/api/v1/sales/debtors?partialPyment=500")
				.header(MocksUtils.X_TRACKING_ID, "registerSaleAndOldDebtorWithAllPymentTest")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body))
				.andExpect(status().isCreated()).andReturn();
		
		var data = MocksUtils.getContentAsString(response, "registerSaleAndOldDebtorWithAllPymentTest");
		var obj = objectMapper.readValue(data, MocksFactory.getReferenceFromWrapperSale());
		
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).getProduct(Mockito.anyString());
		Mockito.verify(productsBusiness, Mockito.atLeastOnce()).updateProduct(Mockito.anyString(), Mockito.any());
		Mockito.verify(debtorBusiness, Mockito.atLeastOnce()).getDebtorById(Mockito.anyString());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).addSale(Mockito.any());
		Mockito.verify(saleMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		Mockito.verify(debtorBusiness, Mockito.atLeastOnce()).deleteDebtorById(Mockito.anyString());
		
		assertNotNull(data);
		assertNotNull(obj);
		assertNotNull(obj.getData().getSale());
		assertNull(obj.getData().getDebtor().getId());
	}
}
