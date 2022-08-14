package dev.vicart.remotewaker.repositories

import dev.vicart.remotewaker.repositories.api.RWApi
import dev.vicart.remotewaker.repositories.storage.UsersStorage

object BaseRepository {

    internal val api = RWApi()

    internal val userStorage = UsersStorage()
}