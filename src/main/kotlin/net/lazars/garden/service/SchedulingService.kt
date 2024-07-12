package net.lazars.garden.service

import org.slf4j.LoggerFactory
import java.time.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SchedulingService(
    private val lightsService: LightsService,
    private val sunriseSunsetService: SunriseSunsetService,
    private val lightsOffTime: LocalTime,
    private val zone: String
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val executor = Executors.newSingleThreadScheduledExecutor()

    init {
        scheduleTask(sunriseSunsetService.sunsetData) { _ -> executeLightsOnTask() }
        scheduleTask(getLightsOffTime()) { _ -> executeLightsOffTask() }
    }

    private fun scheduleTask(instant: Instant, task: (Any?) -> Unit) {
        val now = Instant.now()
        val duration = Duration.between(now, instant)

        executor.schedule({
            task.invoke(null)
        }, duration.toMillis(), TimeUnit.MILLISECONDS)
    }

    private fun executeLightsOnTask() {
        log.info("Lights on task start")
        lightsService.turnOn()

        scheduleTask(sunriseSunsetService.loadUpcomingSunsetData()) { _ -> executeLightsOnTask() }
    }

    private fun executeLightsOffTask() {
        log.info("Lights off task start")
        lightsService.turnOff()

        scheduleTask(getLightsOffTime()) { _ -> executeLightsOffTask() }
    }

    private fun getLightsOffTime(): Instant {
        var lightsOffTime = LocalDateTime.of(LocalDate.now(), lightsOffTime)
        if (!lightsOffTime.isAfter(LocalDateTime.now())) lightsOffTime = lightsOffTime.plusDays(1)

        val instant = lightsOffTime.atZone(ZoneId.of(zone)).toInstant()
        log.info("Lights off: $instant")

        return instant
    }
}
