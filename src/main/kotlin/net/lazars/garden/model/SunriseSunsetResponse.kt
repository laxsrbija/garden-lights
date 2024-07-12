package net.lazars.garden.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SunriseSunsetResponse(val results: SunriseSunsetData)

@Serializable
data class SunriseSunsetData(@SerialName("civil_twilight_end") val twilightEnd: String)
