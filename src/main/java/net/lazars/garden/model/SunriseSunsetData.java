package net.lazars.garden.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SunriseSunsetData {

  @JsonProperty("civil_twilight_end")
  private String twilightEnd;
}
