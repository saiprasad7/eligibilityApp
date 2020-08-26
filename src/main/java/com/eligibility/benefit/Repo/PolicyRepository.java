package com.eligibility.benefit.Repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eligibility.benefit.model.Policies;
import com.eligibility.benefit.model.Subscribers;

public interface PolicyRepository extends MongoRepository<Policies, Long> {


	Policies findByPolicyId(String string);
	List<Policies>findAll();
}
