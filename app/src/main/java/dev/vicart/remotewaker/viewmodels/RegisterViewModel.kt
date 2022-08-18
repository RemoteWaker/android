package dev.vicart.remotewaker.viewmodels

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController

class RegisterViewModel : ViewModel() {

    val username = MutableLiveData("")
    val password = MutableLiveData("")
    val confirmPassword = MutableLiveData("")

    fun register(view: View) {

    }

    fun hasAccount(view: View) {
        view.findNavController().popBackStack()
    }
}