package com.blo.sales.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blo.sales.dao.docs.Cashbox;
import com.blo.sales.dao.docs.Cashboxes;

@Repository
public interface CashboxesRepository extends MongoRepository<Cashbox, String> {
	
	@Query("{ 'status': ?0 }")
	Cashboxes findCashboxByStatus(String status);

}
