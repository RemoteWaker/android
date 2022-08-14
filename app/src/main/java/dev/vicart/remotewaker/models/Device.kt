package dev.vicart.remotewaker.models

import kotlinx.serialization.Serializable

@Serializable
data class Device(val id: Long, val name: String, val deviceId: Long)
