package com.resume.builder.reume_builder;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class  ReumeBuilderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReumeBuilderApplication.class, args);
	}



}

