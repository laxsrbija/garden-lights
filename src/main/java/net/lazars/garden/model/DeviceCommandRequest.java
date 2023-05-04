package net.lazars.garden.model;

import java.util.List;
import lombok.Data;

@Data
public class DeviceCommandRequest {

  private List<DeviceCommand> commands;
}
