package dev.vicart.remotewaker.models

import kotlinx.serialization.Serializable

@Serializable
data class User(val username: String, val password: String, val devices: List<Device>)
