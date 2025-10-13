package com.jsp.clinkNBuy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ClinkNBuyRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinkNBuyRestApplication.class, args);
	}

}
