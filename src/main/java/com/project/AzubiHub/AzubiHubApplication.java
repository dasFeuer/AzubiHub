package com.project.AzubiHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing  /*
This annotation that activates the auditing functionality in your Spring Boot application.
It's a configuration annotation that tells Spring to automatically track and populate audit fields in your entities.
For example (Time, Date) also help to avoid NOT NULL CONSTRAINTS.
*/
public class AzubiHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(AzubiHubApplication.class, args);
	}

}
