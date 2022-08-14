package dev.vicart.remotewaker.repositories

import android.content.Context
import dev.vicart.remotewaker.models.User
import dev.vicart.remotewaker.models.UserLogin
import dev.vicart.remotewaker.repositories.BaseRepository.api
import dev.vicart.remotewaker.repositories.BaseRepository.userStorage
import dev.vicart.remotewaker.repositories.api.RWApi
import dev.vicart.remotewaker.repositories.storage.UsersStorage

object UsersRepository {

    var currentUser: User? = null

    suspend fun loginWithPassword(username: String, password: String, context: Context) {
        val response = api.postLogin(UserLogin(username, password))

        userStorage.setLocalJWT(context, response.jwt)

        loginWithJWT(response.jwt, context)
    }

    suspend fun loginWithJWT(jwt: String, context: Context) {
        try {
            val user = api.getUser(jwt)
            currentUser = user
        }
        catch (e: Exception) {
            logout(context)
        }
    }

    suspend fun initLocalUser(context: Context) {
        val jwt = userStorage.getLocalJWT(context)
        if(jwt != null) {
            loginWithJWT(jwt, context)
        }
    }

    suspend fun logout(context: Context) {
        currentUser = null
        userStorage.removeLocalJWT(context)
    }
}