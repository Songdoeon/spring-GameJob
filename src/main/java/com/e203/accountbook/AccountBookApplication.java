package com.e203.accountbook;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class AccountBookApplication {

	public static void main(String[] args)
	{
		ConfigurableApplicationContext run = SpringApplication.run(AccountBookApplication.class, args);

		log.info("Batch Application Closed");
		run.close();
	}

}
