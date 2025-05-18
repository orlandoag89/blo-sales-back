package com.blo.sales.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
import com.blo.sales.business.dto.DtoIntUser;
import com.blo.sales.business.dto.DtoIntUserToken;
import com.blo.sales.business.enums.RolesIntEnum;
import com.blo.sales.business.enums.StatusCashboxIntEnum;
import com.blo.sales.dao.docs.Cashbox;
import com.blo.sales.dao.docs.Cashboxes;
import com.blo.sales.dao.docs.Config;
import com.blo.sales.dao.docs.Contrasenia;
import com.blo.sales.dao.docs.Debtor;
import com.blo.sales.dao.docs.Debtors;
import com.blo.sales.dao.docs.PartialPyment;
import com.blo.sales.dao.docs.Product;
import com.blo.sales.dao.docs.Products;
import com.blo.sales.dao.docs.Sale;
import com.blo.sales.dao.docs.SaleProduct;
import com.blo.sales.dao.docs.Sales;
import com.blo.sales.dao.docs.Usuario;
import com.blo.sales.dao.enums.DocRolesEnum;
import com.blo.sales.dao.enums.DocStatusCashboxEnum;
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
import com.blo.sales.facade.dto.DtoUser;
import com.blo.sales.facade.dto.DtoUserToken;
import com.blo.sales.facade.dto.DtoWrapperSale;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;
import com.blo.sales.facade.enums.RolesEnum;
import com.blo.sales.facade.enums.StatusCashboxEnum;
import com.blo.sales.utils.Utils;
import com.fasterxml.jackson.core.type.TypeReference;

public final class MocksFactory {

	private static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0Iiwicm9sZSI6IkNPTU1PTiIsImlhdCI6MTc0Njc2MTQzMiwiZXhwIjoxNzQ2ODI2MjMyfQ.GFHwBEDt0nemtEeXisQIDy4rw5xd5sY28KDUkDmYDkE";
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
	
