package com.eligibility.benefit.Repo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.eligibility.benefit.model.Policies;

public interface PolicyRepository extends MongoRepository<Policies, Long> {


	Optional<Policies> findByPolicyId(String string);

}
