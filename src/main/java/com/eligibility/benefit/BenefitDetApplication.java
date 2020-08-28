package com.eligibility.benefit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
//@ComponentScan("com.eligibility.benefit")
public class BenefitDetApplication {
	
	public static void main(String[] args) {
		System.out.println("test in main method");
		SpringApplication.run(BenefitDetApplication.class, args);
	}

}
