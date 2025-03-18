package com.carPooling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.carPooling")
public class CarPoolingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarPoolingApplication.class, args);
	}

}