	public static Product createProduct() {
		var out = new Product();
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
	
	public static Products createProducts() {
		var out = new Products();
		List<Product> products = new ArrayList<>();
		products.add(createProduct());
		out.setProducts(products);
		return out;
	}
	
	public static DtoProducts createDtoProductsSaved() {
		var out = new DtoProducts();
		List<DtoProduct> products = new ArrayList<>();
		products.add(createDtoProduct());
		products.add(createDtoProduct());
		out.setProducts(products);
		return out;
	}
	
	public static DtoIntProducts createDtoIntProductsSaved() {
		var out = new DtoIntProducts();
		List<DtoIntProduct> products = new ArrayList<>();
		products.add(createDtoIntProduct());
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
	
	public static DtoIntCashbox createDtoIntNewCashbox() {
		var out = new DtoIntCashbox();
		out.setDate(NOW);
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
	
	public static DtoWrapperSale createDtoWrapperSaleWithNewDebtor() {
		var out = new DtoWrapperSale();
		out.setDebtor(createNewDtoDebtor());
		out.setSale(createDtoNewSaleNoCashbox());
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
	
	public static Debtor createExistsDebtor() {
		var out = new Debtor();
		out.setId(ANY_ID);
		out.setName(ANY_NAME);
		out.setOpen_date(NOW);

		List<PartialPyment> partial_pyments = new ArrayList<>();
		partial_pyments.add(createPartialPyment());
		out.setPartial_pyments(partial_pyments);

		List<Sale> products = new ArrayList<>();
		products.add(createSaleNoCashbox());
		products.add(createSaleNoCashbox());
		out.setSales(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}
	
	public static Debtor createNewDebtor() {
		var out = new Debtor();
		out.setName(ANY_NAME);
		out.setOpen_date(NOW);

		List<PartialPyment> partial_pyments = new ArrayList<>();
		partial_pyments.add(createPartialPyment());
		out.setPartial_pyments(partial_pyments);

		List<Sale> products = new ArrayList<>();
		products.add(createSaleNoCashbox());
		products.add(createSaleNoCashbox());
		out.setSales(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}
	
	public static Debtors createDebtors() {
		var out = new Debtors();
		
		List<Debtor> debtors = new ArrayList<>();
		debtors.add(createExistsDebtor());
		out.setDebtors(debtors);
		
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
	
	public static PartialPyment createPartialPyment() {
		var out = new PartialPyment();
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
	
	public static DtoSale createDtoSaleNoCashboxAndOpen() {
		var out = new DtoSale();
		out.set_on_cashbox(false);
		out.setClose_sale(0L);
		out.setOpen_date(NOW);

		List<DtoSaleProduct> products = new ArrayList<>();
		products.add(createDtoSaleProduct());
		out.setProducts(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}
	
	public static DtoIntSale createDtoIntSaleNoCashboxAndOpen() {
		var out = new DtoIntSale();
		out.set_on_cashbox(false);
		out.setClose_sale(0L);
		out.setOpen_date(NOW);

		List<DtoIntSaleProduct> products = new ArrayList<>();
		products.add(createDtoIntSaleProduct());
		out.setProducts(products);

		out.setTotal(BIG_DECIMAL_50);
		return out;
	}
	
	public static DtoSale createDtoNewSaleNoCashbox() {
		var out = new DtoSale();
		out.set_on_cashbox(false);
		out.setClose_sale(NOW);
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
	
	public static DtoIntSale createSavedDtoIntSaleNoCashbox() {
		var out = new DtoIntSale();
		out.set_on_cashbox(false);
		out.setId(ANY_ID);
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
	
	public static DtoIntSale createNewDtoIntSaleOnCashbox() {
		var out = new DtoIntSale();
		out.set_on_cashbox(true);
		out.setClose_sale(NOW);
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
	
	public static SaleProduct createSaleProduct() {
		var out = new SaleProduct();
		var product = createProduct();
		out.setDesc(product.getDesc());
		out.setId(product.getId());
		out.setIts_kg(product.isIts_kg());
		out.setName(product.getName());
		out.setQuantity(product.getQuantity());
		out.setTotal_price(product.getTotal_price());
		out.setQuantity_on_sale(BIG_DECIMAL_1);
		return out;
	}
	
	public static Sale createSaleNoCashbox() {
		var out = new Sale();
		out.set_on_cashbox(false);
		out.setClose_sale(NOW);
		out.setId(ANY_ID);
		out.setOpen_date(NOW);
		
		List<SaleProduct> products = new ArrayList<>();
		products.add(createSaleProduct());
		
		out.setProducts(products);
		out.setTotal(BIG_DECIMAL_50);
		return out;
	}
	
	public static Sale createSaleOnCashbox() {
		var out = new Sale();
		out.set_on_cashbox(true);
		out.setClose_sale(NOW);
		out.setId(ANY_ID);
		out.setOpen_date(NOW);
		
		List<SaleProduct> products = new ArrayList<>();
		products.add(createSaleProduct());
		
		out.setProducts(products);
		out.setTotal(BIG_DECIMAL_50);
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
	
	public static Sales createSales() {
		var out = new Sales();

		List<Sale> sales = new ArrayList<>();
		sales.add(createSaleNoCashbox());
		sales.add(createSaleNoCashbox());
		out.setSales(sales);

		return out;
	}
	
	public static DtoIntSales createOpenDtoIntSales() {
		var out = new DtoIntSales();

		List<DtoIntSale> sales = new ArrayList<>();
		
		var item1 = createDtoIntSaleNoCashbox();
		item1.setClose_sale(0L);
		sales.add(item1);
		
		var item2 = createDtoIntSaleNoCashbox();
		item2.setClose_sale(0L);
		sales.add(item2);
		
		out.setSales(sales);

		return out;
	}
	
	public static DtoSales createOpenDtoSales() {
		var out = new DtoSales();

		List<DtoSale> sales = new ArrayList<>();
		
		var item1 = createDtoSaleNoCashbox();
		item1.setClose_sale(0L);
		sales.add(item1);
		
		var item2 = createDtoSaleNoCashbox();
		item2.setClose_sale(0L);
		sales.add(item2);
		
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
	
	public static Cashbox createCloseCashbox() {
		var out = new Cashbox();
		out.setDate(NOW);
		out.setId(ANY_ID);
		out.setMoney(BIG_DECIMAL_50);
		out.setStatus(DocStatusCashboxEnum.CLOSE);
		return out;
	}
	
	public static Cashbox createOpenCashbox() {
		var out = new Cashbox();
		out.setDate(NOW);
		out.setId(ANY_ID);
		out.setMoney(BIG_DECIMAL_50);
		out.setStatus(DocStatusCashboxEnum.OPEN);
		return out;
	}
	
	public static Cashboxes createCashboxes() {
		var out = new Cashboxes();
		
		out.setBoxes(createCashboxesList());
		
		return out;
	}
	
	public static List<Cashbox> createCashboxesList() {
		List<Cashbox> boxes = new ArrayList<>();
		
		boxes.add(createOpenCashbox());
		boxes.add(createCloseCashbox());
		boxes.add(createCloseCashbox());
		return boxes;
	}
	
	public static DtoIntUserToken createDtoIntUserToken() {
		return new DtoIntUserToken(TOKEN);
	}
	
	public static DtoUserToken createDtoUserToken() {
		var out = new DtoUserToken();
		out.setToken(TOKEN);
		return out;
	}
	
	public static DtoIntUser createDtoIntCommonUser() {
		var out = new DtoIntUser();
		out.setId(ANY_ID);
		out.setOld_password(ANY_STRING);
		out.setPassword(ANY_STRING);
		out.setRole(RolesIntEnum.COMMON);
		out.setUsername(ANY_STRING);
		return out;
	}
	
	public static DtoUser createDtoCommonUser() {
		var out = new DtoUser();
		out.setId(ANY_ID);
		out.setOld_password(ANY_STRING);
		out.setPassword(ANY_STRING);
		out.setRole(RolesEnum.COMMON);
		out.setUsername(ANY_STRING);
		out.setPassword_confirm(ANY_STRING);
		return out;
	}
	
	public static DtoIntUser createDtoIntRootUser() {
		var user = createDtoIntCommonUser();
		user.setRole(RolesIntEnum.ROOT);
		return user;
	}
	
	public static DtoUser createDtoRootUser() {
		var user = createDtoCommonUser();
		user.setUsername("root");
		user.setRole(RolesEnum.ROOT);
		return user;
	}
	
	public static Usuario createCommonUsuarioWithOpenPasswordOpen() {
		var out = new Usuario();
		out.setId(ANY_ID);
		out.setRol(DocRolesEnum.COMMON);
		out.setUsername(ANY_STRING);
		
		List<Contrasenia> passwords = new ArrayList<>();
		passwords.add(createContrasenia());
		var itemAux = createContrasenia();
		itemAux.setProcess_reset(true);
		passwords.add(itemAux);
		
		out.setPassword(passwords);
		return out;
	}
	
	public static Usuario createCommonUsuarioWithoutOpenPasswordOpen() {
		var out = createCommonUsuarioWithOpenPasswordOpen();
		out.getPassword().get(1).setProcess_reset(false);
		return out;
	}
	
	public static Usuario createNewCommonUsuarioWithoutOpenPasswordOpen() {
		var out = createCommonUsuarioWithoutOpenPasswordOpen();
		out.setId(null);
		return out;
	}
	
	public static Contrasenia createContrasenia() {
		var out = new Contrasenia();
		out.setCreated_date(NOW);
		out.setPassword(ANY_STRING);
		out.setProcess_reset(false);
		return out;
	}
	
	public static Optional<Config> createConfig() {
		var config = new Config();
		config.setCiphered(false);
		return Optional.of(config);
	}
	
	public static Optional<Usuario> createOptionalUsuario() {
		return Optional.of(createCommonUsuarioWithoutOpenPasswordOpen());
	}
	
	public static Optional<Usuario> createOptionalUsuarioOpenProcess() {
		return Optional.of(createCommonUsuarioWithOpenPasswordOpen());
	}
	
	public static Optional<Cashbox> createOptionalCashbox() {
		return Optional.of(createOpenCashbox());
	}
	
	public static Optional<Debtor> createOptionaDebtor() {
		return Optional.of(createExistsDebtor());
	}
	
	public static Optional<Sale> createOptionalSale() {
		return Optional.of(createSaleNoCashbox());
	}
	
	public static Optional<Product> createOptionalProduct() {
		return Optional.of(createProduct());
	}
	
	public static long getNowDate() {
		return NOW;
	}
	
	public static String getId() {
		return ANY_ID;
	}
	
	public static String getAnyString() {
		return ANY_STRING;
	}
	
	public static TypeReference<DtoCommonWrapper<DtoWrapperSale>> getReferenceFromWrapperSale() {
		return new TypeReference<DtoCommonWrapper<DtoWrapperSale>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoSales>> getReferenceFromDtoSales() {
		return new TypeReference<DtoCommonWrapper<DtoSales>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoSale>> getReferenceFromDtoSale() {
		return new TypeReference<DtoCommonWrapper<DtoSale>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoProducts>> getReferenceFromDtoProducts() {
		return new TypeReference<DtoCommonWrapper<DtoProducts>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoProduct>> getReferenceFromDtoProduct() {
		return new TypeReference<DtoCommonWrapper<DtoProduct>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoDebtor>> getReferenceFromDtoDebtor() {
		return new TypeReference<DtoCommonWrapper<DtoDebtor>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoDebtors>> getReferenceFromDtoDebtors() {
		return new TypeReference<DtoCommonWrapper<DtoDebtors>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoCashbox>> getReferenceFromDtoCashbox() {
		return new TypeReference<DtoCommonWrapper<DtoCashbox>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoCashboxes>> getReferenceFromDtoCashboxes() {
		return new TypeReference<DtoCommonWrapper<DtoCashboxes>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoUserToken>> getReferenceFromDtoUserToken() {
		return new TypeReference<DtoCommonWrapper<DtoUserToken>>() { };
	}
	
	public static TypeReference<DtoCommonWrapper<DtoUser>> getReferenceFromDtoUser() {
		return new TypeReference<DtoCommonWrapper<DtoUser>>() { };
	}
}
