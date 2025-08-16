package com.blo.sales.dao.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSaleDetailReport;
import com.blo.sales.dao.docs.SaleDetailReport;
import com.blo.sales.utils.IToOuter;

@Component
public class SaleDetailReportMapper implements IToOuter<SaleDetailReport, DtoIntSaleDetailReport> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public DtoIntSaleDetailReport toOuter(SaleDetailReport inner) {
		if (inner == null) {
			return null;
		}
		return modelMapper.map(inner, DtoIntSaleDetailReport.class);
	}

}
