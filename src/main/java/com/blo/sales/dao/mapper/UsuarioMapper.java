package com.blo.sales.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.enums.RolesIntEnum;
import com.blo.sales.dao.docs.Usuario;
import com.blo.sales.dao.enums.DocRolesEnum;
import com.blo.sales.utils.IToInner;
import com.blo.sales.utils.IToOuter;

@Component
public class UsuarioMapper implements IToInner<Usuario, DtoIntUser>, IToOuter<Usuario, DtoIntUser> {
	
	@Override
	public Usuario toInner(DtoIntUser outer) {
		if (outer == null) {
			return null;
		}
		var inner = new Usuario();
		
		List<String> passwords = new ArrayList<>();
		passwords.add(outer.getPassword());
		inner.setPassword(passwords);
		
		inner.setRol(DocRolesEnum.valueOf(outer.getRole().name()));
		inner.setUsername(outer.getUsername());
		
		return inner;
	}

	@Override
	public DtoIntUser toOuter(Usuario inner) {
		if (inner == null) {
			return null;
		}
		var outer = new DtoIntUser();
		
		outer.setId(inner.getId());
		outer.setRole(RolesIntEnum.valueOf(inner.getRol().name()));
		outer.setUsername(inner.getUsername());
		
		var lastPassword = inner.getPassword().get(inner.getPassword().size() - 1);
		outer.setPassword(lastPassword);
		
		return outer;
	}

}
