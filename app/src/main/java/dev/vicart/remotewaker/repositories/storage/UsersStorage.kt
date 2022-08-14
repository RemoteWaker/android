package dev.vicart.remotewaker.repositories.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(name = "users")

class UsersStorage {

    companion object {
        val JWT_KEY = stringPreferencesKey("jwt")
    }

    suspend fun getLocalJWT(context: Context): String? {
        return context.userDataStore.data.map {
            it[JWT_KEY]
        }.firstOrNull()
    }

    suspend fun setLocalJWT(context: Context, jwt: String) {
        context.userDataStore.edit {
            it[JWT_KEY] = jwt
        }
    }

    suspend fun removeLocalJWT(context: Context) {
        context.userDataStore.edit {
            it.remove(JWT_KEY)
        }
    }
}