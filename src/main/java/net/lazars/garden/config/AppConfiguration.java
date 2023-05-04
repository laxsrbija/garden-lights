package net.lazars.garden.config;

import net.lazars.garden.client.SmartThingsClient;
import net.lazars.garden.client.SunriseSunsetClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class AppConfiguration {

  @Bean
  public SunriseSunsetClient sunriseSunsetClient() {
    return new Retrofit.Builder()
        .baseUrl("https://api.sunrise-sunset.org/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
        .create(SunriseSunsetClient.class);
  }

  @Bean
  public SmartThingsClient smartThingsClient() {
    return new Retrofit.Builder()
        .baseUrl("https://api.smartthings.com/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
        .create(SmartThingsClient.class);
  }
}
