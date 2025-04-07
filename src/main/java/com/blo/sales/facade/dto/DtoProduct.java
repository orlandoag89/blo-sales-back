package com.blo.sales.facade.dto;

import lombok.Data;

import java.math.BigDecimal;

public @Data class DtoProduct {

    private String id;

    private String name;

    private BigDecimal total_price;

    private int quantity;

    private String desc;

    private  boolean its_kg;
}
