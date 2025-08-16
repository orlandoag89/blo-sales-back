package com.blo.sales.facade.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.blo.sales.business.dto.DtoIntSalesDetailReport;
import com.blo.sales.facade.dto.DtoSaleDetailReport;
import com.blo.sales.facade.dto.DtoSalesDetailReport;
import com.blo.sales.utils.IToOuter;

@Component
public class DtoSalesDetailReportMapper implements IToOuter<DtoIntSalesDetailReport, DtoSalesDetailReport> {

	@Autowired
	private DtoSaleDetailReportMapper mapper;
	
	@Override
	public DtoSalesDetailReport toOuter(DtoIntSalesDetailReport inner) {
		if (inner == null) {
			return null;
		}
		var out = new DtoSalesDetailReport();
		List<DtoSaleDetailReport> sales = new ArrayList<>();
		
		if (inner.getSales() != null && !inner.getSales().isEmpty()) {
			inner.getSales().forEach(s -> sales.add(mapper.toOuter(s)));
		}
		
		out.setSales(sales);
		return out;
	}

}
