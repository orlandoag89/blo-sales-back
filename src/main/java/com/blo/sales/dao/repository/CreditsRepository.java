package com.blo.sales.dao.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.blo.sales.dao.docs.Credit;

@Repository
public interface CreditsRepository extends MongoRepository<Credit, String> {

}
