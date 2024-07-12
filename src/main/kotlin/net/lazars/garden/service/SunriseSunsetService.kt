package net.lazars.garden.service

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import net.lazars.garden.getHttpClient
import net.lazars.garden.model.SunriseSunsetResponse
import org.slf4j.LoggerFactory
import java.time.Instant

class SunriseSunsetService(private val latitude: String, private val longitude: String) {

    private val log = LoggerFactory.getLogger(javaClass)
    lateinit var sunsetData: Instant private set

    init {
        loadSunsetData("today")
        if (Instant.now().isAfter(sunsetData)) loadSunsetData("tomorrow")
    }

    fun loadUpcomingSunsetData(): Instant {
        loadSunsetData("tomorrow")
        return sunsetData
    }

    private fun loadSunsetData(date: String) {
        getHttpClient().use { client ->
            runBlocking {
                val response = client.get("https://api.sunrise-sunset.org/") {
                    url {
                        appendPathSegments("json")
                        parameters.append("lat", latitude)
                        parameters.append("lng", longitude)
                        parameters.append("date", date)
                        parameters.append("formatted", "0")
                        contentType(ContentType.Application.Json)
                    }
                }

                if (response.status == HttpStatusCode.OK) {
                    val responseData: SunriseSunsetResponse = response.body()
                    sunsetData = Instant.parse(responseData.results.twilightEnd)
                    log.info("Loaded sunset data: $sunsetData")
                }
            }
        }
    }
}
