package com.spring.microservices.dairyfactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class DairyFactoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(DairyFactoryApplication.class, args);
	}

}
