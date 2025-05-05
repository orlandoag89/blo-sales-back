package com.blo.sales.dao.impl;

import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.dao.IUsersDao;
import com.blo.sales.dao.mapper.UsuarioMapper;
import com.blo.sales.dao.repository.UsuariosRepository;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.utils.JwtUtil;
import com.blo.sales.utils.PasswordUtil;

@Service
public class UsersDaoImpl implements IUsersDao {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UsersDaoImpl.class);
	
	@Autowired
	private UsuarioMapper usuarioMapper;

	@Autowired
	private UsuariosRepository repository;
	
	@Override
	public DtoIntUserToken register(DtoIntUser userData) throws BloSalesBusinessException {
		LOGGER.info(String.format("registrando usuario %s", Encode.forJava(userData.getUsername())));
		var usuarioDoc = usuarioMapper.toInner(userData);
		LOGGER.info("cifrando password");
		var passwordHash = PasswordUtil.hashPassword(usuarioDoc.getPassword());
		usuarioDoc.setPassword(passwordHash);
		var usuarioSaved = repository.save(usuarioDoc);
		LOGGER.info(String.format("usuario guardado %s", usuarioSaved.getId()));
		
		// generar token
		var token = JwtUtil.generateTokenConClaims(usuarioSaved.getUsername(), usuarioSaved.getRol().name());
		LOGGER.info(String.format("jwt %s", token));
		return new DtoIntUserToken(token);
	}

}
