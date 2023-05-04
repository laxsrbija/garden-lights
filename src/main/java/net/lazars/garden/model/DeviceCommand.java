package net.lazars.garden.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeviceCommand {

  private String component;
  private String capability;
  private String command;

  @Builder.Default
  private List<String> arguments = List.of();
}
