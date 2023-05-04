package net.lazars.garden.service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.time.Instant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.lazars.garden.client.SunriseSunsetClient;
import net.lazars.garden.model.SunriseSunsetResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Response;

@Slf4j
@Service
@RequiredArgsConstructor
public class SunriseSunsetService {

  private final SunriseSunsetClient sunriseSunsetClient;

  @Value("${garden.location.latitude}")
  private String latitude;

  @Value("${garden.location.longitude}")
  private String longitude;

  @Getter @Setter private Instant sunsetData;

  @PostConstruct
  void init() {
    log.info("Initializing sunrise/sunset service");
    loadSunsetData("today");
    if (Instant.now().isAfter(sunsetData)) {
      loadUpcomingSunsetData();
    }
  }

  public Instant loadUpcomingSunsetData() {
    loadSunsetData("tomorrow");
    return sunsetData;
  }

  private void loadSunsetData(final String date) {
    final Response<SunriseSunsetResponse> response;
    try {
      response = sunriseSunsetClient.getSunsetData(latitude, longitude, date, 0).execute();
    } catch (IOException e) {
      log.error("Error loading sunrise/sunset data", e);
      return;
    }

    if (!response.isSuccessful()) {
      log.error("Error loading sunrise/sunset data: {}", response);
      return;
    }

    final SunriseSunsetResponse sunriseSunsetResponse = response.body();
    if (sunriseSunsetResponse != null) {
      final String sunset = sunriseSunsetResponse.getResults().getNauticalTwilightEnd();
      sunsetData = Instant.parse(sunset);
      log.info("Loaded sunset data: {}", sunsetData);
    } else {
      log.error("Error loading sunrise/sunset data: {}", response);
    }
  }
}
