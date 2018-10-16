package com.lp.reptilianvideo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReptilianVideoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReptilianVideoApplication.class, args);
	}
}
