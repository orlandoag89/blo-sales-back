package com.blo.sales.dao.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.dao.IUsersDao;
import com.blo.sales.dao.docs.Usuario;
import com.blo.sales.dao.mapper.UsuarioMapper;
import com.blo.sales.dao.repository.UsuariosRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.utils.JwtUtil;
import com.blo.sales.utils.PasswordUtil;

@Service
public class UsersDaoImpl implements IUsersDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersDaoImpl.class);
	
	private static final String RESTART_ACTIVED = "_RESTART:ACTIVED";
	
	private static final String RESTART_DISACTIVED = "_RESTART:DISACTIVED";
	
	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private UsuariosRepository repository;
	
	@Value("${exceptions.codes.login}")
	private String exceptionsCodesLogin;
	
	@Value("${exceptions.messages.login}")
	private String exceptionsMessagesLogin;
	
	@Value("${exceptions.codes.login-init}")
	private String exceptionsCodesLoginInit;
	
	@Value("${exceptions.messages.login-init}")
	private String exceptionsMessagesLoginInit;
	
	@Value("${exceptions.codes.password-restart-pending}")
	private String exceptionsCodesPasswordRestartPending;
	
	@Value("${exceptions.messages.password-restart-pending}")
	private String exceptionsMessagesPasswordRestartPending;
	
	@Value("${exceptions.codes.passwords-no-equals}")
	private String exceptionsCodesPasswordsNoEquals;
	
	@Value("${exceptions.messages.passwords-no-equals}")
	private String exceptionsMessagesPasswordsNoEquals;
	
	@Value("${exceptions.codes.password-already-use}")
	private String exceptionsCodesPasswordAlreadyUse;
	
	@Value("${exceptions.messages.password-already-use}")
	private String exceptionsMessagesPasswordAlreadyUse;
	
	@Override
	public DtoIntUserToken register(DtoIntUser userData) throws BloSalesBusinessException {
		LOGGER.info(String.format("registrando usuario %s", Encode.forJava(userData.getUsername())));
		userData.setPassword(PasswordUtil.hashPassword(userData.getPassword()));
		var usuarioDoc = usuarioMapper.toInner(userData);
		LOGGER.info("cifrando password");
		var usuarioSaved = repository.save(usuarioDoc);
		LOGGER.info(String.format("usuario guardado %s", usuarioSaved.getId()));
		
		// generar token
		var token = JwtUtil.generateTokenConClaims(usuarioSaved.getUsername(), usuarioSaved.getRol().name());
		LOGGER.info(String.format("jwt %s", token));
		return new DtoIntUserToken(token);
	}

	@Override
	public DtoIntUserToken login(DtoIntUser user) throws BloSalesBusinessException {
		LOGGER.info("login dao flow init");
		var userDb = getUserByName(user.getUsername());
		// validar que la contraseña no sea temporal
		if (userDb.getPassword().endsWith(RESTART_ACTIVED)) {
			LOGGER.error("El usuario tiene una actualizacion pendiente");
			throw new BloSalesBusinessException(exceptionsMessagesPasswordRestartPending, exceptionsCodesPasswordRestartPending, HttpStatus.BAD_REQUEST);
		}
		//realiza login
		if (!PasswordUtil.checkPassword(user.getPassword(), userDb.getPassword())) {
			LOGGER.error("Error en la password");
			throw new BloSalesBusinessException(exceptionsMessagesLoginInit, exceptionsCodesLoginInit, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		LOGGER.info("login exitoso");
		var token = JwtUtil.generateTokenConClaims(userDb.getUsername(), userDb.getRole().name());
		var out = new DtoIntUserToken(token);
		LOGGER.info(String.format("token generado %s", String.valueOf(out)));
		return out;
	}

	@Override
	public DtoIntUser getUserByName(String username) throws BloSalesBusinessException {
		LOGGER.info(String.format("buscando usuario o excepcion %s", Encode.forJava(username)));
		var userFound = getUserOrNullByName(username);
		if (userFound == null) {
			LOGGER.error(String.format("usuario %s no fue encontrado", Encode.forJava(username)));
			throw new BloSalesBusinessException(exceptionsMessagesLogin, exceptionsCodesLogin, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		LOGGER.info(String.format("usuario encontrado", userFound.getUsername()));
		return userFound;
	}

	@Override
	public DtoIntUser getUserOrNullByName(String username) throws BloSalesBusinessException {
		LOGGER.info(String.format("buscando usuario por nombre o null %s", Encode.forJava(username)));
		var userFound = repository.findByUsername(username);
		if (!userFound.isPresent()) {
			LOGGER.info(String.format("usuario %s no fue encontrado", Encode.forJava(username)));
			return null;
		}
		var toOut = usuarioMapper.toOuter(userFound.get());
		LOGGER.info(String.format("usuario encontrado", toOut.getUsername()));
		return toOut;
	}

	@Override
	public DtoIntUser registerTemporaryPassword(DtoIntUser userData) throws BloSalesBusinessException {
		var userDbInfo = getUserById(userData.getId());
		var lastIndex = userDbInfo.getPassword().size() - 1;
		var lastPassword = userDbInfo.getPassword().get(lastIndex);
		/**
		 * si la ultima contraseña tiene un proceso de reseteo:
		 * 	se va a desactivar y actualizar ese proceso
		 * se va a abrir otro proceso de reseteo y se guardara como ultima contraseña
		 */
		if (lastPassword.endsWith(RESTART_ACTIVED)) {
			lastPassword = lastPassword.substring(0, lastPassword.length() - RESTART_ACTIVED.length());
			lastPassword = lastPassword + RESTART_DISACTIVED;
			userDbInfo.getPassword().set(lastIndex, lastPassword);
			repository.save(userDbInfo);
			LOGGER.info("Password guardada actualizada");
		}
		var passwordToSave = PasswordUtil.hashPassword(userData.getPassword()) + RESTART_ACTIVED;
		userDbInfo.getPassword().add(passwordToSave);
		var userSaved = repository.save(userDbInfo);
		LOGGER.info("el usuario fue actualizado");
		var userToOuter = usuarioMapper.toOuter(userSaved);
		return userToOuter;
	}

	@Override
	public DtoIntUser updatePassword(DtoIntUser userData) throws BloSalesBusinessException {
		var userDbInfo = getUserById(userData.getId());
		var lastIndex = userDbInfo.getPassword().size() - 1;
		var lastPassword = userDbInfo.getPassword().get(lastIndex);
		var fluxResetActived = lastPassword.endsWith(RESTART_ACTIVED);
		
		// si es una actualizacion por reseteo
		if (fluxResetActived) {
			// eliminar subfijo reseteo activado
			lastPassword = lastPassword.substring(0, lastPassword.length() - RESTART_ACTIVED.length());
		}
		
		// validacion que la ultima contraseña sea igual a la que llega en el dto
		var isValid = PasswordUtil.checkPassword(userData.getOld_password(), lastPassword);
		if (!isValid) {
			// error que la contraseña no es igual a la ultima
			LOGGER.error("las contraseñas no son iguales");
			throw new BloSalesBusinessException(exceptionsMessagesPasswordsNoEquals, exceptionsCodesPasswordsNoEquals, HttpStatus.BAD_REQUEST);
		}
		
		// valida que la contraseña actual no exista en el historial
		var passwordHistoryFound = userDbInfo.getPassword().stream().filter(s -> PasswordUtil.checkPassword(userData.getPassword(), s)).findFirst();
		LOGGER.info(String.format("contrasenia encontrada en el historial %s", passwordHistoryFound.isPresent()));
		if (passwordHistoryFound.isPresent()) {
			// error porque ya existe esa contraseña guardada
			LOGGER.error("Esta contraseña ya fue usada");
			throw new BloSalesBusinessException(exceptionsMessagesPasswordAlreadyUse, exceptionsCodesPasswordAlreadyUse, HttpStatus.BAD_REQUEST);
		}
		// guarda la contraseña
		userDbInfo.getPassword().add(PasswordUtil.hashPassword(userData.getPassword()));
		
		// si es una actualizacion por reseteo
		if (fluxResetActived) {
			// actualiza la ultima contraseña con el subfijo para desactivar reseteo
			lastPassword = lastPassword + RESTART_DISACTIVED;
			userDbInfo.getPassword().set(lastIndex, lastPassword);
		}
		
		// guarda contraseña
		var saved = repository.save(userDbInfo);
		LOGGER.info("Cambio de contrasenia exitoso");
		return usuarioMapper.toOuter(saved);
	}
	
	/**
	 * recupera un usuario porid
	 * @param id
	 * @return
	 * @throws BloSalesBusinessException
	 */
	private Usuario getUserById(String id) throws BloSalesBusinessException {
		var userFound = repository.findById(id);
		if (!userFound.isPresent()) {
			LOGGER.error(String.format("usuario %s no fue encontrado", Encode.forJava(id)));
			throw new BloSalesBusinessException(exceptionsMessagesLogin, exceptionsCodesLogin, HttpStatus.UNPROCESSABLE_ENTITY);
		}
		LOGGER.info(String.format("Usuario encontrado %s", Encode.forJava(id)));
		return userFound.get();
	}
	
}
