package com.blo.sales.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blo.sales.dao.docs.Cashbox;

@Repository
public interface CashboxesRepository extends MongoRepository<Cashbox, String> {
	
	@Query("{ 'status': ?0 }")
	List<Cashbox> findCashboxByStatus(String status);

}
