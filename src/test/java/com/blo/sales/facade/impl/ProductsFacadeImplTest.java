package com.blo.sales.facade.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.blo.sales.business.IProductsBusiness;
import com.blo.sales.facade.mapper.DtoProductMapper;
import com.blo.sales.facade.mapper.DtoProductsMapper;
import com.blo.sales.factory.MocksFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
	"excpetions.messages.products-empty=ERR05",
	"excpetions.codes.products-empty=Productos no presente"
})
public class ProductsFacadeImplTest {
	
	private static final String EMPTY_STRING = "";

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private IProductsBusiness service;

	@MockBean
	private DtoProductsMapper productsMapper;
	
	@MockBean
	private DtoProductMapper productMapper;
	
	public void beforeEach() {
		
	}

	/**
	 * se registran nuevos productos
	 * @throws Exception 
	 * @throws JsonProcessingException 
	 */
	@Test
	public void addProductsTest() throws JsonProcessingException, Exception {
	    var products = MocksFactory.createDtoProducts();
	    var allProducts = MocksFactory.createDtoIntProductsSaved();
	    var allProductsAsString = objectMapper.writeValueAsString(allProducts);

	    when(productsMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntProducts());
	    when(productsMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoProductsSaved());

	    when(service.addProducts(Mockito.any())).thenReturn(allProducts); 

	    var result = mockMvc.perform(post("/api/v1/products")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(objectMapper.writeValueAsString(products)))
	            .andExpect(status().isCreated())
	            .andReturn();

	   verify(productsMapper, atLeastOnce()).toInner(Mockito.any());
	   verify(productsMapper, atLeastOnce()).toOuter(Mockito.any());
	   assertNotNull(result);
	   assertEquals(allProductsAsString, getContentAsString(result, "addProductsTest"));
	}
	
