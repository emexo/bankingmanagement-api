package com.bankingmanagement;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@Log4j2
@SpringBootApplication
public class BankingManagement {

	public static void main(String[] args) {
		SpringApplication.run(BankingManagement.class, args);
		log.info("#### Application has been started ####");
	}

}
