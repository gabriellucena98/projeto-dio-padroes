package com.dio.project.padroes.java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProjetoDioPadroesApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoDioPadroesApplication.class, args);
	}

}
