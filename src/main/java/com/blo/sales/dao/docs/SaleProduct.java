package com.blo.sales.dao.docs;

import java.io.Serializable;
import java.math.BigDecimal;

public class SaleProduct extends Product implements Serializable {

	private static final long serialVersionUID = 7872922554036945983L;

	private BigDecimal quantity_on_sale;

	public BigDecimal getQuantity_on_sale() {
		return quantity_on_sale;
	}

	public void setQuantity_on_sale(BigDecimal quantity_on_sale) {
		this.quantity_on_sale = quantity_on_sale;
	}

}
