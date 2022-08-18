package dev.vicart.remotewaker.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.vicart.remotewaker.network.ArpClient
import kotlinx.coroutines.launch

class SelectComputerViewModel : ViewModel() {

    val mac = MutableLiveData("")
    val ip = MutableLiveData("")

    fun findMac(view: View) {
        val arp = ArpClient()
        viewModelScope.launch {
            val _mac = arp.getMacFromIp(ip.value!!, view.context)
            mac.value = _mac
        }
    }
}