package com.example.PsiSoftware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class PsiSoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsiSoftwareApplication.class, args);
	}

}
