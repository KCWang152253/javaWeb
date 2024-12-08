package com.kc.webdemo02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Webdemo02Application {

	public static void main(String[] args) {
		SpringApplication.run(Webdemo02Application.class, args);
	}

}
