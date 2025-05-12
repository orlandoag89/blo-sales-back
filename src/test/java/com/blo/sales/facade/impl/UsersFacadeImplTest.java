package com.blo.sales.facade.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.blo.sales.business.IUsersBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.mapper.DtoUserMapper;
import com.blo.sales.facade.mapper.DtoUserTokenMapper;
import com.blo.sales.factory.MocksFactory;
import com.blo.sales.factory.MocksUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(properties = {
	"exceptions.codes.field-blank=ERR013",
	"exceptions.codes.user-not-found=ERR018",
	"exceptions.messages.user-not-found=Usuario no encontrado",
	"exceptions.messages.field-blank=El campo no puede estar vacio"
})
public class UsersFacadeImplTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@MockBean
	private DtoUserMapper userMapper;

	@MockBean
	private DtoUserTokenMapper userTokenMapper;
	
	@MockBean
	private IUsersBusiness business;
	
	@Test
	public void loginSuccessTest() throws JsonProcessingException, Exception {	
		Mockito.when(business.getUserByName(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCommonUser());
		Mockito.when(userMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		Mockito.when(business.login(Mockito.any())).thenReturn(MocksFactory.createDtoIntUserToken());
		Mockito.when(userTokenMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoUserToken());
		
		var result = mockMvc.perform(post("/api/v1/users/actions/login")
			 	.header(MocksUtils.X_TRACKING_ID, "loginSuccessTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(MocksFactory.createDtoCommonUser())))
            .andExpect(status().isOk())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "loginSuccessTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		Mockito.verify(business, Mockito.atLeastOnce()).getUserByName(Mockito.anyString());
		Mockito.verify(userMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).login(Mockito.any());
		Mockito.verify(userTokenMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertNull(obj.getError());
	}
	
	/**
	 * Usuario no encontrado
	 * 
	 * @throws JsonProcessingException
	 * @throws Exception
	 */
	@Test
	public void loginFailTest() throws JsonProcessingException, Exception {	
		Mockito.when(business.getUserByName(Mockito.anyString())).thenThrow(new BloSalesBusinessException("CODE", "MSG", HttpStatus.UNPROCESSABLE_ENTITY));
		
		var result = mockMvc.perform(post("/api/v1/users/actions/login")
			 	.header(MocksUtils.X_TRACKING_ID, "loginFailTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(MocksFactory.createDtoCommonUser())))
            .andExpect(status().isUnprocessableEntity())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "loginFailTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
	
	@Test
	public void registerUserSuccessTest() throws JsonProcessingException, Exception {
		var body = MocksFactory.createDtoCommonUser();
		body.setPassword_confirm(MocksFactory.getAnyString());
		
		Mockito.when(business.getUserOrNullByName(Mockito.anyString())).thenReturn(null);
		Mockito.when(userMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		Mockito.when(business.register(Mockito.any())).thenReturn(MocksFactory.createDtoIntUserToken());
		Mockito.when(userTokenMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoUserToken());
		
		var result = mockMvc.perform(post("/api/v1/users/mgmt/actions/register")
			 	.header(MocksUtils.X_TRACKING_ID, "loginFailTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isCreated())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "registerUserSuccessTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertNull(obj.getError());
	}
	
	@Test
	public void registerUsesNotUsernameTest() throws JsonProcessingException, Exception {
		var body = MocksFactory.createDtoCommonUser();
		body.setUsername("");
		
		var result = mockMvc.perform(post("/api/v1/users/mgmt/actions/register")
			 	.header(MocksUtils.X_TRACKING_ID, "registerUsesNotUsernameTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "registerUsesNotUsernameTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
	
	@Test
	public void registerUsesNotPasswordTest() throws JsonProcessingException, Exception {
		var body = MocksFactory.createDtoCommonUser();
		body.setPassword_confirm(MocksFactory.getAnyString());
		body.setPassword("");
		
		var result = mockMvc.perform(post("/api/v1/users/mgmt/actions/register")
			 	.header(MocksUtils.X_TRACKING_ID, "registerUsesNotUsernameTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "registerUsesNotPasswordTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
	
	@Test
	public void registerUsesNotPasswordConfirmTest() throws JsonProcessingException, Exception {
		var body = MocksFactory.createDtoCommonUser();
		body.setPassword_confirm("");
		
		var result = mockMvc.perform(post("/api/v1/users/mgmt/actions/register")
			 	.header(MocksUtils.X_TRACKING_ID, "registerUsesNotPasswordConfirmTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "registerUsesNotPasswordConfirmTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
	
	@Test
	public void registerUserPasswordsNotEqualsTest() throws JsonProcessingException, Exception {
		var body = MocksFactory.createDtoCommonUser();
		body.setPassword_confirm(MocksFactory.getAnyString() + "ABCD");
		
		var result = mockMvc.perform(post("/api/v1/users/mgmt/actions/register")
			 	.header(MocksUtils.X_TRACKING_ID, "registerUserPasswordsNotEqualsTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "registerUserPasswordsNotEqualsTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
	
	@Test
	public void registerUserAlreadyExitsTest() throws JsonProcessingException, Exception {
		Mockito.when(business.getUserOrNullByName(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var body = MocksFactory.createDtoCommonUser();
		
		var result = mockMvc.perform(post("/api/v1/users/mgmt/actions/register")
			 	.header(MocksUtils.X_TRACKING_ID, "registerUserAlreadyExitsTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isUnprocessableEntity())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "registerUserAlreadyExitsTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
			
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
	
	@Test
	public void createTemporaryPasswordTest() throws JsonProcessingException, Exception {
		Mockito.when(business.login(Mockito.any())).thenReturn(MocksFactory.createDtoIntUserToken());
		Mockito.when(business.getUserByName(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCommonUser());
		Mockito.when(business.registerTemporaryPassword(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		Mockito.when(userMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCommonUser());
		
		var body = MocksFactory.createDtoRootUser();
		
		var result = mockMvc.perform(put("/api/v1/users/mgmt/actions/reset/username")
			 	.header(MocksUtils.X_TRACKING_ID, "createTemporaryPasswordTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isOk())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "createTemporaryPasswordTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUser());
		
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertNull(obj.getError());
	}
	
	@Test
	public void createTemporaryUserNotRootTest() throws JsonProcessingException, Exception {
		var body = MocksFactory.createDtoCommonUser();
		
		var result = mockMvc.perform(put("/api/v1/users/mgmt/actions/reset/username")
			 	.header(MocksUtils.X_TRACKING_ID, "createTemporaryUserNotRoot")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "createTemporaryUserNotRootTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
	
	@Test
	public void createTemporaryEmptyUserTest() throws JsonProcessingException, Exception {
		var body = MocksFactory.createDtoCommonUser();
		
		var result = mockMvc.perform(put("/api/v1/users/mgmt/actions/reset/ ")
			 	.header(MocksUtils.X_TRACKING_ID, "createTemporaryEmptyUserTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "createTemporaryEmptyUserTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
	
	@Test
	public void createTemporaryUndefinedUserTest() throws JsonProcessingException, Exception {
		var body = MocksFactory.createDtoCommonUser();
		
		var result = mockMvc.perform(put("/api/v1/users/mgmt/actions/reset/undefined")
			 	.header(MocksUtils.X_TRACKING_ID, "createTemporaryUndefinedUserTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "createTemporaryUndefinedUserTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}

	@Test
	public void changePasswordTest() throws JsonProcessingException, Exception {
		Mockito.when(business.getUserByName(Mockito.anyString())).thenReturn(MocksFactory.createDtoIntCommonUser());
		Mockito.when(userMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		Mockito.when(business.updatePassword(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		Mockito.when(userMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoCommonUser());
		
		var body = MocksFactory.createDtoCommonUser();
		
		var result = mockMvc.perform(put("/api/v1/users/actions/change-password")
			 	.header(MocksUtils.X_TRACKING_ID, "changePasswordTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isOk())
            .andReturn();
		
		Mockito.verify(business, Mockito.atLeastOnce()).getUserByName(Mockito.anyString());
		Mockito.verify(userMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(business, Mockito.atLeastOnce()).updatePassword(Mockito.any());
		Mockito.verify(userMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		var data = MocksUtils.getContentAsString(result, "changePasswordTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUser());
		
		assertNotNull(obj);
		assertNotNull(obj.getData());
		assertNull(obj.getError());
	}
	
	@Test
	public void changePasswordWhenPasswordsNotEqualsTest() throws JsonProcessingException, Exception {		
		var body = MocksFactory.createDtoCommonUser();
		body.setPassword(MocksFactory.getAnyString() + "ABCD");
		
		var result = mockMvc.perform(put("/api/v1/users/actions/change-password")
			 	.header(MocksUtils.X_TRACKING_ID, "changePasswordTest")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(body)))
            .andExpect(status().isBadRequest())
            .andReturn();
		
		var data = MocksUtils.getContentAsString(result, "changePasswordWhenPasswordsNotEqualsTest");
		var obj = MocksUtils.parserToCommonWrapper(data, MocksFactory.getReferenceFromDtoUserToken());
		
		assertNotNull(obj);
		assertNull(obj.getData());
		assertNotNull(obj.getError());
	}
}
