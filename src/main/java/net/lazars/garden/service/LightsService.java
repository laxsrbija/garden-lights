package net.lazars.garden.service;

import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.lazars.garden.client.SmartThingsClient;
import net.lazars.garden.model.DeviceCommand;
import net.lazars.garden.model.DeviceCommandRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class LightsService {

  private final SmartThingsClient smartThingsClient;

  @Value("${garden.switch.token}")
  private String token;

  @Value("${garden.switch.device-id}")
  private String deviceId;

  public void turnOn() {
    sendCommand("on");
  }

  public void turnOff() {
    sendCommand("off");
  }

  private void sendCommand(final String command) {
    final DeviceCommand deviceCommand =
        DeviceCommand.builder().component("main").capability("switch").command(command).build();

    final DeviceCommandRequest deviceCommandRequest = new DeviceCommandRequest();
    deviceCommandRequest.setCommands(List.of(deviceCommand));

    final String authHeader = "Bearer " + token;

    try {
      smartThingsClient.sendCommand(deviceId, deviceCommandRequest, authHeader).execute();
    } catch (final IOException e) {
      log.error("Error sending command", e);
    }
  }
}
