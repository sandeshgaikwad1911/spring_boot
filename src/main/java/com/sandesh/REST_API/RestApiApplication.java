package com.sandesh.REST_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class RestApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);
	}

		@Bean
		public PlatformTransactionManager anyName(MongoDatabaseFactory dbFactory) {
			return new MongoTransactionManager(dbFactory);
		}
}

/*
		@SpringBootApplication => it's combination of three anotation
			1.@ComponentScan
			2.@EnableAutoConfiguration
			3.@Configuration

			so here we can write configuration and can create beans here using method

			MongoDatabaseFactory help us in creating connection with database.
*/

//	PlatformTransactionManager is an interface and it's implementation is MongoTransactionManager

//	15br8u3ZccD0a6Of
