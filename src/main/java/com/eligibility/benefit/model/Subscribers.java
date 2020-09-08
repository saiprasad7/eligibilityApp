package com.eligibility.benefit.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Data
@Document
public class Subscribers {
	
	@Id
	private String id;
	
	private String subscriberId;
	
	private Name name;
	
	private Address address;
	
	private Date dateOfBirth;
	
	private List<Benefit> benefits;
	
	private List<Dependents> dependents;

}
