package net.lazars.garden.service

import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import net.lazars.garden.getHttpClient
import net.lazars.garden.model.DeviceCommand
import net.lazars.garden.model.DeviceCommandRequest
import org.slf4j.LoggerFactory

class LightsService(private val token: String, private val deviceId: String) {

    private val log = LoggerFactory.getLogger(javaClass)

    fun turnOn() {
        sendCommand("on")
    }

    fun turnOff() {
        sendCommand("off")
    }

    private fun sendCommand(command: String) {
        val request = DeviceCommandRequest(listOf(DeviceCommand("main", "switch", command)))
        getHttpClient().use { client ->
            runBlocking {
                val response = client.post("https://api.smartthings.com/") {
                    url {
                        appendPathSegments("v1/devices/$deviceId/commands")
                        contentType(ContentType.Application.Json)
                        setBody(request)
                        headers {
                            append(HttpHeaders.Authorization, "Bearer $token")
                        }
                    }
                }

                if (response.status != HttpStatusCode.OK) {
                    log.error("Unable to toggle lights: ${response.status}")
                }
            }
        }
    }
}
