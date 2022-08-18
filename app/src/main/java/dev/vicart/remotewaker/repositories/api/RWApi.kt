package dev.vicart.remotewaker.repositories.api

import dev.vicart.remotewaker.BuildConfig
import dev.vicart.remotewaker.models.Device
import dev.vicart.remotewaker.models.User
import dev.vicart.remotewaker.models.UserLogin
import dev.vicart.remotewaker.models.UserLoginResponse
import dev.vicart.remotewaker.repositories.BaseRepository.userStorage
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import javax.net.ssl.SSLSocketFactory

class RWApi {

    private val client = HttpClient(Android) {
        defaultRequest {
            url("http://192.168.1.158:8080")
            contentType(ContentType.Application.Json)
        }
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun postLogin(userLogin: UserLogin): UserLoginResponse {
        val jwt: UserLoginResponse = client.post("/api/login") {
            setBody(userLogin)
        }.body()
        return jwt
    }

    suspend fun getUser(jwt: String): User {
        val user: User = client.get("/api/me") {
            bearerAuth(jwt)
        }.body()
        return user
    }

    suspend fun getDevicesByUser(jwt: String) : List<Device> {
        val devices: List<Device> = client.get("/api/user/devices") {
            bearerAuth(jwt)
        }.body()
        return devices
    }
}