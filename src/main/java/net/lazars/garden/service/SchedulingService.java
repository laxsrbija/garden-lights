package net.lazars.garden.service;

import jakarta.annotation.PostConstruct;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SchedulingService {

  private final TaskScheduler taskScheduler;
  private final LightsService lightsService;
  private final SunriseSunsetService sunriseSunsetService;

  @PostConstruct
  void init() {
    scheduleTask(sunriseSunsetService.getSunsetData());
  }

  @Scheduled(cron = "${garden.lights.turn-off}", zone = "${garden.location.zone}")
  void turnOffLights() {
    lightsService.turnOff();
  }

  void onTaskRun() {
    log.info("Task start");
    lightsService.turnOn();

    final Instant upcomingSunset = sunriseSunsetService.loadUpcomingSunsetData();
    scheduleTask(upcomingSunset);
  }

  private void scheduleTask(final Instant instant) {
    taskScheduler.schedule(new TurnOnLightsTask(this), instant);
    log.info("Scheduled task for {}", instant);
  }

  private record TurnOnLightsTask(SchedulingService schedulingService) implements Runnable {

    @Override
    public void run() {
      schedulingService.onTaskRun();
    }
  }
}