	/**
	 * se intenta agregar productos
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	public void addProductsNullProductsTest() throws JsonProcessingException, Exception {
		var result = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(null)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		
		assertNotNull(result);
		assertEquals(EMPTY_STRING, getContentAsString(result, "addProductsEmptyListTest"));
	}
	
	/**
	 * No se manda una lista null
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	public void addProductsNullListTest() throws JsonProcessingException, Exception {
		var productsToRegister = MocksFactory.createDtoProducts();
		productsToRegister.setProducts(null);
		
		var result = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productsToRegister)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		
		assertNotNull(result);
		assertEquals(EMPTY_STRING, getContentAsString(result, "addProductsEmptyListTest"));
	}
	
	/**
	 * se envia una lista vacía
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	public void addProductsEmptyListTest() throws JsonProcessingException, Exception {
		var productsToRegister = MocksFactory.createDtoProducts();
		productsToRegister.setProducts(new ArrayList<>());
		
		var result = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productsToRegister)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		
		assertNotNull(result);
		assertEquals(EMPTY_STRING, getContentAsString(result, "addProductsEmptyListTest"));
	}
	
	
	/**
	 * recupera todos los productos de la db
	 * @throws Exception 
	 */
	@Test
	public void retrieveAllProductsTest() throws Exception {
		var productsOut = MocksFactory.createDtoProducts();
		var productsOutString = objectMapper.writeValueAsString(productsOut);
		
		when(service.getProducts()).thenReturn(MocksFactory.createDtoIntProducts());
		when(productsMapper.toOuter(Mockito.any())).thenReturn(productsOut);
		
		var result = mockMvc.perform(get("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		verify(service, atLeastOnce()).getProducts();
		verify(productsMapper, atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(result);
		assertEquals(productsOutString, getContentAsString(result, "retrieveAllProductsTest"));
	}
	
	/**
	 * Recupera un producto
	 * @throws Exception
	 */
	@Test
	public void retrieveProductTest() throws Exception {
		var productFromService = MocksFactory.createDtoProduct();
		var productString = objectMapper.writeValueAsString(productFromService);
		
		when(service.getProduct(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntProduct());
		when(productMapper.toOuter(Mockito.any())).thenReturn(productFromService);
		
		var result = mockMvc.perform(get("/api/v1/products/id")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andReturn();
		
		verify(service, atLeastOnce()).getProduct(anyString());
		verify(productMapper, atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(result);
		assertEquals(productString, getContentAsString(result, "retrieveProductTest"));
	}
	
	/**
	 * envia un id vacio para recuperar un producto
	 * @throws Exception
	 */
	@Test
	public void retrieveProductEmptyIdTest() throws Exception {
		var result = mockMvc.perform(get("/api/v1/products/ ")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		assertEquals(EMPTY_STRING, getContentAsString(result, "retrieveProductTest"));
	}
	
	/**
	 * Actualiza un producto
	 * @throws Exception
	 */
	@Test
	public void updateProductById() throws Exception {
		var productToUpdate = MocksFactory.createDtoProduct();
		
		when(productMapper.toInner(any())).thenReturn(MocksFactory.createDtoIntProduct());
		when(service.updateProduct(anyString(), any())).thenReturn(MocksFactory.createDtoIntProduct());
		when(productMapper.toOuter(any())).thenReturn(MocksFactory.createDtoProduct());
		
		var productToUpdateAsString = objectMapper.writeValueAsString(productToUpdate);
		
		var result = mockMvc.perform(put("/api/v1/products/1a2b3c4d")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productToUpdateAsString))
            .andExpect(status().isOk())
            .andReturn();
		
		verify(productMapper, atLeastOnce()).toInner(Mockito.any());
		verify(service, atLeastOnce()).updateProduct(anyString(), any());
		verify(productMapper, atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(result);
		assertEquals(productToUpdateAsString, getContentAsString(result, "updateProductById"));
	}
	
	/**
	 * se intenta actulizar un producto pero sin id
	 * @throws Exception
	 */
	@Test
	public void updateProductByIdNotId() throws Exception {
		var productToUpdate = MocksFactory.createDtoProduct();
		
		var productToUpdateAsString = objectMapper.writeValueAsString(productToUpdate);
		
		var result = mockMvc.perform(put("/api/v1/products/ ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productToUpdateAsString))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		assertNotNull(result);
		assertEquals(EMPTY_STRING, getContentAsString(result, "updateProductByIdNotId"));
	}
	
	/**
	 * se intenta actulizar un producto pero con id como undefined
	 * @throws Exception
	 */
	@Test
	public void updateProductByIdUndefined() throws Exception {
		var productToUpdate = MocksFactory.createDtoProduct();
		
		var productToUpdateAsString = objectMapper.writeValueAsString(productToUpdate);
		
		var result = mockMvc.perform(put("/api/v1/products/undefined")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productToUpdateAsString))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		assertNotNull(result);
		assertEquals(EMPTY_STRING, getContentAsString(result, "updateProductByIdUndefined"));
	}
	
	/**
	 * Se intenta actualizar un producto sin cuerpo de peticion
	 * @throws Exception
	 */
	@Test
	public void updateProductByIdNotBody() throws Exception {
		
		var productToUpdateAsString = objectMapper.writeValueAsString(null);
		
		var result = mockMvc.perform(put("/api/v1/products/ ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productToUpdateAsString))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		assertNotNull(result);
		assertEquals(EMPTY_STRING, getContentAsString(result, "updateProductByIdNotBody"));
	}
	
	private String getContentAsString(MvcResult result, String fromMethod) throws UnsupportedEncodingException {
		if (result == null) {
			throw new UnsupportedEncodingException("[" + fromMethod + "]result is null");
		}
		var contentString = result.getResponse().getContentAsString();
		if (StringUtils.isBlank(contentString)) {
			System.err.println("[" + fromMethod + "] contentString is empty");
			return EMPTY_STRING;
		}
		System.out.println("[" + fromMethod + "] " + contentString);
		return contentString;
	}
}
