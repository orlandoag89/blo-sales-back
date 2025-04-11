package com.blo.sales.business.impl;

import java.math.BigDecimal;

import org.modelmapper.ModelMapper;
import org.owasp.encoder.Encode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.blo.sales.business.ICashboxBusiness;
import com.blo.sales.business.ISalesBusiness;
import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.dao.ICashboxesDao;
import com.blo.sales.exceptions.BloSalesBusinessException;
import com.blo.sales.utils.Utils;

@Service
public class CashboxBusinessImpl implements ICashboxBusiness {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CashboxBusinessImpl.class);
	
	@Autowired
	private ICashboxesDao dao;
	
	@Autowired
	private ISalesBusiness sales;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Value("${exceptions.messages.cashbox-not-exists}")
	private String cashboxDoesNotExistsCode;
	
	@Value("${exceptions.codes.cashbox-not-exists}")
	private String cashboxDoesNotExistsMessage;

	/** se agrega una nueva caja */
	@Override
	public DtoIntCashbox saveCashbox(DtoIntCashbox cashbox) {
		LOGGER.info(String.format("cashbox data %s", Encode.forJava(String.valueOf(cashbox))));
		cashbox.setStatus(StatusCashboxIntEnum.valueOf(StatusCashboxIntEnum.OPEN.name()));
		var saved = dao.addCashbox(cashbox);
		var out = modelMapper.map(saved, DtoIntCashbox.class);
		LOGGER.info(String.format("cashbox saved %s", Encode.forJava(String.valueOf(out))));
		return out;
	}

	@Override
	public DtoIntCashbox getCashboxOpen() {
		LOGGER.info("retrieving cashbox open");
		return dao.getCashboxOpen();
	}

	@Override
	public DtoIntCashbox updateCashbox(String id, DtoIntCashbox cashbox) throws BloSalesBusinessException {
		LOGGER.info(String.format("updating cashbox %s", String.valueOf(cashbox)));
		return dao.updateCashbox(id, cashbox);
	}

	@Override
	public DtoIntCashbox closeCashbox() throws BloSalesBusinessException {
		/** recupera una caja abierta por el abono de un deudor */
		var openCashbox = getCashboxOpen();
		LOGGER.info(String.format("open cashbox %s", String.valueOf(openCashbox)));
		
		/** si no existe una caja entonces la crea */
		var isNewCashbox = openCashbox == null;
		if (isNewCashbox) {
			openCashbox = new DtoIntCashbox();
			openCashbox.setMoney(BigDecimal.ZERO);
			openCashbox.setDate(Utils.getTimeNow());
		}
		
		var totalSalesOnDay = BigDecimal.ZERO;
		var salesNotCashbox = sales.getSalesNotCashbox().getSales();
		LOGGER.info(String.format("sales no cashbox %s", String.valueOf(salesNotCashbox)));
		
		for(var sale: salesNotCashbox) {
			totalSalesOnDay = totalSalesOnDay.add(sale.getTotal());
			sale.set_on_cashbox(true);
			sales.updateSale(sale.getId(), sale);
		}
		LOGGER.info(String.format("total from sales %s", totalSalesOnDay));
		
		var allTotal = totalSalesOnDay.add(openCashbox.getMoney());
		openCashbox.setMoney(allTotal);
		openCashbox.setStatus(StatusCashboxIntEnum.CLOSE);
		
		if (isNewCashbox) {
			var outCashbox = saveCashbox(openCashbox);
			LOGGER.info(String.format("cashbox has been created %s", String.valueOf(outCashbox)));
			return outCashbox;
		}
		
		var cashboxUpdated = dao.updateCashbox(openCashbox.getId(), openCashbox);
		LOGGER.info(String.format("cashbox has been updated %s", String.valueOf(cashboxUpdated)));
		return cashboxUpdated;
	}

	@Override
	public DtoIntCashboxes getAllCashboxes() {
		LOGGER.info("getting all cashboxes");
		return dao.getAllCashboxes();
	}

}
