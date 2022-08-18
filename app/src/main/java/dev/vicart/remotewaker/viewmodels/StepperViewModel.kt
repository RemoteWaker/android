package dev.vicart.remotewaker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepperViewModel : ViewModel() {

    val isNextEnabled = MutableLiveData(true)
}