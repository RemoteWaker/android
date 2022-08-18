package dev.vicart.remotewaker.viewmodels

import android.content.Context
import androidx.lifecycle.*
import dev.vicart.remotewaker.network.SSDPClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception

class SelectDeviceViewModel : ViewModel() {

    val devices = MutableLiveData<SSDPClient.SSDPResponse?>(null)

    fun searchDevices(context: Context) {
        val client = SSDPClient(context)
        viewModelScope.launch(Dispatchers.IO) {
            val device = client.searchDevices("ssdp:remotewaker").firstOrNull {
                !it.isSetUp
            }
            devices.postValue(device)
        }
    }
}