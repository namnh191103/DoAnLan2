package com.DATN_2.BanSach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.DATN_2.BanSach", "com.myapp"})
@EnableJpaRepositories(basePackages = "com.myapp.repository")
@EntityScan(basePackages = "com.myapp.model")
public class BanSachApplication {

	public static void main(String[] args) {
		SpringApplication.run(BanSachApplication.class, args);
	}

}
