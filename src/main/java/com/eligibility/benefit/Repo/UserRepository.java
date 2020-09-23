package com.eligibility.benefit.Repo;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import com.eligibility.benefit.model.Users;

@Repository
public interface UserRepository extends MongoRepository<Users, Long> {
	
	Users findByName(String name);

	Users findByToken(String token);
}
