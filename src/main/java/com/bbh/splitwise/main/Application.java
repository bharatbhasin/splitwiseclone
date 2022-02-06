package com.bbh.splitwise.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.bbh.splitwise")
@EnableJpaRepositories("com.bbh.splitwise")
@EntityScan("com.bbh.splitwise")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}
}
