package com.blo.sales.facade.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.IUsersBusiness;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.IUsersFacade;
import com.blo.sales.facade.dto.DtoUser;
import com.blo.sales.facade.dto.DtoUserToken;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.dto.commons.DtoError;
import com.blo.sales.facade.enums.RolesEnum;
import com.blo.sales.facade.mapper.DtoUserMapper;
import com.blo.sales.facade.mapper.DtoUserTokenMapper;
import com.blo.sales.utils.PasswordUtil;

@RestController
public class UsersFacadeImpl implements IUsersFacade {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersFacadeImpl.class);

	@Autowired
	private DtoUserMapper userMapper;

	@Autowired
	private DtoUserTokenMapper userTokenMapper;
	@Autowired
	private IUsersBusiness business;

	@Value("${excpetions.codes.user-exists}")
	private String exceptionsCodesUserExists;

	@Value("${excpetions.messages.user-exists}")
	private String exceptionsMessagesUserExists;
	
	@Value("${exceptions.codes.field-blank}")
	private String exceptionsCodesFieldBlank;
	
	@Value("${exceptions.messages.field-blank}")
	private String exceptionsMessagessFieldBlank;
	
	@Value("${exceptions.codes.password-not-equals}")
	private String exceptionsCodesPasswordNotEquals;
	
	@Value("${exceptions.messages.password-not-equals}")
	private String exceptionsMessagesPasswordNotEquals;
	
	@Value("${exceptions.codes.rol-username}")
	private String exceptionsCodesRolUsername;
	
	@Value("${exceptions.messages.rol-username}")
	private String exceptionsMessagesRolUsername;
	
	@Value("${exceptions.codes.user-not-found}")
	private String exceptionsCodesUserNotFound;
	
	@Value("${exceptions.messages.user-not-found}")
	private String exceptionsMessagesUserNotFound;
	
	private static final String ROOT_USER = "root";

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoUserToken>> login(DtoUser user) {
		var body = new DtoCommonWrapper<DtoUserToken>();
		try {
			// valida existencia de usuario
			LOGGER.info(String.format("buscando al usuario: %s", Encode.forJava(user.getUsername())));
			puttingIdToUser(user);
			var innerUser = userMapper.toInner(user);
			LOGGER.info("usuario encontrado");
			
			var token = business.login(innerUser);
			var tokenToBody = userTokenMapper.toOuter(token);
			LOGGER.info(String.format("token info %s", String.valueOf(tokenToBody)));
			body.setData(tokenToBody);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(String.format("Error %s", e.getMessage()));
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			body.setError(error);
			return new ResponseEntity<>(body, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoUserToken>> registerUser(DtoUser user) {
		var body = new DtoCommonWrapper<DtoUserToken>();
		try {
			//valida el nombre de usuario
			if (
					user.getUsername().trim().isBlank() ||
					user.getPassword_confirm().trim().isBlank() ||
					user.getPassword().trim().isBlank()
				) {
				LOGGER.error("Algun campo esta vacio");
				throw new BloSalesBusinessException(exceptionsCodesFieldBlank, exceptionsMessagessFieldBlank, HttpStatus.BAD_REQUEST);
			}
			
			if (!user.getPassword().trim().equals(user.getPassword_confirm().trim())) {
				LOGGER.error("La contrasenia no es igual");
				throw new BloSalesBusinessException(exceptionsCodesPasswordNotEquals, exceptionsMessagesPasswordNotEquals, HttpStatus.BAD_REQUEST);
			}
			
			if (
					user.getUsername().equals(ROOT_USER) && !user.getRole().name().equals(RolesEnum.ROOT.name()) ||
					!user.getUsername().equals(ROOT_USER) && user.getRole().name().equals(RolesEnum.ROOT.name())
				) {
				LOGGER.error("el rol y nombre de usuario no corresponden");
				throw new BloSalesBusinessException(exceptionsCodesRolUsername, exceptionsMessagesRolUsername, HttpStatus.BAD_REQUEST);
			}
			
			LOGGER.info("validando existencia de usuario");
			var userFound = existsUserByUserName(user.getUsername());
			if (userFound) {
				LOGGER.error(String.format("%s ya esta registrado", user.getUsername()));
				throw new BloSalesBusinessException(exceptionsMessagesUserExists, exceptionsCodesUserExists, HttpStatus.UNPROCESSABLE_ENTITY);
			}
			
			var innerUser = userMapper.toInner(user);			
			LOGGER.info(String.format("registrando usuario %s", Encode.forJava(user.getUsername())));
			var userReg = business.register(innerUser);
			var toBody = userTokenMapper.toOuter(userReg);
			LOGGER.info(String.format("datos de usuario %s", String.valueOf(toBody)));
			body.setData(toBody);
			return new ResponseEntity<>(body, HttpStatus.CREATED);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			var error = new DtoError(e.getErrorCode(), e.getExceptionMessage());
			body.setError(error);
			return new ResponseEntity<>(body, e.getExceptionHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoUser>> createTemporaryPassword(DtoUser rootDataUser, String username) {
		var body = new DtoCommonWrapper<DtoUser>();
		try {
			if (!rootDataUser.getUsername().equals(ROOT_USER)) {
				LOGGER.error("Solo el usuario root debe actualizar la contrasenia");
				throw new BloSalesBusinessException(exceptionsMessagesUserNotFound, exceptionsCodesUserNotFound, HttpStatus.NOT_FOUND);
			}
			puttingIdToUser(rootDataUser);
			business.login(userMapper.toInner(rootDataUser));
			
			var userFound = business.getUserByName(username);
			var passwordTmp = PasswordUtil.generateAlphanumeric(8);
			// actualiza la contraseña actual
			userFound.setPassword(passwordTmp);
			var userUpdate = business.registerTemporaryPassword(userFound);
			userUpdate.setPassword(passwordTmp);
			LOGGER.info(String.format("el usuario %s fue actualizado", userUpdate.getUsername()));
			var toBody = userMapper.toOuter(userUpdate);
			body.setData(toBody);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getMessage());
			body.setError(new DtoError(e.getErrorCode(), e.getExceptionMessage()));
			return new ResponseEntity<>(body, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCommonWrapper<DtoUser>> changePassword(DtoUser userData) {
		var body = new DtoCommonWrapper<DtoUser>();
		try {
			// valida contraseñas
			if (!userData.getPassword().equals(userData.getPassword_confirm())) {
				LOGGER.error("las contraseñas no son iguales");
				throw new BloSalesBusinessException(exceptionsMessagesPasswordNotEquals, exceptionsCodesPasswordNotEquals, HttpStatus.BAD_REQUEST);
			}
			
			// valida existencia de usuario
			puttingIdToUser(userData);
			
			var userDataInner = userMapper.toInner(userData);
			LOGGER.info(String.format("actualizando usuario %s", Encode.forJava(userDataInner.getUsername())));
			var userInfoUpdated = business.updatePassword(userDataInner);
			var toBody = userMapper.toOuter(userInfoUpdated);
			LOGGER.info("Usuario actualizado");
			body.setData(toBody);
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(String.format("Excepcion: %s", e));
			body.setError(new DtoError(e.getErrorCode(), e.getExceptionMessage()));
			return new ResponseEntity<>(body, e.getExceptHttpStatus());
		}
	}

	/**
	 * return true si existe un usuario con ese nombre
	 */
	private boolean existsUserByUserName(String username) throws BloSalesBusinessException {
		return business.getUserOrNullByName(username) != null;
	}
	
	private void puttingIdToUser(DtoUser userData) throws BloSalesBusinessException {
		LOGGER.info(String.format("buscando al usuario: %s", Encode.forJava(userData.getUsername())));
		var userByName = business.getUserByName(userData.getUsername());
		// setea el id
		userData.setId(userByName.getId());
	}
}
