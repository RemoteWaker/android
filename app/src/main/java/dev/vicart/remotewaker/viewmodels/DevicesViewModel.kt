package dev.vicart.remotewaker.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vicart.remotewaker.models.Device
import dev.vicart.remotewaker.repositories.DevicesRepository
import dev.vicart.remotewaker.repositories.UsersRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class DevicesViewModel : ViewModel() {

    val devices = MutableLiveData(emptyList<Device>())

    fun loadDevices() {
        viewModelScope.launch {
            devices.value = UsersRepository.currentUser?.devices
        }
    }
}