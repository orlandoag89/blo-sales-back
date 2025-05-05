package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.dao.docs.Usuario;
import com.blo.sales.dao.enums.DocRolesEnum;
import com.blo.sales.utils.IToInner;

@Component
public class UsuarioMapper implements IToInner<Usuario, DtoIntUser> {
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Usuario toInner(DtoIntUser outer) {
		if (outer == null) {
			return null;
		}
		var inner = modelMapper.map(outer, Usuario.class);
		inner.setRol(DocRolesEnum.valueOf(outer.getRole().name()));
		return inner;
	}

}
