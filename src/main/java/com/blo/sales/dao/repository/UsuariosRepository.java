package com.blo.sales.dao.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.blo.sales.dao.docs.Usuario;

@Repository
public interface UsuariosRepository extends MongoRepository<Usuario, String> {

	@Query("{ 'username': ?0}")
	Optional<Usuario> findByUsername(String username);
}
