package com.eligibility.benefit.Repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.eligibility.benefit.model.Benefit;
import com.eligibility.benefit.model.Dependents;
import com.eligibility.benefit.model.Subscribers;

@Repository
public interface SubscriberRepository extends MongoRepository<Subscribers, Long> {
	
	List<Subscribers>findAll();
		// TODO Auto-generated method stub

	//void saveAll(Subscribers subscriber, Benefit benefit, Dependents dependents);

	//void saveAll(Benefit benefit, Subscribers subscriber);
		

	//}

}
