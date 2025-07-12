package com.blo.sales.dao.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blo.sales.dao.docs.Product;

@Repository
public interface ProductsRepository extends MongoRepository<Product, String> {
	
	@Query("{ 'name': ?0 }")
	Optional<Product> findProductByName(String name);

}
