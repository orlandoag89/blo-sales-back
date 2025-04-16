package com.blo.sales.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.blo.sales.business.dto.DtoIntCashbox;
import com.blo.sales.business.dto.DtoIntCashboxes;
import com.blo.sales.business.dto.DtoIntDebtor;
import com.blo.sales.business.dto.DtoIntDebtors;
import com.blo.sales.business.dto.DtoIntPartialPyment;
import com.blo.sales.business.dto.DtoIntProduct;
import com.blo.sales.business.dto.DtoIntProducts;
import com.blo.sales.business.dto.DtoIntSale;
import com.blo.sales.business.dto.DtoIntSaleProduct;
import com.blo.sales.business.dto.DtoIntSales;
import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.facade.dto.DtoCashbox;
import com.blo.sales.facade.dto.DtoCashboxes;
import com.blo.sales.facade.dto.DtoDebtor;
import com.blo.sales.facade.dto.DtoDebtors;
import com.blo.sales.facade.dto.DtoPartialPyment;
import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.facade.dto.DtoProducts;
import com.blo.sales.facade.dto.DtoSale;
import com.blo.sales.facade.dto.DtoSaleProduct;
import com.blo.sales.facade.dto.DtoSales;
import com.blo.sales.facade.enums.StatusCashboxEnum;
import com.blo.sales.utils.Utils;

public final class MocksFactory {

	private static final String ANY_STRING = "any_string";
	private static final String ANY_NAME = "Pepe";
	private static final String ANY_ID = "1a2b3c4d5e";
	private static final BigDecimal BIG_DECIMAL_1 = BigDecimal.ONE;
	private static final BigDecimal BIG_DECIMAL_5 = new BigDecimal("5");
	private static final BigDecimal BIG_DECIMAL_50 = new BigDecimal("50");
	private static final long NOW = Utils.getTimeNow();

	private MocksFactory() {
	}

	public static DtoProduct createDtoProduct() {
		var out = new DtoProduct();
		out.setId(ANY_ID);
		out.setDesc(ANY_STRING);
		out.setIts_kg(false);
		out.setName(ANY_STRING);
		out.setQuantity(BIG_DECIMAL_5);
		out.setTotal_price(BIG_DECIMAL_50);
		return out;
	}

	public static DtoIntProduct createDtoIntProduct() {
		var out = new DtoIntProduct();
		out.setId(ANY_ID);
		out.setDesc(ANY_STRING);
		out.setIts_kg(false);
		out.setName(ANY_STRING);
		out.setQuantity(BIG_DECIMAL_5);
		out.setTotal_price(BIG_DECIMAL_50);
		return out;
	}

	public static DtoProducts createDtoProducts() {
		var out = new DtoProducts();
		List<DtoProduct> products = new ArrayList<>();
		products.add(createDtoProduct());
		out.setProducts(products);
		return out;
	}

	public static DtoProducts createDtoProductsEmptyList() {
		var out = new DtoProducts();
		List<DtoProduct> products = new ArrayList<>();
		out.setProducts(products);
		return out;
	}

	public static DtoProducts createDtoProductsNullList() {
		var out = new DtoProducts();
		out.setProducts(null);
		return out;
	}

	public static DtoIntProducts createDtoIntProducts() {
		var out = new DtoIntProducts();
		List<DtoIntProduct> products = new ArrayList<>();
		products.add(createDtoIntProduct());
		out.setProducts(products);
		return out;
	}

	public static DtoIntProducts createDtoIntProductsEmptyList() {
		var out = new DtoIntProducts();
		List<DtoIntProduct> products = new ArrayList<>();
		out.setProducts(products);
		return out;
	}

	public static DtoIntProducts createDtoIntProductsNullList() {
		var out = new DtoIntProducts();
		out.setProducts(null);
		return out;
	}

	public static DtoCashbox createDtoCashboxOpen() {
		var out = new DtoCashbox();
		out.setDate(NOW);
		out.setId(ANY_ID);
		out.setMoney(BIG_DECIMAL_50);
		out.setStatus(StatusCashboxEnum.OPEN);
		return out;
	}

