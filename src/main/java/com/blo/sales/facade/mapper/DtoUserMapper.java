package com.blo.sales.facade.mapper;

import com.blo.sales.utils.IToOuter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.enums.RolesIntEnum;
import com.blo.sales.facade.dto.DtoUser;
import com.blo.sales.utils.IToInner;

@Component
public class DtoUserMapper implements IToInner<DtoIntUser, DtoUser>, IToOuter<DtoIntUser, DtoUser> {
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public DtoIntUser toInner(DtoUser outer) {
		if (outer == null) {
			return null;
		}
		var inner = mapper.map(outer, DtoIntUser.class);
		if (outer.getRole() != null) {
			inner.setRole(RolesIntEnum.valueOf(outer.getRole().name()));
		}
		return inner;
	}

	@Override
	public DtoUser toOuter(DtoIntUser inner) {
		if (inner == null) {
			return null;
		}
		return mapper.map(inner, DtoUser.class);
	}
}
