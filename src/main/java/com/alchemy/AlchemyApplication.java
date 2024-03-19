package com.alchemy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.alchemy.utils.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties(FileStorageProperties.class)

public class AlchemyApplication {

	public static void main(String[] args) {

		SpringApplication.run(AlchemyApplication.class, args);

	}

}
