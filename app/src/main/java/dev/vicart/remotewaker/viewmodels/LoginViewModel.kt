package dev.vicart.remotewaker.viewmodels

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.*
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.textfield.TextInputLayout
import dev.vicart.remotewaker.R
import dev.vicart.remotewaker.repositories.UsersRepository
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    val username = MutableLiveData("")
    val password = MutableLiveData("")
    val loginIncorrect = MutableLiveData(false)

    fun login(view: View) {
        viewModelScope.launch {
            try {
                UsersRepository.loginWithPassword(username.value!!, password.value!!, view.context)
                view.findNavController().navigate(R.id.action_loginFragment_to_devicesFragment)
            }
            catch (e: Exception) {
                e.printStackTrace()
                loginIncorrect.value = true
            }
        }
    }

    fun registerRequested(view: View) {
        val usernameLayout: TextInputLayout = view.rootView.findViewById(R.id.login_username_layout)
        val passwordLayout: TextInputLayout = view.rootView.findViewById(R.id.login_password_layout)
        val image: ImageView = view.rootView.findViewById(R.id.login_image)
        val link: Button = view.rootView.findViewById(R.id.login_link)
        val btn: Button = view.rootView.findViewById(R.id.login_button)

        val extra = FragmentNavigatorExtras(usernameLayout to "username", passwordLayout to "password",
            image to "image", link to "link", btn to "btn")

        view.findNavController().navigate(R.id.action_loginFragment_to_registerFragment, null, null, extra)
    }
}