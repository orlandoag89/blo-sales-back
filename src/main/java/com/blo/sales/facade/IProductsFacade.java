package com.blo.sales.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.blo.sales.facade.dto.DtoProduct;
import com.blo.sales.facade.dto.DtoProducts;
import com.blo.sales.facade.dto.commons.DtoCommonWrapper;

@RequestMapping("/api/v1/products")
public interface IProductsFacade {

    @PostMapping()
    ResponseEntity<DtoCommonWrapper<DtoProducts>> addProduct(@RequestBody DtoProducts prudct);
    
    @GetMapping
    ResponseEntity<DtoCommonWrapper<DtoProducts>> retrieveAllProducts();
    
    @GetMapping("/{productId}")
    ResponseEntity<DtoCommonWrapper<DtoProduct>> retrieveProduct(@PathVariable String productId);
    
    @PutMapping("/{productId}")
    ResponseEntity<DtoCommonWrapper<DtoProduct>> updateProduct(@PathVariable String productId, @RequestBody DtoProduct product);
}
