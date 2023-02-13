package com.login.hth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class HthLoginApplication extends SpringBootServletInitializer {
	public static void main(String[] args){
		SpringApplication.run(HthLoginApplication.class, args);
	}
}
