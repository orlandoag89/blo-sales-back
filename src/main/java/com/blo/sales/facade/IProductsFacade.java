package com.blo.sales.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blo.sales.facade.dto.DtoProducts;

@RequestMapping("/api/v1/products")
public interface IProductsFacade {

    @PostMapping()
    ResponseEntity<DtoProducts> addProduct(@RequestBody DtoProducts prudct);
}
