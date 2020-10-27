package com.example.springbootteste;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringboottesteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringboottesteApplication.class, args);
	}

}
