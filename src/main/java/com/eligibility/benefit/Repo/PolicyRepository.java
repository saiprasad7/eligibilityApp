package com.eligibility.benefit.Repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eligibility.benefit.model.Policies;

@Repository
public interface PolicyRepository extends MongoRepository<Policies, Long> {

	Policies findByPolicyId(String string);
	
	List<Policies> findAll();
	
}
