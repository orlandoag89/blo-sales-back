package com.blo.sales.dao.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blo.sales.dao.docs.Credit;

@Repository
public interface CreditsRepository extends MongoRepository<Credit, String> {

	@Query("{ 'status_credit': ?0 }")
	List<Credit> retrieveCreditsBy(String status);
}
