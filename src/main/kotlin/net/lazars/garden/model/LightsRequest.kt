package net.lazars.garden.model

import kotlinx.serialization.Serializable

@Serializable
data class DeviceCommandRequest(val commands: List<DeviceCommand>)

@Serializable
data class DeviceCommand(
    val component: String,
    val capability: String,
    val command: String,
    val arguments: List<String> = listOf()
)