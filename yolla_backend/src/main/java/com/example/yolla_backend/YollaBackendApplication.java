package com.example.yolla_backend;

import com.example.yolla_backend.Model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class YollaBackendApplication {
	private static final Logger logger = LoggerFactory.getLogger(YollaBackendApplication.class);

	public static void main(String[] args) {
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
		ctx.scan("com.example.yolla_backend");
		ctx.refresh();
		SpringApplication.run(YollaBackendApplication.class, args);
	}

}
