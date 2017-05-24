package com.adowsky;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.TimeZone;

@EnableScheduling
@SpringBootApplication
public class HomeLibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeLibraryApplication.class, args);
	}
}
