package dev.vicart.remotewaker.viewmodels

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import dev.vicart.remotewaker.R
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

    fun addDevice(view: View) {
        view.findNavController().navigate(R.id.action_devicesFragment_to_stepperActivity)
    }
}