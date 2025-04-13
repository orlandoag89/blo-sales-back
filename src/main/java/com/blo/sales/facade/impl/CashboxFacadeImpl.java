package com.blo.sales.facade.impl;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.facade.ICashboxFacade;
import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.facade.dto.DtoCashboxes;
import com.blo.sales.facade.mapper.DtoCashboxMapper;
import com.blo.sales.facade.mapper.DtoCashboxesMapper;
import com.blo.sales.utils.Utils;

@RestController
public class CashboxFacadeImpl implements ICashboxFacade {

	private static final Logger LOGGER = LoggerFactory.getLogger(CashboxFacadeImpl.class);
	
	@Autowired
	private DtoCashboxMapper cashboxMapper;
	
	@Autowired
	private DtoCashboxesMapper cashboxesMapper;
	
	@Autowired
	private ICashboxBusiness business;
	
	@Autowired
	private ISalesBusiness sales;
	
	@Override
	public ResponseEntity<DtoCashbox> closeCashbox() {
		LOGGER.info("closing cashbox");
		
		try {
			var busCashbox = business.getCashboxOpen();
			LOGGER.info(String.format("open cashbox %s", String.valueOf(busCashbox)));
			
			var isNewCashbox = busCashbox == null;
			DtoCashbox openCashbox = null;
			if (isNewCashbox) {
				LOGGER.info("new cashbox");
				openCashbox = new DtoCashbox();
				openCashbox.setDate(Utils.getTimeNow());
				openCashbox.setMoney(BigDecimal.ZERO);
			} else {
				openCashbox = cashboxMapper.toOuter(busCashbox);
			}
			
			var totalSalesOnDay = BigDecimal.ZERO;
			var salesNotCashbox = sales.getSalesNotCashbox().getSales();
			LOGGER.info(String.format("sales not cashbox %s", String.valueOf(salesNotCashbox)));
			
			for (var sale: salesNotCashbox) {
				totalSalesOnDay = totalSalesOnDay.add(sale.getTotal());
				sale.set_on_cashbox(true);
				var saleUpdated = sales.updateSale(sale.getId(), sale);
				LOGGER.info(String.format("sale updated %s", String.valueOf(saleUpdated)));
			}
			
			LOGGER.info(String.format("total from sales %s", totalSalesOnDay));
			var total = totalSalesOnDay.add(openCashbox.getMoney());
			openCashbox.setStatus(StatusCashboxIntEnum.CLOSE);
			openCashbox.setMoney(total);
			LOGGER.info(String.format("open cashbox %s", String.valueOf(openCashbox)));
			
			var innerCashbox = cashboxMapper.toInner(openCashbox);
			if (isNewCashbox) {
				var saved = business.saveCashbox(innerCashbox);
				var out = cashboxMapper.toOuter(saved);
				LOGGER.info(String.format("save format %s", String.valueOf(out)));
				return new ResponseEntity<>(out, HttpStatus.CREATED);
			}
			
			var updated = business.updateCashbox(innerCashbox.getId(), innerCashbox);
			var out =  cashboxMapper.toOuter(updated);
			LOGGER.info(String.format("cashbox has been updated %s", String.valueOf(updated)));
			return new ResponseEntity<>(out, HttpStatus.OK);
		} catch (BloSalesBusinessException e) {
			LOGGER.error(e.getExceptionMessage());
			return new ResponseEntity<>(null, e.getExceptHttpStatus());
		}
	}

	@Override
	public ResponseEntity<DtoCashboxes> getAllCashboxes() {
		var cashboxes = business.getAllCashboxes();
		var out = cashboxesMapper.toOuter(cashboxes);
		LOGGER.info(String.format("cashboxes %s", String.valueOf(out)));
		return new ResponseEntity<DtoCashboxes>(out, HttpStatus.OK);
	}

}
