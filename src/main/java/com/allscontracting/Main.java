package com.allscontracting;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(value="alls.mail")
public class Main {

	public static void main(String[] arguments) {
		SpringApplication.run(Main.class, arguments);
	}

}
