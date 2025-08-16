package com.blo.sales.facade.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSaleDetailReport;
import com.blo.sales.facade.dto.DtoSaleDetailReport;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoSaleDetailReportMapper implements IToOuter<DtoIntSaleDetailReport, DtoSaleDetailReport> {

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public DtoSaleDetailReport toOuter(DtoIntSaleDetailReport inner) {
		if (inner == null) {
			return null;
		}
		return mapper.map(inner, DtoSaleDetailReport.class);
	}

}
