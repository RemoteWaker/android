package dev.vicart.remotewaker.views.bindings

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object ErrorBinding {

    @JvmStatic
    @BindingAdapter("app:errorText")
    fun setErrorText(view: TextInputLayout, error: String) {
        view.error = error
    }
}