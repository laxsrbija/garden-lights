package net.lazars.garden;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GardenLightsApplication {

  public static void main(final String[] args) {
    SpringApplication.run(GardenLightsApplication.class, args);
  }
}
