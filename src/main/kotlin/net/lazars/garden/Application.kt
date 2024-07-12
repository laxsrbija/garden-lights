package net.lazars.garden

import io.ktor.server.application.*
import net.lazars.garden.service.LightsService
import net.lazars.garden.service.SchedulingService
import net.lazars.garden.service.SunriseSunsetService
import java.time.LocalTime

fun main(args: Array<String>) {
    io.ktor.server.cio.EngineMain.main(args)
}

fun Application.gardenLights() {
    val latitude = environment.config.property("garden.location.latitude").getString()
    val longitude = environment.config.property("garden.location.longitude").getString()
    val zone = environment.config.property("garden.location.zone").getString()
    val turnOff = environment.config.property("garden.lights.turn-off").getString()
    val token = environment.config.property("garden.switch.token").getString()
    val deviceId = environment.config.property("garden.switch.device-id").getString()

    val lightsService = LightsService(token, deviceId)
    val sunriseSunsetService = SunriseSunsetService(latitude, longitude)
    val turnOffTime = LocalTime.parse(turnOff)
    SchedulingService(lightsService, sunriseSunsetService, turnOffTime, zone)
}
