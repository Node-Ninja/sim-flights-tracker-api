package dev.nodeninja.simflightstracker;

import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfigProperties.class)
public class SimFlightsTracker {
	public static void main(String[] args) {
		SpringApplication.run(SimFlightsTracker.class, args);
	}
}
