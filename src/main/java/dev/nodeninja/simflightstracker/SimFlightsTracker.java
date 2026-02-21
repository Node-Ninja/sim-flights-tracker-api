package dev.nodeninja.simflightstracker;

import dev.nodeninja.simflightstracker.config.ApplicationConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ServletComponentScan
@EnableConfigurationProperties(ApplicationConfigProperties.class)
@EnableMongoAuditing
public class SimFlightsTracker {
	public static void main(String[] args) {
		SpringApplication.run(SimFlightsTracker.class, args);
	}
}
