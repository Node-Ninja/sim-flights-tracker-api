package dev.nodeninja.simflightstracker;

import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfigProperties.class)
public class SimFlightsTracker {
	public static void main(String[] args) {
		SpringApplication.run(SimFlightsTracker.class, args);
	}
}
