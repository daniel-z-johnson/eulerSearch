package com.self.exercise.search.euler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EulerApplication.class, args);
	}
}
