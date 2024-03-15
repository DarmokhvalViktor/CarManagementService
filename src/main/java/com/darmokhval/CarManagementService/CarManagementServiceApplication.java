package com.darmokhval.CarManagementService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CarManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarManagementServiceApplication.class, args);
	}

}
