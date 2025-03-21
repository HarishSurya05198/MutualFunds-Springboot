package com.mutual.funds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MutualFundsApplication {

	public static void main(String[] args) {
		SpringApplication.run(MutualFundsApplication.class, args);
	}

}
