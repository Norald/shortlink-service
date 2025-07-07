package com.prokop.shortlink;

import org.springframework.boot.SpringApplication;

public class TestShortlinkServiceApplication {

	public static void main(String[] args) {
		SpringApplication.from(ShortlinkServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
