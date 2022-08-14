package dev.vicart.remotewaker.models

import kotlinx.serialization.Serializable

@Serializable
data class UserLoginResponse(val jwt: String)
