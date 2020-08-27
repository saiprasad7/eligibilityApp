package com.eligibility.benefit.Repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eligibility.benefit.model.Subscribers;

@Repository
public interface SubscriberRepository extends MongoRepository<Subscribers, Long> {
	
	Subscribers findBySubscriberId(String subscribers);

}
