package dev.vicart.remotewaker.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLogin(val username: String, val password: String)