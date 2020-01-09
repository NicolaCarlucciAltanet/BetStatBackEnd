package com.betstat.backend;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@Configuration
@PropertySource(value= {"classpath:application.properties"})

public class BackEndBetStatApplication {

	/**
	 * Istanza di Log4j2
	 */

	final static Logger logger = LogManager.getLogger(BackEndBetStatApplication.class);

	public static void main(String[] args) throws ClientProtocolException, IOException {
		SpringApplication.run(BackEndBetStatApplication.class, args);
	}

}
