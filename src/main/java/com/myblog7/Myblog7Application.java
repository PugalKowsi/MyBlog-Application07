package com.myblog7;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.apache.coyote.http11.Constants.a;

@SpringBootApplication
public class Myblog7Application {

	public static void main(String[] args) {
		SpringApplication.run(Myblog7Application.class, args);
	}
 @Bean
 public ModelMapper modelMapper(){
		return new ModelMapper();
 }
 @Bean
 public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	}

