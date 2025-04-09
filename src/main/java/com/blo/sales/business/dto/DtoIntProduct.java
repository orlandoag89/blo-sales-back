package com.blo.sales.business.dto;

import lombok.Data;

import java.math.BigDecimal;

public @Data class DtoIntProduct {

    private String id;

    private String name;

    private BigDecimal total_price;

    private int quantity;

    private String desc;

    private  boolean its_kg;
}
