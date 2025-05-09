package com.blo.sales.dao.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.blo.sales.dao.mapper.UsuarioMapper;
import com.blo.sales.dao.repository.UsuariosRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.factory.MocksFactory;

@ExtendWith(MockitoExtension.class)
public class UsersDaoImplTest {
	
	@InjectMocks
	private UsersDaoImpl dao;
	
	@Mock
	private UsuarioMapper usuarioMapper;

	@Mock
	private UsuariosRepository repository;

	@Test
	public void registerTest() throws BloSalesBusinessException {
		Mockito.when(usuarioMapper.toInner(Mockito.any())).thenReturn(MocksFactory.createNewCommonUsuarioWithoutOpenPasswordOpen());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCommonUsuarioWithoutOpenPasswordOpen());
		
		var saved = dao.register(MocksFactory.createDtoIntCommonUser());
		
		Mockito.verify(usuarioMapper, Mockito.atLeastOnce()).toInner(Mockito.any());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		
		assertNotNull(saved);
		assertNotNull(saved.getToken());
	}
	
	@Test
	public void loginSuccessTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalUsuario());
		
		var login = dao.login(MocksFactory.createDtoIntCommonUser());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		
		assertNotNull(login);
		assertNotNull(login.getToken());
	}
	
	/**
	 * no se encuentra el id
	 * @throws BloSalesBusinessException
	 */
	@Test
	public void loginFailIdNotFoundTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(BloSalesBusinessException.class, () -> dao.login(MocksFactory.createDtoIntCommonUser()));
	}
	
	/**
	 * passwords no coinciden
	 * @throws BloSalesBusinessException
	 */
	@Test
	public void loginFailIdNotPasswordsEqualsTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalUsuario());
		
		var input = MocksFactory.createDtoIntCommonUser();
		input.setPassword("abcd");
		
		assertThrows(BloSalesBusinessException.class, () -> dao.login(input));
	}
	
	/**
	 * proceso de cambio pendiente
	 * @throws BloSalesBusinessException
	 */
	@Test
	public void loginFailIdProcessExistsTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalUsuarioOpenProcess());
		
		assertThrows(BloSalesBusinessException.class, () -> dao.login(MocksFactory.createDtoIntCommonUser()));
	}
	
	@Test
	public void getUserByNameTest() throws BloSalesBusinessException {
		Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(MocksFactory.createOptionalUsuario());
		Mockito.when(usuarioMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var usrFound = dao.getUserByName(MocksFactory.getAnyString());
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findByUsername(Mockito.anyString());
		Mockito.verify(usuarioMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(usrFound);
	}
	
	@Test
	public void getUserByNameNotFoundTest() throws BloSalesBusinessException {
		Mockito.when(repository.findByUsername(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(BloSalesBusinessException.class, () -> dao.getUserByName(MocksFactory.getAnyString()));
	}
	
	@Test
	public void registerTemporaryPasswordTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalUsuario());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCommonUsuarioWithoutOpenPasswordOpen());
		Mockito.when(usuarioMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var out = dao.registerTemporaryPassword(MocksFactory.createDtoIntCommonUser());
		
		assertNotNull(out);
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(usuarioMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
	}
	
	@Test
	public void registerTemporaryPasswordOpenProcessTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalUsuarioOpenProcess());
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCommonUsuarioWithoutOpenPasswordOpen());
		Mockito.when(usuarioMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var out = dao.registerTemporaryPassword(MocksFactory.createDtoIntCommonUser());
		
		assertNotNull(out);
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(usuarioMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
	}
	
	@Test
	public void updatePasswordAlreadyUsedTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalUsuarioOpenProcess());
		
		assertThrows(BloSalesBusinessException.class, () -> dao.updatePassword(MocksFactory.createDtoIntCommonUser()));
	}
	
	@Test
	public void updatePasswordNotEqualsTest() throws BloSalesBusinessException {
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(MocksFactory.createOptionalUsuarioOpenProcess());
		
		var data = MocksFactory.createDtoIntCommonUser();
		data.setOld_password("abcdefg");
		
		assertThrows(BloSalesBusinessException.class, () -> dao.updatePassword(data));
	}
	
	@Test
	public void updatePasswordOpenProcessTest() throws BloSalesBusinessException {
		var PASS = "abc";
		
		var foundId = MocksFactory.createCommonUsuarioWithOpenPasswordOpen();
		foundId.getPassword().get(1).setPassword(PASS);
		
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(foundId));
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCommonUsuarioWithoutOpenPasswordOpen());
		Mockito.when(usuarioMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var userNewInfo = MocksFactory.createDtoIntCommonUser();
		userNewInfo.setOld_password(PASS);
		userNewInfo.setPassword(PASS + PASS);
		
		var out = dao.updatePassword(userNewInfo);
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(usuarioMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
	}
	
	@Test
	public void updatePasswordNoOpenProcessTest() throws BloSalesBusinessException {
		var PASS = "abc";
		
		var foundId = MocksFactory.createCommonUsuarioWithoutOpenPasswordOpen();
		
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(foundId));
		Mockito.when(repository.save(Mockito.any())).thenReturn(MocksFactory.createCommonUsuarioWithoutOpenPasswordOpen());
		Mockito.when(usuarioMapper.toOuter(Mockito.any())).thenReturn(MocksFactory.createDtoIntCommonUser());
		
		var userNewInfo = MocksFactory.createDtoIntCommonUser();
		userNewInfo.setPassword(PASS + PASS);
		
		var out = dao.updatePassword(userNewInfo);
		
		Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyString());
		Mockito.verify(repository, Mockito.atLeastOnce()).save(Mockito.any());
		Mockito.verify(usuarioMapper, Mockito.atLeastOnce()).toOuter(Mockito.any());
		
		assertNotNull(out);
	}
}
