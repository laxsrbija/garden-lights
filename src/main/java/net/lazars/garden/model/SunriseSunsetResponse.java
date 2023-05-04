package net.lazars.garden.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SunriseSunsetResponse {

  private SunriseSunsetData results;
}
