package com.eligibility.benefit.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


//@Data
@Document(collection="subscribers")
public class Subscribers {
	@Id
	private String id;
	
	private String subscriberId;
	
	private Name name;
	private Address address;
	private String email;
	private String password;
	private Date dateOfBirth;
	//@DBRef(db="policies")
//	private Benefit benefit;
	//Benefit[]  = new Benefit[1];
	
   private List<Benefit> benefits;
	
	private List<Dependents> dependents;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	/**/
	public List<Benefit> getBenefits() {
		return benefits;
	}
	public void setBenefits(List<Benefit> benefits) {
		this.benefits = benefits;
	}
	public void setDependents(List<Dependents> dependents) {
		this.dependents = dependents;
	}
	public List<Dependents> getDependents() {
		return dependents;
	}

	

}
