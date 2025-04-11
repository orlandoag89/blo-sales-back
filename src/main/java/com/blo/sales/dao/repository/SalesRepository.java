package com.blo.sales.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blo.sales.dao.docs.Sale;

@Repository
public interface SalesRepository extends MongoRepository<Sale, String> {
	
	@Query("{ 'close_sale' : 0 }")
	List<Sale> findSalesNotClosed();

	@Query("{ 'close_sale' : { $ne: 0 } }")
	List<Sale> findSalesClosed();
	
	@Query(" { 'is_on_cashbox': false } ")
	List<Sale> findSaleNoCashbox();
}