	public static DtoCashbox createDtoCashboxClose() {
		var out = createDtoCashboxOpen();
		out.setStatus(StatusCashboxEnum.CLOSE);
		return out;
	}

	public static DtoIntCashbox createDtoIntCashboxOpen() {
		var out = new DtoIntCashbox();
		out.setDate(NOW);
		out.setId(ANY_ID);
		out.setMoney(BIG_DECIMAL_50);
		out.setStatus(StatusCashboxIntEnum.OPEN);
		return out;
	}

	public static DtoIntCashbox createDtoIntCashboxClose() {
		var out = createDtoIntCashboxOpen();
		out.setStatus(StatusCashboxIntEnum.CLOSE);
		return out;
	}

	public static DtoCashboxes createDtoCashboxes() {
		var out = new DtoCashboxes();
		List<DtoCashbox> boxes = new ArrayList<>();
		boxes.add(createDtoCashboxOpen());
		boxes.add(createDtoCashboxClose());
		out.setBoxes(boxes);
		return out;
	}

	public static DtoIntCashboxes createDtoIntCashboxes() {
		var out = new DtoIntCashboxes();
		List<DtoIntCashbox> boxes = new ArrayList<>();
		boxes.add(createDtoIntCashboxOpen());
		boxes.add(createDtoIntCashboxClose());
		out.setBoxes(boxes);
		return out;
	}

	public static DtoIntCashboxes createDtoIntCashboxesListEmpty() {
		var out = new DtoIntCashboxes();
		out.setBoxes(new ArrayList<>());
		return out;
	}

	public static DtoIntCashboxes createDtoIntCashboxesListNull() {
		var out = new DtoIntCashboxes();
		out.setBoxes(null);
		return out;
	}

	public static DtoDebtor createNewDtoDebtor() {
		var out = new DtoDebtor();
		out.setName(ANY_NAME);
		out.setOpen_date(NOW);
		out.setPartial_pyments(null);
		out.setSales(null);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}
	
	public static DtoIntDebtor createNewDtoIntDebtor() {
		var out = new DtoIntDebtor();
		out.setName(ANY_NAME);
		out.setOpen_date(NOW);
		out.setPartial_pyments(null);
		out.setSales(null);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}

	public static DtoDebtor createExistsDtoDebtor() {
		var out = new DtoDebtor();
		out.setId(ANY_ID);
		out.setName(ANY_NAME);
		out.setOpen_date(NOW);

		List<DtoPartialPyment> partial_pyments = new ArrayList<>();
		partial_pyments.add(createDtoPartialPyment());
		out.setPartial_pyments(partial_pyments);

		List<DtoSale> products = new ArrayList<>();
		products.add(createDtoSaleNoCashbox());
		products.add(createDtoSaleOnCashbox());
		out.setSales(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}
	
	public static DtoIntDebtor createExistsDtoIntDebtor() {
		var out = new DtoIntDebtor();
		out.setId(ANY_ID);
		out.setName(ANY_NAME);
		out.setOpen_date(NOW);

		List<DtoIntPartialPyment> partial_pyments = new ArrayList<>();
		partial_pyments.add(createDtoIntPartialPyment());
		out.setPartial_pyments(partial_pyments);

		List<DtoIntSale> products = new ArrayList<>();
		products.add(createDtoIntSaleNoCashbox());
		products.add(createDtoIntSaleOnCashbox());
		out.setSales(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}

	public static DtoPartialPyment createDtoPartialPyment() {
		var out = new DtoPartialPyment();
		out.setDate(NOW);
		out.setPartial_pyment(BIG_DECIMAL_5);
		return out;
	}
	
	public static DtoIntPartialPyment createDtoIntPartialPyment() {
		var out = new DtoIntPartialPyment();
		out.setDate(NOW);
		out.setPartial_pyment(BIG_DECIMAL_5);
		return out;
	}

	public static DtoSale createDtoSaleOnCashbox() {
		var out = new DtoSale();
		out.set_on_cashbox(true);
		out.setClose_sale(NOW);
		out.setId(ANY_ID);
		out.setOpen_date(NOW);
		out.setTotal(BIG_DECIMAL_50);

		List<DtoSaleProduct> products = new ArrayList<>();
		products.add(createDtoSaleProduct());

		out.setProducts(products);
		return out;
	}

	public static DtoSale createDtoSaleNoCashbox() {
		var out = new DtoSale();
		out.set_on_cashbox(false);
		out.setClose_sale(NOW);
		out.setId(ANY_ID);
		out.setOpen_date(NOW);

		List<DtoSaleProduct> products = new ArrayList<>();
		products.add(createDtoSaleProduct());
		out.setProducts(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}
	
	public static DtoIntSale createDtoIntSaleNoCashbox() {
		var out = new DtoIntSale();
		out.set_on_cashbox(false);
		out.setClose_sale(NOW);
		out.setId(ANY_ID);
		out.setOpen_date(NOW);

		List<DtoIntSaleProduct> products = new ArrayList<>();
		products.add(createDtoIntSaleProduct());
		out.setProducts(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}

	public static DtoIntSale createDtoIntSaleOnCashbox() {
		var out = new DtoIntSale();
		out.set_on_cashbox(true);
		out.setClose_sale(NOW);
		out.setId(ANY_ID);
		out.setOpen_date(NOW);

		List<DtoIntSaleProduct> products = new ArrayList<>();
		products.add(createDtoIntSaleProduct());
		out.setProducts(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}

	public static DtoSaleProduct createDtoSaleProduct() {
		var out = new DtoSaleProduct();
		var product = createDtoProduct();
		out.setDesc(product.getDesc());
		out.setId(product.getId());
		out.setIts_kg(product.isIts_kg());
		out.setName(product.getName());
		out.setQuantity(product.getQuantity());
		out.setTotal_price(product.getTotal_price());
		out.setQuantity_on_sale(BIG_DECIMAL_1);
		return out;
	}

	public static DtoIntSaleProduct createDtoIntSaleProduct() {
		var out = new DtoIntSaleProduct();
		var product = createDtoIntProduct();
		out.setDesc(product.getDesc());
		out.setId(product.getId());
		out.setIts_kg(product.isIts_kg());
		out.setName(product.getName());
		out.setQuantity(product.getQuantity());
		out.setTotal_price(product.getTotal_price());
		out.setQuantity_on_sale(BIG_DECIMAL_1);
		return out;
	}

	public static DtoSales createDtoSales() {
		var out = new DtoSales();

		List<DtoSale> sales = new ArrayList<>();
		sales.add(createDtoSaleNoCashbox());
		sales.add(createDtoSaleOnCashbox());
		out.setSales(sales);

		return out;
	}
	
	public static DtoIntSales createDtoIntSales() {
		var out = new DtoIntSales();

		List<DtoIntSale> sales = new ArrayList<>();
		sales.add(createDtoIntSaleNoCashbox());
		sales.add(createDtoIntSaleOnCashbox());
		out.setSales(sales);

		return out;
	}
	
	public static DtoIntSales createDtoIntSalesListEmpty() {
		var out = new DtoIntSales();

		out.setSales(new ArrayList<>());

		return out;
	}
	
	public static DtoIntSales createDtoIntSalesListNull() {
		var out = new DtoIntSales();

		out.setSales(null);

		return out;
	}

	public static DtoSales createDtoSalesListEmpty() {
		var out = new DtoSales();
		
		out.setSales(new ArrayList<>());

		return out;
	}
	
	public static DtoSales createDtoSalesListNull() {
		var out = new DtoSales();
		
		out.setSales(null);

		return out;
	}
	
	public static DtoDebtors createDtoDebtors() {
		var out = new DtoDebtors();
		List<DtoDebtor> debtors = new ArrayList<>();
		debtors.add(createExistsDtoDebtor());
		out.setDebtors(debtors);
		return out;
	}
	
	public static DtoIntDebtors createDtoIntDebtors() {
		var out = new DtoIntDebtors();
		List<DtoIntDebtor> debtors = new ArrayList<>();
		debtors.add(createExistsDtoIntDebtor());
		out.setDebtors(debtors);
		return out;
	}
}
